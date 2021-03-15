package com.meli.desafiospring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.exceptions.NotEnoughProductsException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.IndividualPurchase;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.ShoppingCartDao;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.repositories.ShoppingRepository;
import com.meli.desafiospring.services.ShoppingCartService;
import com.meli.desafiospring.services.impl.ShoppingCartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class TestShoppingCartService {

    @MockBean
    private ShoppingRepository shoppingRepository;

    @MockBean
    private ProductRepository productRepository;

    private ShoppingCartService shoppingCartService;

    private final String ONE_ID_FILE = "src/test/resources/static/oneProductWithId1.json";

    @BeforeEach
    public void setConfig(){
        initMocks(this);
        this.shoppingCartService = new ShoppingCartServiceImpl(shoppingRepository, productRepository);
    }

    @Test
    public void testAddItemToCart() throws NotFoundProductException, FilterNotValidException, IOException, NotEnoughProductsException {
        List<IndividualPurchase> purchases = new LinkedList<>();
        purchases.add(new IndividualPurchase(1, 1));
        PurchaseArticles purchaseArticles = new PurchaseArticles(purchases);

        Set<Integer> ids = new HashSet<>();
        ids.add(1);

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOS = objectMapper.readValue(new File(ONE_ID_FILE),
                new TypeReference<>(){});

        Mockito.when(this.shoppingRepository.getOpenShoppingCart()).thenReturn(null);
        Mockito.when(this.productRepository.getByIds(ids)).thenReturn(productDAOS);
        Mockito.when(this.shoppingRepository.save(new ShoppingCartDao())).thenReturn(new ShoppingCartDao());


        this.shoppingCartService.addItemsToList(purchaseArticles);
    }
}
