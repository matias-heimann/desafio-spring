package com.meli.desafiospring.repositories.impl;

import com.meli.desafiospring.model.ShoppingCartDao;
import com.meli.desafiospring.repositories.ShoppingRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class ShoppingRepositoryImpl implements ShoppingRepository {

    private HashMap<Integer, ShoppingCartDao> shoppingCarts;

    public ShoppingRepositoryImpl(){
        this.shoppingCarts = new HashMap<>();
    }

    @Override
    public ShoppingCartDao save(ShoppingCartDao shoppingCart) {
        if(shoppingCart.getId() == null){
            shoppingCart.setId(this.shoppingCarts.size());
        }
        this.shoppingCarts.put(shoppingCart.getId(), shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCartDao getOpenShoppingCart() {
        if(this.shoppingCarts.size() == 0){
            return null;
        }
        ShoppingCartDao shoppingCartDao = this.shoppingCarts.get(this.shoppingCarts.size() - 1);
        return (!shoppingCartDao.getIsClosed()) ? shoppingCartDao : null;
    }
}
