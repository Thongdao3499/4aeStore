package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.BaseResponse;
import com.cybersoft.cozastore.payload.request.SignUpRequest;
import com.cybersoft.cozastore.service.imp.LoginServiceImp;
import com.cybersoft.cozastore.util.JwtHelper;
import com.google.gson.Gson;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.util.List;

@CrossOrigin //cho phép truy cập
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Gson gson = new Gson();

    @Autowired
    private LoginServiceImp loginServiceImp;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestParam String email, @RequestParam String password){

//        SecretKey key =  Keys.secretKeyFor(SignatureAlgorithm.HS256);
//        String secretString = Encoders.BASE64.encode(key.getEncoded());
//        System.out.println("Kiem tra " + secretString);

        UsernamePasswordAuthenticationToken authen = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authen);

        // Lấy danh sách role đã lưu từ Security Context Holder khi AuthenManager chứng thực thành công
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        String jsonRole = gson.toJson(roles);

        String token = jwtHelper.generateToken(jsonRole);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("");
        baseResponse.setData(token);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        boolean isSuccess = loginServiceImp.insertUser(signUpRequest);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("");
        baseResponse.setData(isSuccess);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
