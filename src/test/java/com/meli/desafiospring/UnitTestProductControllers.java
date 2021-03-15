package com.meli.desafiospring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.controllers.ProductController;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.repositories.impl.ProductRepositoryImpl;
import com.meli.desafiospring.services.ProductService;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class UnitTestProductControllers {

    private ProductController productController;

    @Mock
    private ProductService productServiceMock;

    @BeforeEach
    public void setControllers() throws IOException {
        initMocks(this);
        this.productController = new ProductController(this.productServiceMock);
    }

    @Test
    public void testGetAllProducts() throws FilterNotValidException {
        List<ProductListDTO> products = new LinkedList<>();
        products.add(new ProductListDTO(0, "p1", "c1", "b1", 1000, true, 10, 5));
        products.add(new ProductListDTO(2, "p3", "c1", "b2", 6000, false, 20, 5));
        products.add(new ProductListDTO(1, "p2", "c2", "b2", 5000, true, 10, 4));
        products.add(new ProductListDTO(3, "p3", "c2", "b2", 5000, true, 10, 4));
        Mockito.when(productServiceMock.getProducts(new HashMap<>())).thenReturn(products);
        Assertions.assertIterableEquals(products, this.productController.getProducts(new HashMap<>()));
    }

    @Test
    public void testGetProductsFilteredByCategory() throws FilterNotValidException {
        List<ProductListDTO> products = new LinkedList<>();
        products.add(new ProductListDTO(0, "p1", "c1", "b1", 1000, true, 10, 5));
        products.add(new ProductListDTO(2, "p3", "c1", "b2", 6000, false, 20, 5));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("categoria", "c1");
        Mockito.when(productServiceMock.getProducts(hashMap)).thenReturn(products);
        Assertions.assertIterableEquals(products, this.productController.getProducts(hashMap));
    }

    @Test
    public void testGetProductsFilteredByCategoryAndBrand() throws FilterNotValidException {
        List<ProductListDTO> products = new LinkedList<>();
        products.add(new ProductListDTO(0, "p1", "c1", "b1", 1000, true, 10, 5));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("categoria", "c1");
        hashMap.put("brand", "b1");
        Mockito.when(productServiceMock.getProducts(hashMap)).thenReturn(products);
        Assertions.assertIterableEquals(products, this.productController.getProducts(hashMap));
    }

    @Test
    public void testGetProductsWithNullMap() throws FilterNotValidException {
        Mockito.when(productServiceMock.getProducts(null)).thenThrow(FilterNotValidException.class);
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productController.getProducts(null));
    }

    @Test
    public void testInvalidFilter() throws FilterNotValidException {
        Mockito.when(productServiceMock.getProducts(null)).thenThrow(FilterNotValidException.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("nullFilter", "nullValue");
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productController.getProducts(null));
    }

    @Test
    public void testInvalidOrderValue() throws FilterNotValidException {
        Mockito.when(productServiceMock.getProducts(null)).thenThrow(FilterNotValidException.class);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("order", "6");
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productController.getProducts(null));
    }


}
