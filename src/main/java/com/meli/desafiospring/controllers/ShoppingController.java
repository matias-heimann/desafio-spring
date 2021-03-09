package com.meli.desafiospring.controllers;

import com.meli.desafiospring.exceptions.NotEnoughProductsException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.exceptions.NotShoppingCartOpen;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.dto.BuyOrderDTO;
import com.meli.desafiospring.services.ProductService;
import com.meli.desafiospring.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/purchase-request")
public class ShoppingController extends BaseController{

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * Add items to the shopping cart, if there isn't an open shopping cart it creates one.
     * @param products
     * @return Order of products
     * @throws NotFoundProductException
     * @throws NotEnoughProductsException
     */
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("")
    public BuyOrderDTO purchaseItems(@RequestBody PurchaseArticles products) throws NotFoundProductException,
            NotEnoughProductsException {
        return this.shoppingCartService.addItemsToList(products);
    }

    /**
     * Close purchase request and saves the shopping request.
     * @return
     * @throws NotShoppingCartOpen
     * @throws NotFoundProductException
     */
    @PostMapping("/end")
    public BuyOrderDTO purchaseItems() throws NotShoppingCartOpen, NotFoundProductException {
        return this.shoppingCartService.finishBuyOrder();
    }

}
