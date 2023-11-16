package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.payload.request.CartRequest;
import com.cybersoft.cozastore.payload.response.CartResponse;

import java.util.List;

public interface CartServiceImp {

    boolean insertProductIntoCart(CartRequest cartRequest);

    List<CartResponse> getCart(int idUser);

}
