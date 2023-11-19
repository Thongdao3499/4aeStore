package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.payload.request.CreateCategoryRequest;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CategoryServiceImp {


    List<CategoryEntity> findAll();

    CategoryEntity createCategory(CreateCategoryRequest request);

    CategoryEntity updateCategory(int id, CreateCategoryRequest request);
    void deleteCategory(int id);

}
