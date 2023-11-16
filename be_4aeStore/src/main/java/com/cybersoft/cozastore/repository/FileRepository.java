package com.cybersoft.cozastore.repository;

import com.cybersoft.cozastore.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    FileEntity findByName(String fileName);

}
