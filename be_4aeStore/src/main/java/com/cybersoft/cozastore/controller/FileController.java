package com.cybersoft.cozastore.controller;

import com.cybersoft.cozastore.payload.BaseResponse;
import com.cybersoft.cozastore.service.imp.FileServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileServiceImp fileServiceImp;

    @PostMapping("")
    public ResponseEntity uploadFile(@RequestParam MultipartFile file) throws IOException {
        String message = fileServiceImp.uploadFile(file);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatusCode(200);
        baseResponse.setMessage("Successful");
        baseResponse.setData(message);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadFile(@PathVariable String fileName) throws IOException {
        byte[] fileImage = fileServiceImp.downloadFile(fileName);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(fileImage, headers, HttpStatus.OK);
    }

}
