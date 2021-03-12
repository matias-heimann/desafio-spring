package com.meli.desafiospring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.controllers.ProductController;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.repositories.ProductRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProductControllers {

    private final String ORDERED_BY_PRESTIGE = "src/test/resources/static/allProductsOrderedByPrestige.json";
    private final String FILTERED_BY_CATEGORY = "src/test/resources/static/filteredByCategoryProducts.json";
    private final String FILTERED_BY_CATEGORY_AND_PRICE = "src/test/resources/static/productsFilteredByCategoryAndProducts.json";
    private final String ORDERED_ALPHABETICALLY = "src/test/resources/static/productsOrderedAlphabetically.json";
    private final String ORDERED_ANTI_ALPHABETICALLY = "src/test/resources/static/productsOrderedAntiAlphabetically.json";
    private final String ORDERED_BY_PRICE_DESC = "src/test/resources/static/productsOrderedByPriceDesc.json";
    private final String ORDERED_BY_PRICE_ASC = "src/test/resources/static/productsOrderedByPriceAsc.json";

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAllProducts() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(ORDERED_BY_PRESTIGE),
                new TypeReference<>(){});

        Mockito.when(productRepository.getWithFilterAndOrder(new HashMap<>(), 4)).thenReturn(productDAOList);

        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/articles").accept(MediaType.ALL)
        ).andReturn();


        List<ProductListDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>(){});
        List<ProductListDTO> expected = objectMapper.readValue(new File(ORDERED_BY_PRESTIGE),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void getProductsInHerramientasCategory() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(FILTERED_BY_CATEGORY),
                new TypeReference<>(){});

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("category", "herramientas");
        Mockito.doReturn(productDAOList).when(productRepository).getWithFilterAndOrder(hashMap, 4);

        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/articles?category=herramientas").accept(MediaType.ALL)
        ).andReturn();


        List<ProductListDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>(){});
        List<ProductListDTO> expected = objectMapper.readValue(new File(FILTERED_BY_CATEGORY),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void getProductsInHerramientasCategoryAndWithPrice140000() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(FILTERED_BY_CATEGORY_AND_PRICE),
                new TypeReference<>(){});

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("category", "herramientas");
        hashMap.put("price", "14000");
        Mockito.doReturn(productDAOList).when(productRepository).getWithFilterAndOrder(hashMap, 4);

        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/articles?category=herramientas&price=14000").accept(MediaType.ALL)
        ).andReturn();


        List<ProductListDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>(){});
        List<ProductListDTO> expected = objectMapper.readValue(new File(FILTERED_BY_CATEGORY_AND_PRICE),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void testGetProductsAlphabetically() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(ORDERED_ALPHABETICALLY),
                new TypeReference<>(){});

        Mockito.doReturn(productDAOList).when(productRepository).getWithFilterAndOrder(new HashMap<>(), 0);

        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/articles?order=0").accept(MediaType.ALL)
        ).andReturn();


        List<ProductListDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>(){});
        List<ProductListDTO> expected = objectMapper.readValue(new File(ORDERED_ALPHABETICALLY),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void testGetProductsAntiAlphabetically() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(ORDERED_ANTI_ALPHABETICALLY),
                new TypeReference<>(){});

        Mockito.doReturn(productDAOList).when(productRepository).getWithFilterAndOrder(new HashMap<>(), 1);
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/articles?order=1").accept(MediaType.ALL)
        ).andReturn();


        List<ProductListDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>(){});
        List<ProductListDTO> expected = objectMapper.readValue(new File(ORDERED_ANTI_ALPHABETICALLY),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void testGetProductsByPriceDesc() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(ORDERED_BY_PRICE_DESC),
                new TypeReference<>(){});

        Mockito.doReturn(productDAOList).when(productRepository).getWithFilterAndOrder(new HashMap<>(), 3);
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/articles?order=3").accept(MediaType.ALL)
        ).andReturn();


        List<ProductListDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>(){});
        List<ProductListDTO> expected = objectMapper.readValue(new File(ORDERED_BY_PRICE_DESC),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void testGetProductsByPriceAsc() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productDAOList = objectMapper.readValue(new File(ORDERED_BY_PRICE_ASC),
                new TypeReference<>(){});

        Mockito.doReturn(productDAOList).when(productRepository).getWithFilterAndOrder(new HashMap<>(), 2);
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/articles?order=2").accept(MediaType.ALL)
        ).andReturn();


        List<ProductListDTO> actual = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                new TypeReference<>(){});
        List<ProductListDTO> expected = objectMapper.readValue(new File(ORDERED_BY_PRICE_ASC),
                new TypeReference<>(){});
        Assertions.assertIterableEquals(expected, actual);
    }

    @Test
    public void testNonExistentFilter() throws Exception {
        HttpUriRequest httpUriRequest = new HttpGet("http://localhost:8080/api/v1/articles?nullFilter=asd");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpUriRequest);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testInvalidValueForFilter() throws Exception {
        HttpUriRequest httpUriRequest = new HttpGet("http://localhost:8080/api/v1/articles?price=asd");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpUriRequest);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

    @Test
    public void testInvalidOrderValue() throws Exception {
        HttpUriRequest httpUriRequest = new HttpGet("http://localhost:8080/api/v1/articles?order=asd");
        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(httpUriRequest);
        Assertions.assertEquals(400, response.getStatusLine().getStatusCode());
    }

}
