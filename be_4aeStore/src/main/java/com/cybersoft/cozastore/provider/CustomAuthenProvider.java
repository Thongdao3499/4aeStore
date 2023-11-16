package com.cybersoft.cozastore.provider;

import com.cybersoft.cozastore.entity.UserEntity;
import com.cybersoft.cozastore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //logic xu ly dang nhap
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserEntity user = userRepository.findByEmail(username);
        if (user != null){
            //user ton tai, kiem tra tiep pw
            if(passwordEncoder.matches(password, user.getPassword())){
                //username dung, pw dung
                    //danh sach quyen
                List<GrantedAuthority> roles = new ArrayList<>();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(user.getRole().getName());
                roles.add(grantedAuthority);

                //tao chung thuc
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword(), roles);

                SecurityContextHolder.getContext().setAuthentication(token);

                return token;

            } else{
                return null;
            }

        } else{
            return null;
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
