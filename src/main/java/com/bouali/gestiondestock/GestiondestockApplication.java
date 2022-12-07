package com.bouali.gestiondestock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


//@SpringBootApplication
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GestiondestockApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestiondestockApplication.class, args);
		System.out.println("GestiondestockApplication ==> main" );
		System.out.println("2222test push with intelligi222" );
	}

}
