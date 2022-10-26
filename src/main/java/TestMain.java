import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TestMain {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException, ServerException, InsufficientDataException, ErrorResponseException, InvalidResponseException, XmlParserException, InternalException {

        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("https://play.min.io")
                        .credentials("Q3AM3UQ867SPQQA43P2F", "zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG")
                        .build();

        /* Amazon S3: */
        // MinioClient minioClient =
        //     MinioClient.builder()
        //         .endpoint("https://s3.amazonaws.com")
        //         .credentials("YOUR-ACCESSKEY", "YOUR-SECRETACCESSKEY")
        //         .build();

        {
            // Lists objects information.
            Iterable<Result<Item>> results =
                    minioClient.listObjects(ListObjectsArgs.builder().bucket("test-bucket").build());

            for (Result<Item> result : results) {
                Item item = result.get();
                System.out.println( item.size() + "\t" + item.objectName());
            }
        }
    }
}
