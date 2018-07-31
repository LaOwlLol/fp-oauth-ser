package fauxpas.server;

import com.google.cloud.datastore.DatastoreOptions;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Server {

    public static void main(String[] args) {

        SpringApplication.run(Server.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            ObjectifyService.init(new ObjectifyFactory(
                  DatastoreOptions.newBuilder()
                        .setHost("http://localhost:8081")
                        .setProjectId("fp-oauth-ser-dev")
                        .build()
                        .getService()
            ));

        };
    }

}
