package com.revature.ocean.controllers;

import com.revature.ocean.models.Response;
import com.revature.ocean.services.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileUploadControllerTest {

    S3Service s3Service;
    MultipartFile file;
    File convertedFile;
    FileUploadController fileUploadController;
    private String bucketUrl = "https://teamwaterbucket.s3.us-east-2.amazonaws.com/";

    @BeforeEach
    public void setup() throws IOException {
        s3Service = new S3Service();
        file = mock(MultipartFile.class);
        convertedFile = mock(File.class);
        fileUploadController = new FileUploadController(s3Service);
        byte a = 99;
        byte b = 98;
        byte[] c = {a, b};
        when(file.getOriginalFilename()).thenReturn("testingAgain");
        when(file.getBytes()).thenReturn(c);
    }

    @Test
    public void uploadProfileImageTest(){
        String fileName = bucketUrl + "users/images/profile/" + file.getOriginalFilename();
        //when(s3Service.uploadProfileImage(file)).thenReturn(new Response(true, "image uploaded","test"));
        Response expectedResponse = new Response(true, "image uploaded",fileName);
        ResponseEntity<Response> actualResult = fileUploadController.uploadProfileImage(file);
        ResponseEntity<Response> expectedResult = new ResponseEntity<Response>(expectedResponse, HttpStatus.OK);

        assertEquals(expectedResult, actualResult);
    }
}
