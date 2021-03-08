package com.meli.desafiospring.repositories;

import com.meli.desafiospring.model.ShoppingCartDao;

public interface ShoppingRepository {

    public ShoppingCartDao save(ShoppingCartDao shoppingCart);
    public ShoppingCartDao getOpenShoppingCart();

}
