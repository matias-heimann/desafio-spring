package com.meli.desafiospring.services;

import com.meli.desafiospring.exceptions.NotEnoughProductsException;
import com.meli.desafiospring.exceptions.NotFoundProductException;
import com.meli.desafiospring.exceptions.NotShoppingCartOpen;
import com.meli.desafiospring.model.PurchaseArticles;
import com.meli.desafiospring.model.dto.BuyOrderDTO;

public interface ShoppingCartService {
    public BuyOrderDTO addItemsToList(PurchaseArticles purchaseArticles) throws NotFoundProductException, NotEnoughProductsException;
    public BuyOrderDTO finishBuyOrder() throws NotShoppingCartOpen, NotFoundProductException;

}
