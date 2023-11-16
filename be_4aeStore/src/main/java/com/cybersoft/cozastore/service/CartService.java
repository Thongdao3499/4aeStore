package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CartEntity;
import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.entity.UserEntity;
import com.cybersoft.cozastore.payload.request.CartRequest;
import com.cybersoft.cozastore.payload.response.CartResponse;
import com.cybersoft.cozastore.repository.CartRepository;
import com.cybersoft.cozastore.repository.ProductRepository;
import com.cybersoft.cozastore.repository.UserRepository;
import com.cybersoft.cozastore.service.imp.CartServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService implements CartServiceImp {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean insertProductIntoCart(CartRequest cartRequest) {
        CartEntity cart = new CartEntity();

        Optional<ProductEntity> product = productRepository.findById(cartRequest.getIdProduct());
        Optional<UserEntity> user = userRepository.findById(cartRequest.getIdUser());

        if (product.isPresent() && user.isPresent()) {
            cart.setProduct(product.get());
            cart.setUser(user.get());
            cart.setQuanity(cartRequest.getQuanity());

            try {
                cartRepository.save(cart);
                return true;
            } catch (Exception e){
                System.out.println("Error: " + e);
                return false;
            }
        }
        return false;
    }

    @Override
    public List<CartResponse> getCart(int idUser) {
        List<CartEntity> cartEntities = cartRepository.findAll();
        List<CartResponse> cartResponses = new ArrayList<>();

        for (CartEntity c: cartEntities) {
            if (c.getUser().getId() == idUser) {
                CartResponse cartTemp = new CartResponse();

                cartTemp.setCart(c.getId());
                cartTemp.setQuanity(c.getQuanity());
                cartTemp.setNameProduct(c.getProduct().getName());

                cartResponses.add(cartTemp);
            }
        }
        return cartResponses;
    }

}
