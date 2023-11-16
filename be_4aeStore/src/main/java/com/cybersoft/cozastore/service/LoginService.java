package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.UserEntity;
import com.cybersoft.cozastore.payload.request.SignUpRequest;
import com.cybersoft.cozastore.repository.UserRepository;
import com.cybersoft.cozastore.service.imp.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService implements LoginServiceImp {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean insertUser(SignUpRequest signUpRequest) {
        boolean isSuccess = false;

        UserEntity user = new UserEntity();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setUsername(signUpRequest.getUsername());

        try {
            userRepository.save(user);
            isSuccess = true;
        } catch (Exception e){
            System.out.println("Them that bai " + e.getLocalizedMessage());
            isSuccess = false;
        }

        return isSuccess;
    }

}
