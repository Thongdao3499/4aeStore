package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.payload.request.CreateCategoryRequest;
import com.cybersoft.cozastore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryServiceImp categoryServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getListCategory() {
        List<CategoryEntity> categoryEntities = categoryServiceImp.findAll();
        return ResponseEntity.ok(categoryEntities);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody CreateCategoryRequest request) {
        CategoryEntity category = categoryServiceImp.createCategory(request);
        return ResponseEntity.ok(category);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @RequestBody CreateCategoryRequest request) {
        CategoryEntity categoryEntity = categoryServiceImp.updateCategory(id,request);
        return new ResponseEntity<>("Update Category Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory (@PathVariable int id){
        categoryServiceImp.deleteCategory(id);
        return new ResponseEntity<>("Delete Category Successfully", HttpStatus.OK);
    }
}