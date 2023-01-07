package top.alexmmd.dog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import top.alexmmd.common.security.annos.EnableSecurity;

/**
 * @author 汪永晖
 */
@SpringBootApplication
@MapperScan("top.alexmmd.dog.dao")
@EnableSecurity
@EnableScheduling
public class DogHoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogHoleApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
