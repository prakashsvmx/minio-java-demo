import io.minio.MinioAsyncClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class AsyncClientExampleV2 {
    String endPoint = "https://play.min.io";
    String accessKey = "Q3AM3UQ867SPQQA43P2F";
    String secretKey = "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG";
    String bucketName = "test-java-bucket";
    String objectNameWithPath = " /pictures/Mobile_view.png";
    String filePath = "/Pictures/Mobile_view.png";

    // Create client with credentials.
    MinioAsyncClient minioAsyncClient =
            MinioAsyncClient.builder()
                    .endpoint(endPoint)
                    .credentials(accessKey, secretKey)
                    .build();


    Path path = Paths.get(filePath);
    InputStream inputStream;

    {
        try {
            inputStream = Files.newInputStream(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Future<ObjectWriteResponse> writeObject() throws Exception{

        CompletableFuture<ObjectWriteResponse> orcf = minioAsyncClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectNameWithPath).stream(
                                inputStream, -1, 10485760)
                        .build());
        return orcf;

    }


    public void cleanup(){
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
        System.out.println("Clean up done");
    }

    public static void  main(String args[])   throws IOException, NoSuchAlgorithmException, InvalidKeyException {

        try {
            AsyncClientExampleV2 av2 = new AsyncClientExampleV2();
            Future<ObjectWriteResponse> wr = av2.writeObject();
            ObjectWriteResponse owr = wr.get();
            System.out.println("::Got res"+owr.etag());
            av2.cleanup();
            System.exit(0);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
