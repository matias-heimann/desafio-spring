package com.meli.desafiospring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.exceptions.FilterNotValidException;
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
import com.meli.desafiospring.services.impl.ShoppingCartServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

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

    private final String ONE_ID_FILE = "src/test/resources/static/oneProductWithId0.json";
    private final String FILTERED_BY_CATEGORY = "src/test/resources/static/filteredByCategoryProducts.json";

    @BeforeEach
    public void setConfig(){
        initMocks(this);
        this.shoppingCartService = new ShoppingCartServiceImpl(shoppingRepository, productRepository);
    }

    @Test
    public void testAddItemToCart() throws NotFoundProductException, FilterNotValidException, IOException, NotEnoughProductsException {
        List<IndividualPurchase> purchases = new LinkedList<>();
        purchases.add(new IndividualPurchase(0, 1));
        PurchaseArticles purchaseArticles = new PurchaseArticles(purchases);

        Set<Integer> ids = new HashSet<>();
        ids.add(0);

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOS = objectMapper.readValue(new File(ONE_ID_FILE),
                new TypeReference<>(){});

        Mockito.when(this.shoppingRepository.getOpenShoppingCart()).thenReturn(null);
        Mockito.when(this.productRepository.getByIds(ids)).thenReturn(productDAOS);
        Mockito.when(this.shoppingRepository.save(new ShoppingCartDao())).thenReturn(new ShoppingCartDao());

        List<ProductTicketDTO> productTicketDTOS = new LinkedList<>();
        productTicketDTOS.add(new ProductTicketDTO(0, "Desmalezadora", "Makita", 1));

        BuyOrderDTO expected = new BuyOrderDTO(null, productTicketDTOS, 9600,
                new StatusCodeDTO(HttpStatus.OK, "The products were added successfully"));

        Assert.assertEquals(expected, this.shoppingCartService.addItemsToList(purchaseArticles));
    }

    @Test
    public void testNullRequest(){
        Assert.assertThrows(NotEnoughProductsException.class, () -> this.shoppingCartService.addItemsToList(null));
    }

    @Test
    public void testEmptyBuyRequest(){
        Assert.assertThrows(NotEnoughProductsException.class,
                () -> this.shoppingCartService.addItemsToList(new PurchaseArticles(new LinkedList<>())));
    }

    @Test
    public void testAddItemToOpenCart() throws FilterNotValidException, NotFoundProductException, NotEnoughProductsException, IOException {
        this.testAddItemToCart();

        List<IndividualPurchase> purchases = new LinkedList<>();
        purchases.add(new IndividualPurchase(0, 1));
        purchases.add(new IndividualPurchase(2, 1));
        PurchaseArticles purchaseArticles = new PurchaseArticles(purchases);

        Set<Integer> ids = new HashSet<>();
        ids.add(0);
        ids.add(2);

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOS = objectMapper.readValue(new File(FILTERED_BY_CATEGORY),
                new TypeReference<>(){});

        List<IndividualPurchase> purchases1 = new LinkedList<>();
        purchases.add(new IndividualPurchase(0, 1));

        Mockito.when(this.shoppingRepository.getOpenShoppingCart()).thenReturn(new ShoppingCartDao(0, purchases1));
        Mockito.when(this.productRepository.getByIds(ids)).thenReturn(productDAOS);

        List<ProductTicketDTO> productTicketDTOS = new LinkedList<>();
        productTicketDTOS.add(new ProductTicketDTO(0, "Desmalezadora", "Makita", 2));
        productTicketDTOS.add(new ProductTicketDTO(2, "Soldadora", "Black & Decker", 1));

        BuyOrderDTO expected = new BuyOrderDTO(null, productTicketDTOS, 26415,
                new StatusCodeDTO(HttpStatus.OK, "The products were added successfully"));

        Assert.assertEquals(expected, this.shoppingCartService.addItemsToList(purchaseArticles));
    }

    @Test
    public void testCloseCart() throws FilterNotValidException, NotFoundProductException, NotEnoughProductsException, IOException, NotShoppingCartOpen {
        this.testAddItemToCart();

        List<IndividualPurchase> purchases = new LinkedList<>();
        purchases.add(new IndividualPurchase(0, 1));
        PurchaseArticles purchaseArticles = new PurchaseArticles(purchases);

        Set<Integer> ids = new HashSet<>();
        ids.add(0);

        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOS = objectMapper.readValue(new File(ONE_ID_FILE),
                new TypeReference<>(){});

        List<ProductTicketDTO> productTicketDTOS = new LinkedList<>();
        productTicketDTOS.add(new ProductTicketDTO(0, "Desmalezadora", "Makita", 1));

        Mockito.when(this.shoppingRepository.getOpenShoppingCart()).thenReturn(new ShoppingCartDao(0, purchases));
        Mockito.when(this.productRepository.getByIds(ids)).thenReturn(productDAOS);

        BuyOrderDTO expected = new BuyOrderDTO(0, productTicketDTOS, 9600,
                new StatusCodeDTO(HttpStatus.OK, "The purchase was done successfully"));
        Assert.assertEquals(expected, this.shoppingCartService.finishBuyOrder());
    }

    public void testCloseShoppingCartWhenThereIsNoCartOpen(){
        Mockito.when(this.shoppingRepository.getOpenShoppingCart()).thenReturn(null);
        Assert.assertThrows(NotShoppingCartOpen.class, () -> this.shoppingCartService.finishBuyOrder());
    }
}
