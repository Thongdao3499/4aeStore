package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.entity.BlogEntity;
import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.request.CreateBlogRequest;
import com.cybersoft.cozastore.payload.request.CreateProductRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface BlogServiceImp {

    //add
    boolean insertBlog(MultipartFile file, String title, String content, Date createDate)
            throws IOException;

    //update
    BlogEntity updateBlog(int id, CreateBlogRequest request)
            throws ChangeSetPersister.NotFoundException;

    //delete
    void deleteBlog(int id);

    //get all
    List<BlogEntity> getAllBlog();

    //search
    List<BlogEntity> searchBlog(String keyword);

}
