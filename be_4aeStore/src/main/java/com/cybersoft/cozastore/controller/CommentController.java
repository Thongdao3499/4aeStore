package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.entity.BlogEntity;
import com.cybersoft.cozastore.entity.CommentEntity;
import com.cybersoft.cozastore.payload.request.CreateBlogRequest;
import com.cybersoft.cozastore.payload.request.CreateCommentRequest;
import com.cybersoft.cozastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

@CrossOrigin
@RestController
@RequestMapping("/blog-detail")
public class CommentController {

    @Autowired
    private CommentServiceImp commentServiceImp;

    @PostMapping("")
    public ResponseEntity<?> insertComment(@RequestParam String name, @RequestParam String content, @RequestParam Date createDate
    ) throws IOException {

        boolean isSuccess = commentServiceImp.insertComment(name, content, createDate);

        return new ResponseEntity<>("Comment posted", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable int id){
        commentServiceImp.deleteComment(id);

        return new ResponseEntity<>("Comment deleted", HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable int id, @RequestBody CreateCommentRequest request
    ) throws ChangeSetPersister.NotFoundException {

        CommentEntity comment = commentServiceImp.updateComment(id, request);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

}
