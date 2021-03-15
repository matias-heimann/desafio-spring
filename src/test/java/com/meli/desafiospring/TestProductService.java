package com.meli.desafiospring;

import com.meli.desafiospring.controllers.ProductController;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.services.ProductService;
import com.meli.desafiospring.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.mockito.MockitoAnnotations.initMocks;

@SpringBootTest
public class TestProductService {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setControllers() {
        initMocks(this);
        this.productService = new ProductServiceImpl(this.productRepository);
    }

    @Test
    public void testGetAllProducts() throws FilterNotValidException {
        List<ProductDAO> products = new LinkedList<>();
        products.add(new ProductDAO(0, "p1", "c1", "b1", 1000, true, 10, 5));
        products.add(new ProductDAO(2, "p3", "c1", "b2", 6000, false, 20, 5));
        products.add(new ProductDAO(1, "p2", "c2", "b2", 5000, true, 10, 4));
        products.add(new ProductDAO(3, "p3", "c2", "b2", 5000, true, 10, 4));
        Mockito.when(this.productRepository.getWithFilterAndOrder(new HashMap<>(), 4)).thenReturn(products);

        List<ProductListDTO> expected = new LinkedList<>();
        expected.add(new ProductListDTO(0, "p1", "c1", "b1", 1000, true, 10, 5));
        expected.add(new ProductListDTO(2, "p3", "c1", "b2", 6000, false, 20, 5));
        expected.add(new ProductListDTO(1, "p2", "c2", "b2", 5000, true, 10, 4));
        expected.add(new ProductListDTO(3, "p3", "c2", "b2", 5000, true, 10, 4));

        Assertions.assertIterableEquals(expected, this.productService.getProducts(new HashMap<>()));
    }

    @Test
    public void testFilterByCategory() throws FilterNotValidException {
        List<ProductDAO> products = new LinkedList<>();
        products.add(new ProductDAO(0, "p1", "c1", "b1", 1000, true, 10, 5));
        products.add(new ProductDAO(2, "p3", "c1", "b2", 6000, false, 20, 5));
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("category", "c1");
        Mockito.when(this.productRepository.getWithFilterAndOrder(new HashMap<>(), 4)).thenReturn(products);

        List<ProductListDTO> expected = new LinkedList<>();
        expected.add(new ProductListDTO(0, "p1", "c1", "b1", 1000, true, 10, 5));
        expected.add(new ProductListDTO(2, "p3", "c1", "b2", 6000, false, 20, 5));

        Assertions.assertIterableEquals(expected, this.productService.getProducts(new HashMap<>()));
    }

    @Test
    public void testInvalidOrder() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("order", "not a number");
        Assertions.assertThrows(FilterNotValidException.class, () -> productService.getProducts(hashMap));
    }

    @Test
    public void testInvalidFilter() throws FilterNotValidException {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("invalidFilter", "not a filter");
        Mockito.when(productRepository.getWithFilterAndOrder(hashMap, 4)).thenThrow(FilterNotValidException.class);
        Assertions.assertThrows(FilterNotValidException.class, () -> productService.getProducts(hashMap));
    }

}
