package com.tabia.projeto_tecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProjetoTecnicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoTecnicoApplication.class, args);
	}

}
