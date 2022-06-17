package com.example.spring0617;

import com.example.spring0617.auth.config.properties.AppProperties;
import com.example.spring0617.auth.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		CorsProperties.class,
		AppProperties.class
})
public class Spring0617Application {
	public static void main(String[] args) {
		SpringApplication.run(Spring0617Application.class, args);
	}
}
