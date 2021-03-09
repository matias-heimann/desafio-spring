package com.meli.desafiospring.repositories.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.utils.FilterProductRepositoryUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private HashMap<Integer, ProductDAO> products;
    private HashMap<String, Function<FilterProductRepositoryUtil, Boolean>> filters;
    private HashMap<Integer, Comparator<ProductDAO>> sorters;
    @Value("${products-json}")
    private String filename;


    public ProductRepositoryImpl() throws IOException {
        this.filename = "src/main/resources/static/products.json";
        products = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productList = objectMapper.readValue(new File(filename),
                new TypeReference<>(){});
        for(ProductDAO productDAO: productList){
            products.put(productDAO.getId(), productDAO);
        }
        this.addFilters();
        this.addSorters();
    }

    @Override
    public List<ProductDAO> getAll() {
        return this.products.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<ProductDAO> getWithFilterAndOrder(HashMap<String, String> filters, Integer order) throws FilterNotValidException {
        List<ProductDAO> productsAux = this.products.values().stream().collect(Collectors.toList());
        for(Map.Entry<String, String> entry: filters.entrySet()){
            if(this.filters.get(entry.getKey()) == null){
                throw new FilterNotValidException("Filter with name " + entry.getKey() + " is not valid");
            }
            try {
                productsAux = productsAux.stream().filter(p -> this.filters
                        .get(entry.getKey())
                        .apply(new FilterProductRepositoryUtil(p, entry.getValue())))
                        .collect(Collectors.toList());
            }
            catch (NumberFormatException numberFormatException){
                throw new FilterNotValidException(entry.getValue() + " is not a valid format for filter " + entry.getKey());
            }
        }
        return productsAux.stream().sorted(this.sorters.get(order)).collect(Collectors.toList());
    }

    @Override
    public List<ProductDAO> getByIds(Set<Integer> ids) throws NotFoundProductException {
        List<ProductDAO> productDAOS = new LinkedList<>();
        for(Integer i: ids){
            ProductDAO productDAO = this.products.get(i);
            if(productDAO == null){
                throw new NotFoundProductException("Article with id " + i + " doesn't exist");
            }
            productDAOS.add(productDAO);
        }
        return productDAOS;
    }

    private void addFilters(){
        filters = new HashMap<>();
        filters.put("id", (FilterProductRepositoryUtil filter) -> filter
                .getProductDAO()
                .getId()
                .equals(Integer.valueOf(filter.getFilter().toString())));
        filters.put("name", (FilterProductRepositoryUtil filter) -> filter
                .getProductDAO()
                .getName().toLowerCase(Locale.ROOT)
                .equals(filter.getFilter().toString().toLowerCase(Locale.ROOT)));
        filters.put("category", (FilterProductRepositoryUtil filter) -> filter
                .getProductDAO()
                .getCategory().toLowerCase(Locale.ROOT)
                .equals(filter.getFilter().toString().toLowerCase(Locale.ROOT)));
        filters.put("brand", (FilterProductRepositoryUtil filter) -> filter
                .getProductDAO()
                .getBrand().toLowerCase(Locale.ROOT)
                .equals(filter.getFilter().toString().toLowerCase(Locale.ROOT)));
        filters.put("price", (FilterProductRepositoryUtil filter) -> filter
                .getProductDAO()
                .getPrice()
                .equals(Integer.valueOf(filter.getFilter().toString())));
        filters.put("maxPrice", (FilterProductRepositoryUtil filter) -> (filter
                .getProductDAO()
                .getPrice()
                .compareTo(Integer.valueOf(filter.getFilter().toString())) <= 0));
        filters.put("minPrice", (FilterProductRepositoryUtil filter) -> (filter
                .getProductDAO()
                .getPrice()
                .compareTo(Integer.valueOf(filter.getFilter().toString())) >= 0));
        filters.put("freeShiping", (FilterProductRepositoryUtil filter) -> filter
                .getProductDAO()
                .getFreeShiping()
                .equals(Boolean.valueOf(filter.getFilter().toString())));
        filters.put("prestige", (FilterProductRepositoryUtil filter) -> filter
                .getProductDAO()
                .getPrestige()
                .equals(Integer.valueOf(filter.getFilter().toString())));
    }

    private void addSorters(){
        sorters = new HashMap<>();
        sorters.put(0, (p1, p2) -> p1.getName().toLowerCase(Locale.ROOT).compareTo(p2.getName().toLowerCase(Locale.ROOT)));
        sorters.put(1, (p1, p2) -> p2.getName().toLowerCase(Locale.ROOT).compareTo(p1.getName().toLowerCase(Locale.ROOT)));
        sorters.put(2, (p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
        sorters.put(3, (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
        sorters.put(4, (p1, p2) -> p2.getPrestige().compareTo(p1.getPrestige()));
    }
}
