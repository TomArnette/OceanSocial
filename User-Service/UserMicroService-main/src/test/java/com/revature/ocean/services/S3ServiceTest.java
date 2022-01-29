package com.revature.ocean.services;

import com.revature.ocean.models.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class S3ServiceTest {
    S3Service s3Service;
    MultipartFile file;
    File convertedFile;
    private String bucketUrl = "https://teamwaterbucket.s3.us-east-2.amazonaws.com/";
/*
... So

S3Service has a method that converts a multipart file into a regular file before uploading

It's private, so it can't be called, so it can't be mocked
* */


    @BeforeEach
    public void Setup() throws IOException {
        s3Service = new S3Service();
        file = mock(MultipartFile.class);
        convertedFile = mock(File.class);
        byte a = 99;
        byte b = 98;
        byte[] c = {a, b};
        //when(FileOutputStream.write(File)).thenReturn(convertedFile);
        when(file.getOriginalFilename()).thenReturn("testFile");
        when(file.getBytes()).thenReturn(c);
    }

    @Test
    public void uploadImageTest(){
        Response response = s3Service.uploadImage(file);
        String expectedResult = bucketUrl + "users/images/" + file.getOriginalFilename();
        //System.out.println("Upload image test: " + response.getData());

        assertEquals(expectedResult, response.getData());
    }

    @Test
    public void uploadProfileImageTest(){
        Response response = s3Service.uploadProfileImage(file);
        String expectedResult = bucketUrl + "users/images/profile/" + file.getOriginalFilename();
        //System.out.println("Upload profile image test: " + response.getData());

        assertEquals(expectedResult, response.getData());
    }
}
