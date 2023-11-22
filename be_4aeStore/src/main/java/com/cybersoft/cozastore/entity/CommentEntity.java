package com.cybersoft.cozastore.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "create_date")
    private Date create_date;

    @ManyToOne
    @JoinColumn(name = "id_blog")
    private BlogEntity blog;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public BlogEntity getBlog() {
        return blog;
    }

    public void setBlog(BlogEntity blog) {
        this.blog = blog;
    }
}