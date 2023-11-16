package com.cybersoft.cozastore.service;

import com.cybersoft.cozastore.entity.FileEntity;
import com.cybersoft.cozastore.repository.FileRepository;
import com.cybersoft.cozastore.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService implements FileServiceImp {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String typeFile = file.getContentType();
        byte[] data = file.getBytes();

        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(fileName);
        fileEntity.setType(typeFile);
        fileEntity.setData(data);

        FileEntity result = fileRepository.save(fileEntity);

        if (result != null) {
            return fileName + " is successfully uploaded";
        }
        return "Failed to upload";
    }

    @Override
    public byte[] downloadFile(String fileName) throws IOException {
        FileEntity file = fileRepository.findByName(fileName);

        return file.getData();
    }

}
