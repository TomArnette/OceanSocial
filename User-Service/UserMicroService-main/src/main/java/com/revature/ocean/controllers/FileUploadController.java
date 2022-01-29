package com.revature.ocean.controllers;

import com.revature.ocean.models.*;
import com.revature.ocean.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("fileUploadController")
public class FileUploadController {

    private S3Service s3Service;

    @Autowired
    public FileUploadController(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @PostMapping("/profile")
    public ResponseEntity<Response> uploadProfileImage(@RequestParam(value = "file")MultipartFile file) {
        return new ResponseEntity<Response>(s3Service.uploadProfileImage(file), HttpStatus.OK);
    }

}