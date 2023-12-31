package com.cybersoft.cozastore.service.imp;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileServiceImp {

    String uploadFile(MultipartFile file) throws IOException;

    byte[] downloadFile(String fileName) throws IOException;

}
