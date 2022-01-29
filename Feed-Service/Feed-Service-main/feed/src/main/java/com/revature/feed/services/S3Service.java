package com.revature.feed.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.revature.feed.models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service("s3Service")
public class S3Service {

    private String awsID = System.getenv("TEAMWATER_ACCESSKEY");
    private String awsKey = System.getenv("TEAMWATER_SECRETKEY");
    private String region = "us-east-2";
    private String bucketName = "teamwaterbucket";
    private String bucketUrl = "https://teamwaterbucket.s3.us-east-2.amazonaws.com/";


    BasicAWSCredentials awsCredentials;

    AmazonS3 s3Client;

    public S3Service(){
        awsCredentials = new BasicAWSCredentials(awsID, awsKey);
        s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    // changed to return Response instead of String
    private Response uploadFile(File file, String pathName){
        s3Client.putObject(bucketName, pathName, file);

        // once the file is uploaded to amazon, we no longer need it on the server - Trevor
        file.delete();

        // changed to return response - Trevor
        return new Response(true, "image uploaded",bucketUrl+pathName);
    }


    // Upload Profile picture to S3 and returning URL
    public Response uploadProfileImage(MultipartFile multipartFile){
        File file = convertMultiPartFileToFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        String pathName = "users/images/profile/" + fileName;
        return uploadFile(file, pathName);
    }

    // Upload Post picture to S3 and returning URL
    public Response uploadImage(MultipartFile multipartFile){
        File file = convertMultiPartFileToFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();
        String pathName = "users/images/" + fileName;
        return uploadFile(file, pathName);
    }

    // Sends file to S3
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }

}
