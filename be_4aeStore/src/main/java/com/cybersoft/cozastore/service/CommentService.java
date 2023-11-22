package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.CommentEntity;
import com.cybersoft.cozastore.payload.request.CreateCommentRequest;
import com.cybersoft.cozastore.repository.CommentRepository;
import com.cybersoft.cozastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@Service
public class CommentService implements CommentServiceImp {

    @Value("${root.folder}")
    private String rootFolder;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public boolean insertComment(String name, String content, Date create_date) throws IOException {

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(content);
        commentEntity.setCreate_date(create_date);

        commentRepository.save(commentEntity);

        return false;
    }

    @Override
    public CommentEntity updateComment(int id, CreateCommentRequest request) throws ChangeSetPersister.NotFoundException {
        CommentEntity comment = commentRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        comment.setContent(request.getContent());
        comment.setCreate_date(request.getCreate_date());

        try {
            commentRepository.save(comment);
        } catch (Exception e){
            throw new RuntimeException("Error saving comment: " + e.getMessage());
        }
        return comment;
    }

    @Override
    public void deleteComment(int id) {
        this.commentRepository.deleteById(id);
    }
}
