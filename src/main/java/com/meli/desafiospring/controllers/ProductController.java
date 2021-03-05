package com.meli.desafiospring.controllers;

import com.meli.desafiospring.exceptions.BaseException;
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
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/articles")
    public List<ProductListDTO> getProducts(@RequestParam(defaultValue = "", required = false) String category,
                                            @RequestParam(defaultValue = "", required = false) String name,
                                            @RequestParam(defaultValue = "", required = false) String brand,
                                            @RequestParam(defaultValue = "", required = false) Integer price,
                                            @RequestParam(defaultValue = "", required = false) Integer maxPrice,
                                            @RequestParam(defaultValue = "", required = false) Integer minPrice,
                                            @RequestParam(defaultValue = "", required = false) Boolean freeShiping,
                                            @RequestParam(defaultValue = "", required = false) Integer prestige,
                                            @RequestParam(defaultValue = "4", required = false) Integer order){
        HashMap<String, Object> filters = new HashMap<>();
        if(!name.equals("")){
            filters.put("name", name.toLowerCase(Locale.ROOT));
        }
        if(!category.equals("")){
            filters.put("category", category.toLowerCase(Locale.ROOT));
        }
        if(!brand.equals("")){
            filters.put("brand", brand.toLowerCase(Locale.ROOT));
        }
        if(price != null){
            filters.put("price", price);
        }
        if(maxPrice != null){
            filters.put("maxPrice", maxPrice);
        }
        if(minPrice != null){
            filters.put("minPrice", minPrice);
        }
        if(freeShiping != null){
            filters.put("freeShiping", freeShiping);
        }
        if(prestige != null){
            filters.put("prestige", prestige);
        }
        return this.productService.getProducts(filters, order);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/purchase-request")
    public BuyOrderDTO purchaseItems(@RequestBody PurchaseArticles products) throws NotFoundProductException {
        return this.productService.purchaseItems(products);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<StatusCodeDTO> handleException(BaseException baseException){
        return new ResponseEntity<>(new StatusCodeDTO(baseException.getStatus(), baseException.getMessage()), baseException.getStatus());
    }

}
