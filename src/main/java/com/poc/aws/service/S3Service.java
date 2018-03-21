package com.poc.aws.service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.poc.aws.config.ApplicationConfig;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class S3Service {

    private final ApplicationConfig applicationConfig;

    public S3Service(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
    }

    public AmazonS3 getS3Client() {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(applicationConfig.getCMMS().getAws().getAccess_key_id(), applicationConfig.getCMMS().getAws().getSecret_access_key());
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(applicationConfig.getCMMS().getAws().getRegion()))
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
                .build();

        return s3Client;
    }

    public String uploadToS3(String fileName, String fileExt, String base64) {
        String imageUrl = "";
        try {
            File file = createTempFile(fileName, fileExt, base64);
            AmazonS3 s3Client = getS3Client();
            s3Client.putObject(new PutObjectRequest(applicationConfig.getCMMS().getAws().getBucket(), fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
            imageUrl = s3Client.getUrl(applicationConfig.getCMMS().getAws().getBucket(), fileName).toString();
        } catch(AmazonServiceException ase) {
            System.out.println("error uploading to s3");
        } catch (AmazonClientException ace) {
            System.out.println("AmazonClientException to s3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageUrl;
    }

    private File createTempFile(String fileName, String fileExt, String base64) throws IOException {
        Path path = Files.createTempFile(fileName, fileExt);
        File file = path.toFile();
        byte[] fileBytes = DatatypeConverter.parseBase64Binary(base64);
        Files.write(path, fileBytes);
        System.out.println("path :: " + file.getAbsolutePath());
        file.deleteOnExit();
        return file;
    }
}
