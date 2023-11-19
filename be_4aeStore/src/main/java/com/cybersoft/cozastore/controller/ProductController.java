package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.request.CreateProductRequest;
import com.cybersoft.cozastore.service.imp.ProductServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/product")
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
                                           @RequestParam double price, @RequestParam int quantity,
                                           @RequestParam int idColor, @RequestParam int idSize,
                                           @RequestParam int idCategory
    ) throws IOException {

        boolean isSuccess = productServiceImp.insertProduct(name, file, price, quantity, idColor, idSize, idCategory);

        return new ResponseEntity<>("Insert Product", HttpStatus.OK);
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
