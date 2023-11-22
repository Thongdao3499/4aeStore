package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.BlogEntity;
import com.cybersoft.cozastore.entity.CommentEntity;
import com.cybersoft.cozastore.payload.request.CreateBlogRequest;
import com.cybersoft.cozastore.repository.BlogRepository;
import com.cybersoft.cozastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BlogService implements BlogServiceImp {

    @Value("${root.folder}")
    private String rootFolder;

    @Autowired
    private BlogRepository blogRepository;

    @Override
    public boolean insertBlog(MultipartFile file, String title, String content, Date createDate) throws IOException {

        String pathImage = rootFolder + "\\" + file.getOriginalFilename();
        Path path = Paths.get(rootFolder);
        Path pathImageCopy = Paths.get(pathImage);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        Files.copy(file.getInputStream(), pathImageCopy, StandardCopyOption.REPLACE_EXISTING);

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setTitle(title);
        blogEntity.setContent(content);
        blogEntity.setImage(file.getOriginalFilename());
        blogEntity.setCreateDate(createDate);

        blogRepository.save(blogEntity);

        return false;
    }

    @Override
    public BlogEntity updateBlog(int id, CreateBlogRequest request) throws ChangeSetPersister.NotFoundException {
        BlogEntity blog = blogRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setImage(request.getImage());

        try {
            blogRepository.save(blog);
        } catch (Exception e){
            throw new RuntimeException("Error saving blog: " + e.getMessage());
        }
        return blog;
    }

    @Override
    public void deleteBlog(int id) {
        this.blogRepository.deleteById(id);
    }

    @Override
    public List<BlogEntity> getAllBlog() {
        return blogRepository.findAll();
    }

    @Override
    public List<BlogEntity> searchBlog(String keyword) {
        return blogRepository.findByName(keyword);
    }
}
