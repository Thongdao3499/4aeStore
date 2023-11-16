package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.request.CreateProductRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductServiceImp {

    ProductEntity updateProduct(int  id, CreateProductRequest request) throws ChangeSetPersister.NotFoundException;

    void deleteProduct(int  id);

//    boolean clearCache();


    List<ProductEntity> searchProducts(String keyword);


    boolean insertProduct(String name, MultipartFile file, double price,
                          int quanity, int idColor, int idSize, int idCategory) throws IOException;

}
