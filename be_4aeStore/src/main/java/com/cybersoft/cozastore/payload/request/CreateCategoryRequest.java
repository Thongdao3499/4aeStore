package com.cybersoft.cozastore.payload.request;

import com.cybersoft.cozastore.entity.ProductEntity;

import java.util.Date;
import java.util.List;

public class CreateCategoryRequest {

    private String name;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
