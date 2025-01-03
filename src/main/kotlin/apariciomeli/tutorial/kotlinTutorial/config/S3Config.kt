package apariciomeli.tutorial.kotlinTutorial.config

import io.github.cdimascio.dotenv.Dotenv
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.S3Configuration
import java.net.URI

@Configuration
class S3Config {
    private final val dotenv: Dotenv = Dotenv.load()

    val accessKey: String = dotenv["AWS_ACCESS_KEY_ID"]
    val secretKey: String = dotenv["AWS_SECRET_ACCESS_KEY"]
    val region: String = dotenv["AWS_REGION"]

    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .region(Region.of(region))
            .endpointOverride(URI.create("https://fra1.digitaloceanspaces.com"))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(accessKey, secretKey)
                )
            )
            .build()
    }
}