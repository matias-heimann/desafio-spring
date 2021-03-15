package com.meli.desafiospring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.repositories.impl.ProductRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootTest
public class TestProductRepository {

    private ProductRepository productRepository;

    private final String ALL_PRODUCTS = "src/test/resources/static/allProducts.json";
    private final String ORDERED_BY_PRESTIGE = "src/test/resources/static/allProductsOrderedByPrestige.json";
    private final String FILTERED_BY_CATEGORY = "src/test/resources/static/filteredByCategoryProducts.json";
    private final String FILTERED_BY_CATEGORY_AND_PRICE = "src/test/resources/static/productsFilteredByCategoryAndProducts.json";
    private final String ORDERED_ALPHABETICALLY = "src/test/resources/static/productsOrderedAlphabetically.json";
    private final String ORDERED_ANTI_ALPHABETICALLY = "src/test/resources/static/productsOrderedAntiAlphabetically.json";
    private final String ORDERED_BY_PRICE_DESC = "src/test/resources/static/productsOrderedByPriceDesc.json";
    private final String ORDERED_BY_PRICE_ASC = "src/test/resources/static/productsOrderedByPriceAsc.json";
    private final String FILTERED_BY_IDS = "src/test/resources/static/productsFilteredByIds.json";

    @BeforeEach
    private void setRepository() throws IOException {
        this.productRepository = new ProductRepositoryImpl(ALL_PRODUCTS);
    }

    @Test
    public void testGetAll() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(ALL_PRODUCTS),
                new TypeReference<>(){});

        Assertions.assertIterableEquals(productDAOList, this.productRepository.getAll());
    }

    @Test
    public void testGetAllWithPrestigeOrder() throws IOException, FilterNotValidException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> expected = objectMapper.readValue(new File(ORDERED_BY_PRESTIGE),
                new TypeReference<>(){});

        Assertions.assertIterableEquals(expected, this.productRepository.getWithFilterAndOrder(new HashMap<>(), 4));
    }

    @Test
    public void testGetFilteredByCategory() throws IOException, FilterNotValidException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> expected = objectMapper.readValue(new File(FILTERED_BY_CATEGORY),
                new TypeReference<>(){});

        HashMap<String, String> filters = new HashMap<>();
        filters.put("category", "herramientas");
        Assertions.assertIterableEquals(expected, this.productRepository.getWithFilterAndOrder(filters, 4));
    }

    @Test
    public void testOrderedAlphabetically() throws IOException, FilterNotValidException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> expected = objectMapper.readValue(new File(ORDERED_ALPHABETICALLY),
                new TypeReference<>(){});

        Assertions.assertIterableEquals(expected, this.productRepository.getWithFilterAndOrder(new HashMap<>(), 0));
    }

    @Test
    public void testInvalidFilter() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("filter", "filter");
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productRepository.getWithFilterAndOrder(hashMap, 0));
    }

    @Test
    public void testInvalidFilterValue() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("price", "not a number");
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productRepository.getWithFilterAndOrder(hashMap, 0));
    }

    @Test
    public void testGetByIds() throws IOException, NotFoundProductException, FilterNotValidException {
        Set<Integer> set = new HashSet<>();
        set.add(0);
        set.add(2);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> expected = objectMapper.readValue(new File(FILTERED_BY_IDS),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, this.productRepository.getByIds(set));
    }

    @Test
    public void testNotFoundProductGettingById(){
        Set<Integer> set = new HashSet<>();
        set.add(190);
        Assertions.assertThrows(NotFoundProductException.class, () -> this.productRepository.getByIds(set));
    }

    @Test
    public void testNullFilters(){
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productRepository.getWithFilterAndOrder(null, 0));
    }

    @Test
    public void testNullOrder(){
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productRepository.getWithFilterAndOrder(new HashMap<>(), null));
    }

    @Test
    public void testNullSetOfIds(){
        Assertions.assertThrows(FilterNotValidException.class, () -> this.productRepository.getByIds(null));
    }
}
