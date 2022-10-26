import com.ea.async.Async;
import io.minio.MinioAsyncClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;

public class AsyncClientExample {

    public static void  main(String args[])   throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        try {
 /*   String endPoint = "http://localhost:9000";
    String accessKey = "minio";
    String secretKey = "minio123";

    String bucketName = "test-java-bucket";
    String objectNameWithPath = " mmtwo/ app /error.png";
    String filePath = "/home/prakash/Pictures/error.png";*/

            String endPoint = "https://play.min.io";
            String accessKey = "Q3AM3UQ867SPQQA43P2F";
            String secretKey = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG";

            String bucketName = "test-java-bucket";
            String objectNameWithPath = " /pictures/Mobile_view.png";
            String filePath = "/home/prakash/Pictures/Mobile_view.png";

            // Create client with credentials.
            MinioAsyncClient minioAsyncClient =
                    MinioAsyncClient.builder()
                            .endpoint(endPoint)
                            .credentials(accessKey, secretKey)
                            .build();


            Path path = Paths.get(filePath);
            InputStream inputStream = Files.newInputStream(path);

            CompletableFuture<ObjectWriteResponse> future =  minioAsyncClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectNameWithPath).stream(
                                    inputStream, -1, 10485760)
                            .build());


            ObjectWriteResponse wRes = future.get();
            System.out.println(wRes.etag());
            System.out.println( wRes.object());



            future.thenApply((or) -> {
                or.etag();

                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Inner DOne....");
                return or;

            } );


            System.out.println("Join  DOne....");
            future.join();
            System.out.println("Outer DOne....");


        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
