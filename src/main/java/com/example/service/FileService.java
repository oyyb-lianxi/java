package com.example.service;

import com.example.model.entity.Result;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    Result uploadFile(MultipartFile file);


}
