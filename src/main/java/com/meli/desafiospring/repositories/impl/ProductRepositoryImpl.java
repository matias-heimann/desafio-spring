package com.meli.desafiospring.repositories.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.ProductDAO;
import com.meli.desafiospring.repositories.ProductRepository;
import com.meli.desafiospring.utils.FilterRepositoryUtil;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private HashMap<Integer, ProductDAO> products;
    private HashMap<String, Function<FilterRepositoryUtil, Boolean>> filters;
    private HashMap<Integer, Comparator<ProductDAO>> sorters;


    public ProductRepositoryImpl() throws IOException {
        products = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDAO> productList = objectMapper.readValue(new File("src/main/resources/static/products.json"),
                new TypeReference<List<ProductDAO>>(){});
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
    public List<ProductDAO> getWithFilterAndOrder(HashMap<String, Object> filters, Integer order) {
        List<ProductDAO> productsAux = this.products.values().stream().collect(Collectors.toList());
        for(Map.Entry<String, Object> entry: filters.entrySet()){
            productsAux = productsAux.stream().filter(p -> this.filters
                    .get(entry.getKey())
                    .apply(new FilterRepositoryUtil(p, entry.getValue())))
                    .collect(Collectors.toList());
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
        filters.put("name", (FilterRepositoryUtil filter) -> filter
                .getProductDAO()
                .getName().toLowerCase(Locale.ROOT)
                .equals(filter.getFilter()));
        filters.put("category", (FilterRepositoryUtil filter) -> filter
                .getProductDAO()
                .getCategory().toLowerCase(Locale.ROOT)
                .equals(filter.getFilter()));
        filters.put("brand", (FilterRepositoryUtil filter) -> filter
                .getProductDAO()
                .getBrand().toLowerCase(Locale.ROOT)
                .equals(filter.getFilter()));
        filters.put("maxPrice", (FilterRepositoryUtil filter) -> filter
                .getProductDAO()
                .getPrice()
                .equals(filter.getFilter()));
        filters.put("maxPrice", (FilterRepositoryUtil filter) -> (filter
                .getProductDAO()
                .getPrice()
                .compareTo((Integer) filter.getFilter()) < 0));
        filters.put("minPrice", (FilterRepositoryUtil filter) -> (filter
                .getProductDAO()
                .getPrice()
                .compareTo((Integer) filter.getFilter()) > 0));
        filters.put("freeShiping", (FilterRepositoryUtil filter) -> filter
                .getProductDAO()
                .getFreeShiping()
                .equals(filter.getFilter()));
        filters.put("prestige", (FilterRepositoryUtil filter) -> filter
                .getProductDAO()
                .getPrestige()
                .equals(filter.getFilter()));
    }

    private void addSorters(){
        sorters = new HashMap<>();
        sorters.put(0, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        sorters.put(1, (p1, p2) -> p2.getName().compareTo(p1.getName()));
        sorters.put(2, (p1, p2) -> p2.getPrice().compareTo(p1.getPrice()));
        sorters.put(3, (p1, p2) -> p1.getPrice().compareTo(p2.getPrice()));
        sorters.put(4, (p1, p2) -> p2.getPrestige().compareTo(p1.getPrestige()));
    }
}
