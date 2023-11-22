package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.entity.BlogEntity;
import com.cybersoft.cozastore.entity.ProductEntity;
import com.cybersoft.cozastore.payload.request.CreateBlogRequest;
import com.cybersoft.cozastore.payload.request.CreateProductRequest;
import com.cybersoft.cozastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogServiceImp blogServiceImp;

    @GetMapping("/all")
    public ResponseEntity<?> getAllBlog(){
        List<BlogEntity> blogs = blogServiceImp.getAllBlog();

        if (blogs != null && !blogs.isEmpty()){
            return new ResponseEntity<>(blogs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> insertBlog(@RequestParam MultipartFile file, @RequestParam String title,
                                        @RequestParam String content, @RequestParam Date createDate
    ) throws IOException {

        boolean isSuccess = blogServiceImp.insertBlog(file, title, content, createDate);

        return new ResponseEntity<>("Blog posted", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable int id){
        blogServiceImp.deleteBlog(id);

        return new ResponseEntity<>("Blog deleted", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable int id, @RequestBody CreateBlogRequest request
    ) throws ChangeSetPersister.NotFoundException {

        BlogEntity blog = blogServiceImp.updateBlog(id, request);

        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BlogEntity>> searchProduct(@RequestParam String keyword){
        List<BlogEntity> list = blogServiceImp.searchBlog(keyword);

        return ResponseEntity.ok(list);
    }

}
