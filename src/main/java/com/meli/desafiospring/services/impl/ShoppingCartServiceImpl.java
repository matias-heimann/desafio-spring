package com.meli.desafiospring.services.impl;

import com.meli.desafiospring.exceptions.NotEnoughProductsException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.exceptions.NotShoppingCartOpen;
import com.meli.desafiospring.model.IndividualPurchase;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.ShoppingCartDao;
import com.meli.desafiospring.model.dto.BuyOrderDTO;
import com.meli.desafiospring.model.dto.ProductTicketDTO;
import com.meli.desafiospring.model.dto.StatusCodeDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.repositories.ShoppingRepository;
import com.meli.desafiospring.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public BuyOrderDTO addItemsToList(PurchaseArticles products) throws NotFoundProductException, NotEnoughProductsException {
        ShoppingCartDao shoppingCartDao = this.shoppingRepository.getOpenShoppingCart();
        if(shoppingCartDao == null){
            shoppingCartDao = new ShoppingCartDao();
            shoppingCartDao = this.shoppingRepository.save(shoppingCartDao);
        }

        Map<Integer, Integer> amountOrderedByProductId = new HashMap<>();
        Set<Integer> ids = products.getArticles()
                .stream().map(p -> p.getId()).collect(Collectors.toSet());
        for(IndividualPurchase purchase: shoppingCartDao.getArticles()){
            amountOrderedByProductId.put(purchase.getId(), purchase.getQuantity());
            ids.add(purchase.getId());
        }

        List<ProductDAO> productDAOS = this.productRepository.getByIds(ids);

        Map<Integer, Integer> prices = new HashMap<>();
        Map<Integer, Integer> stock = new HashMap<>();
        productDAOS.stream().forEach(p -> prices.put(p.getId(), p.getPrice()));
        productDAOS.stream().forEach(p -> stock.put(p.getId(), p.getQuantity()));

        Integer total = products.getArticles().stream()
                .map(p -> p.getQuantity() * prices.get(p.getId()))
                .reduce(0, (i1, i2) -> i1 + i2);


        for(IndividualPurchase individualPurchase: products.getArticles()){
            Integer totalAmount = (amountOrderedByProductId.get(individualPurchase.getId()) != null) ?
                    amountOrderedByProductId.get(individualPurchase.getId()) + individualPurchase.getQuantity() :
                    individualPurchase.getQuantity();
            if(stock.get(individualPurchase.getId()) < totalAmount){
                throw new NotEnoughProductsException(totalAmount +
                        " units of product with id " + individualPurchase.getId() +
                        " are intended to be purchased and there are only " +
                        stock.get(individualPurchase.getId()) + " units available");
            }
            amountOrderedByProductId.put(individualPurchase.getId(), totalAmount);
        }

        shoppingCartDao.getArticles().clear();

        for(Map.Entry<Integer, Integer> entry: amountOrderedByProductId.entrySet()){
            shoppingCartDao.getArticles().add(new IndividualPurchase(entry.getKey(), entry.getValue()));
        }
        shoppingRepository.save(shoppingCartDao);

        List<ProductTicketDTO> productTicketDTOList = productDAOS.stream()
                .map(p -> new ProductTicketDTO(p.getId(), p.getName(), p.getBrand(), amountOrderedByProductId.get(p.getId())))
                .collect(Collectors.toList());
        BuyOrderDTO buyOrderDTO = new BuyOrderDTO(null, productTicketDTOList, total,
                new StatusCodeDTO(HttpStatus.OK, "The products were added successfully"));
        return buyOrderDTO;
    }

    @Override
    public BuyOrderDTO finishBuyOrder() throws NotShoppingCartOpen, NotFoundProductException {
        ShoppingCartDao shoppingCartDao = this.shoppingRepository.getOpenShoppingCart();
        if(shoppingCartDao == null){
            throw new NotShoppingCartOpen("There is no open shopping cart");
        }

        Map<Integer, Integer> amountOrderedByProductId = new HashMap<>();
        shoppingCartDao.getArticles().forEach(p -> amountOrderedByProductId.put(p.getId(), p.getQuantity()));


        Set<Integer> ids = shoppingCartDao.getArticles()
                .stream().map(p -> p.getId()).collect(Collectors.toSet());
        List<ProductDAO> productDAOS = this.productRepository.getByIds(ids);

        Map<Integer, Integer> prices = new HashMap<>();
        productDAOS.stream().forEach(p -> prices.put(p.getId(), p.getPrice()));

        Integer total = shoppingCartDao.getArticles().stream()
                .map(p -> p.getQuantity() * prices.get(p.getId()))
                .reduce(0, (i1, i2) -> i1 + i2);

        shoppingCartDao.setIsClosed(true);
        shoppingRepository.save(shoppingCartDao);

        List<ProductTicketDTO> productTicketDTOList = productDAOS.stream()
                .map(p -> new ProductTicketDTO(p.getId(), p.getName(), p.getBrand(), amountOrderedByProductId.get(p.getId())))
                .collect(Collectors.toList());

        BuyOrderDTO buyOrderDTO = new BuyOrderDTO(shoppingCartDao.getId(), productTicketDTOList, total,
                new StatusCodeDTO(HttpStatus.OK, "The purchase was done successfully"));

        return buyOrderDTO;
    }
}
