package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.payload.request.SignUpRequest;

public interface LoginServiceImp {

    boolean insertUser(SignUpRequest signUpRequest);

}
