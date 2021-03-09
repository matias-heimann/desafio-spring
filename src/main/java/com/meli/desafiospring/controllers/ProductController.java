package com.meli.desafiospring.controllers;

import com.meli.desafiospring.exceptions.BaseException;
import com.meli.desafiospring.exceptions.FilterNotValidException;
import com.meli.desafiospring.exceptions.NotEnoughProductsException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.dto.BuyOrderDTO;
import com.meli.desafiospring.model.dto.ProductListDTO;
import com.meli.desafiospring.model.dto.StatusCodeDTO;
import com.meli.desafiospring.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1/articles")
public class ProductController extends BaseController{

    @Autowired
    private ProductService productService;


    /**
     * End point for product list with orders and filters.
     * Possible filters: id, name, category, brand, price, maxPrice, minPrice, freeShiping and prestige.
     * Possible orders: 0 (alphabetically by name(desc)), 1 (alphabetically by name (desc)),
     * 2 (ordered by descending price), 3 (ordered by ascending price), 4 or default by prestige.
     * @param filters
     * @return list of products
     * @throws FilterNotValidException
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<ProductListDTO> getProducts(@RequestParam HashMap<String, String> filters) throws FilterNotValidException {
        return this.productService.getProducts(filters);
    }

}
