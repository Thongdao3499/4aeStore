package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllProduct(){

        return new ResponseEntity<>("Hello Product", HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam String name, @RequestParam MultipartFile file,
                                           @RequestParam double price, @RequestParam int quanity,
                                           @RequestParam int idColor, @RequestParam int idSize,
                                           @RequestParam int idCategory
    ) throws IOException {

        boolean isSuccess = productServiceImp.insertProduct(name, file, price, quanity, idColor, idSize, idCategory);

        return new ResponseEntity<>("Insert Product", HttpStatus.OK);
    }

}
