package kdu.cse.unispace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class UnispaceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnispaceApplication.class, args);
	}

}