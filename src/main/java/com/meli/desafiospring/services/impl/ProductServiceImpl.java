package com.meli.desafiospring.services.impl;

import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.dto.BuyOrderDTO;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.model.dto.ProductTicketDTO;
import com.meli.desafiospring.model.dto.StatusCodeDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    private Integer purchases;

    public ProductServiceImpl(){
        this.purchases = 0;
    }

    @Override
    public List<ProductListDTO> getProducts(HashMap<String, Object> filters, Integer order) {
        return this.productRepository
                .getWithFilterAndOrder(filters, order)
                .stream()
                .map(p -> new ProductListDTO(p)).collect(Collectors.toList());
    }

    @Override
    public BuyOrderDTO purchaseItems(PurchaseArticles products) throws NotFoundProductException {
        List<Integer> ids = products.getArticles()
                .stream().map(p -> p.getId()).collect(Collectors.toList());
        List<ProductDAO> productDAOS = this.productRepository.getByIds(ids);
        HashMap<Integer, Integer> prices = new HashMap<>();
        productDAOS.stream().forEach(p -> prices.put(p.getId(), p.getPrice()));
        Integer total = products.getArticles().stream()
                .map(p -> p.getQuantity() * prices.get(p.getId()))
                .reduce(0, (i1, i2) -> i1 + i2);
        BuyOrderDTO buyOrderDTO = new BuyOrderDTO(this.purchases, products.getArticles(), total,
                new StatusCodeDTO(HttpStatus.OK, "The purchase was done successfully"));
        this.purchases++;
        return buyOrderDTO;
    }
}
