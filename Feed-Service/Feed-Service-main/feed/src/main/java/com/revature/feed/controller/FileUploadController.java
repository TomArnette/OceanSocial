package com.revature.feed.controller;

import com.revature.feed.models.*;
import com.revature.feed.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
/**
 * @author Andrew Patrick
 * @author Ezequiel Flores
 * @author Joan Gorsky
 * @author Shane Danner
 * @author Thanh Nguyen
 */
@RestController("fileUploadController")
public class FileUploadController {

    private S3Service s3Service;

    @Autowired
    public FileUploadController(S3Service s3Service){this.s3Service = s3Service;}

    @Autowired
    private Environment environment;
    /**
     * @param file
     *         -File being sent from Front-end to upload to S3 bucket for users' profile picture.
     * @return the url string to the S3 image location.
     */
    @PostMapping("/profile")
    public ResponseEntity<Response> uploadProfileImage(@RequestParam(value = "file")MultipartFile file) {
        return new ResponseEntity<Response>(s3Service.uploadProfileImage(file), HttpStatus.OK);
    }
    /**
     * @param file
     *         -File being sent from Front-end to upload to S3 bucket for posts' picture.
     * @return the url string to the S3 image location.
     */
    @PostMapping("/image")
    public ResponseEntity<Response> uploadFile(@RequestParam(value = "file")MultipartFile file) {
        return new ResponseEntity<Response>(s3Service.uploadImage(file), HttpStatus.OK);
    }

}
