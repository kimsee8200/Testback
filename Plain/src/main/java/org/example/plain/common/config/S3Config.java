package org.example.plain.common.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Value(value = "${cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value(value = "${cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value(value = "${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 getS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(secretKey, accessKey);

        return AmazonS3Client.builder()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
