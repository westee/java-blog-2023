package hello.integration;

import hello.HelloApplication;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.inject.Inject;
import java.io.IOException;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HelloApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test-application.yml")
public class MyIntegrationTest {
    @Inject
    Environment environment;

    @Test
    public void testNotLogin() {
        String port = environment.getProperty("local.server.port");
        System.out.println();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://localhost:" + port + "/auth")
                .build();

        try (
                Response response = client.newCall(request).execute()
        ) {
            Assertions.assertTrue(response.body().string().contains("未登录"));
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
