package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CategoryEntity;
import com.cybersoft.cozastore.payload.request.CreateCategoryRequest;
import com.cybersoft.cozastore.repository.CategoryRepository;
import com.cybersoft.cozastore.service.imp.CategoryServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CategoryServie implements CategoryServiceImp {

    @Autowired
     private CategoryRepository categoryRepository;


    @Override
    public List<CategoryEntity> findAll() {
        List<CategoryEntity> list = categoryRepository.findAll();

        return list;
    }
    @Override
    public CategoryEntity createCategory(CreateCategoryRequest request) {
        CategoryEntity category = new CategoryEntity();
        category.setName(request.getName());
        categoryRepository.save(category);
        return category;
    }

    @Override
    public CategoryEntity updateCategory(int id, CreateCategoryRequest request) {
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isPresent()) {
            CategoryEntity foundCategory = optionalCategory.get();
            foundCategory.setName(request.getName());
            return categoryRepository.save(foundCategory);

        } else {
            System.out.println("Not Fount Category With Id :" + id);
        }
        return null;
    }

    @Override
    public void deleteCategory(int id) {
       this.categoryRepository.deleteById(id);
    }
}
