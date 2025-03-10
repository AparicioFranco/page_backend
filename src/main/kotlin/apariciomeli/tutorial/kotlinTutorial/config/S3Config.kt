package apariciomeli.tutorial.kotlinTutorial.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import java.net.URI

@Configuration
class S3Config {

    private final val dotenv = io.github.cdimascio.dotenv.Dotenv.load()
    val accessKey: String = System.getenv("AWS_ACCESS_KEY_ID")
    val secretKey: String = System.getenv("AWS_SECRET_ACCESS_KEY")
    val region: String = System.getenv("AWS_REGION")
//    val accessKey = dotenv["AWS_ACCESS_KEY_ID"] ?: throw IllegalArgumentException("Missing AWS_ACCESS_KEY_ID")
//    val secretKey = dotenv["AWS_SECRET_ACCESS_KEY"] ?: throw IllegalArgumentException("Missing AWS_SECRET_ACCESS_KEY")
//    val region = dotenv["AWS_REGION"] ?: "us-east-1"

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