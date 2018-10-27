package pl.smartexplorer.cerber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author
 * Karol Meksuła
 * 21-10-2018
 * */

@EnableJpaRepositories
@SpringBootApplication
public class CerberApplication {

    public static void main(String[] args) {
        SpringApplication.run(CerberApplication.class, args);
    }

}
