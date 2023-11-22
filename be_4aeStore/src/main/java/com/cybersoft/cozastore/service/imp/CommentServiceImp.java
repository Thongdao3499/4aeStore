package com.cybersoft.cozastore.service.imp;

import com.cybersoft.cozastore.entity.CommentEntity;
import com.cybersoft.cozastore.payload.request.CreateCommentRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

public interface CommentServiceImp {

    //add
    boolean insertComment(String name, String content, Date create_date)
            throws IOException;

    //update
    CommentEntity updateComment(int id, CreateCommentRequest request)
        throws ChangeSetPersister.NotFoundException;

    //delete
    void deleteComment(int id);

}
