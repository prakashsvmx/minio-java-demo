import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class PutObject {

    /**
     * MinioClient.putObject() example.
     */
    public static void main(String[] args)
            throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {

            //8.4.0 minio-java sdk
            //minio latest
            //mc mb local/test-java-bucket  - Create the bucket prior to running this

            String endPoint = "http://localhost:9000";
            String accessKey = "minio";
            String secretKey = "minio123";

            String bucketName = "test-java-bucket";
            String objectNameWithPath = " mmtwo/ app /error.png";
            String filePath = "/home/prakash/Pictures/error.png";



            /* play.min.io for test and development. */
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint(endPoint)
                            .credentials(accessKey, secretKey)
                            .build();


            Map<String, String> headers = new HashMap<>();
            // Add custom content type
            headers.put("Content-Type", "image/png");
            // Add storage class
            headers.put("X-Amz-Storage-Class", "REDUCED_REDUNDANCY");

            // Add custom/user metadata
            Map<String, String> userMetadata = new HashMap<>();
            userMetadata.put("My-Project", "Project One");


            InputStream inputStream = Files.newInputStream(Paths.get(filePath));


            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectNameWithPath).stream(
                                    inputStream, inputStream.available(), -1)
                            .headers(headers)
                            .userMetadata(userMetadata)
                            .build());
            inputStream.close();
            System.out.println(" File  uploaded successfully");


        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
        }
    }
}