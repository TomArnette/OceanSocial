package com.revature.ocean.services;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.revature.ocean.models.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service("s3Service")
public class S3Service {

    //We'll probably need these to be environment variables when we deploy but while we're testing this is fine.

    private String awsID = System.getenv("TEAMWATER_ACCESSKEY");
    private String awsKey = System.getenv("TEAMWATER_SECRETKEY");
    private String region = "us-east-2";
    private String bucketName = "teamwaterbucket";
    private String bucketUrl = "https://teamwaterbucket.s3.us-east-2.amazonaws.com/";

    //https://teamwaterbucket.s3.us-east-2.amazonaws.com/users/UploadImageTestUser/images/ImageTest.jpg
    //PathName = users/UploadImageTestUser/images/ImageTest.jpg



    BasicAWSCredentials awsCredentials;

    AmazonS3 s3Client;

    public S3Service(){
        System.out.println("S3Service Constructor");

        awsCredentials = new BasicAWSCredentials(awsID, awsKey);

        s3Client = AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();

    }

    // changed to return Response instead of String - Trevor
    private Response uploadFile(File file, String pathName){
        System.out.println("S3service.uploadFile");
        s3Client.putObject(bucketName, pathName, file);

        // once the file is uploaded to amazon, we no longer need it on the server - Trevor
        file.delete();

        // changed to return response - Trevor
        return new Response(true, "image uploaded",bucketUrl+pathName);
    }

    //The pathname example in kevin's explanation of this concept was:
    //"/kevsfolder/"+file.getName()
    //The files, images, path names etc will probably upload to a user's personal folder.

    //Which means that the path we're following would go /username/file type"+file.getName()
    //For example, a profile picture for user SomeGuy would go:
    //"/SomeGuy/profile.[file extension]

    // made same changes in this method and uploadImage() - Trevor
    public Response uploadProfileImage(MultipartFile multipartFile){
        File file = convertMultiPartFileToFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();

        System.out.println("S3service.uploadProfileImage");
        String pathName = "users/images/profile/" + fileName;
        return uploadFile(file, pathName);
    }

    public Response uploadImage(MultipartFile multipartFile){
        // added these 2 lines to convert and get file name - Trevor
        File file = convertMultiPartFileToFile(multipartFile);
        String fileName = multipartFile.getOriginalFilename();

        System.out.println("S3service.uploadImage");
        // no longer have the username here so adjusted pathName - Trevor
        String pathName = "users/images/" + fileName;
        return uploadFile(file, pathName);
    }

    // added this method for file conversion - Trevor
    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error in S3Service.convertMultiPartFileToFile");
        }
        return convertedFile;
    }

    // not using for now - Trevor
    /*public String uploadVideo(String username, File file){
        System.out.println("S3service.uploadVideo");
        String pathName = "users/" + username + "/videos/" + file.getName();
        return uploadFile(file, pathName);

    }*/

    // Not using for now - Trevor
    /*public String uploadProfileImage(User user, File file){
        return uploadProfileImage(user.getUsername(), file);
    }

    public String uploadImage(User user, File file){
        return uploadImage(user.getUsername(), file);
    }

    public String uploadVideo(User user, File file){
        return uploadVideo(user.getUsername(), file);
    }*/

}
