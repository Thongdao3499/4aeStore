package com.cybersoft.cozastore.service.imp;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductServiceImp {

    boolean insertProduct(String name, MultipartFile file, double price,
                          int quanity, int idColor, int idSize, int idCategory) throws IOException;

}
