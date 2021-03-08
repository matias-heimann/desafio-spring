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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("")
    public List<ProductListDTO> getProducts(@RequestParam HashMap<String, String> filters) throws FilterNotValidException {
        System.out.println(filters);
        return this.productService.getProducts(filters);
    }

}
