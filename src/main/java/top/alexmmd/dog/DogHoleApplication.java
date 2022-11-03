package top.alexmmd.dog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import top.alexmmd.common.security.annos.EnableSecurity;

/**
 * @author 汪永晖
 */
@SpringBootApplication
@MapperScan("top.alexmmd.dog.dao")
@EnableSecurity
@EnableWebSecurity(debug = true)
public class DogHoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogHoleApplication.class, args);
    }

}
