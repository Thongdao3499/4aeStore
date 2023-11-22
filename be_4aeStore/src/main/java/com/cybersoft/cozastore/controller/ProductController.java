package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.request.CreateProductRequest;
import com.cybersoft.cozastore.service.imp.ProductServiceImp;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "http://127.0.0.1:5503", exposedHeaders = {"Access-Control-Allow-Origin"})
public class ProductController {

    @Autowired
    private ProductServiceImp productServiceImp;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts(){
        List<ProductEntity> products = productServiceImp.getAllProduct();
        if(products != null && !products.isEmpty()){
            return new ResponseEntity<>(products,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertProduct(@RequestParam String name, @RequestParam MultipartFile file,
                                           @RequestParam double price, @RequestParam int quantity
    ) throws IOException {

        try {
            boolean isSuccess = productServiceImp.insertProduct(name, file, price, quantity);

            if (isSuccess) {
                return new ResponseEntity<>("Product inserted successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to insert product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Error processing the request: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id){
        productServiceImp.deleteProduct(id);

        return new ResponseEntity<>("Product is deleted", HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int  id,
                                           @RequestBody CreateProductRequest request) throws ChangeSetPersister.NotFoundException {
        ProductEntity product = productServiceImp.updateProduct(id, request);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductEntity>> searchProduct(@RequestParam String keyword){
        List<ProductEntity> list = productServiceImp.searchProducts(keyword);

        return ResponseEntity.ok(list);
    }



}
