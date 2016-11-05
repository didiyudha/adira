package com.adira;

import com.adira.service.storage.StorageProperties;
import com.adira.service.storage.StorageService;
import com.adira.service.workbook.WorkbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan
@EnableConfigurationProperties(StorageProperties.class)
public class AdiraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AdiraApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	@Autowired
	WorkbookService workbookService;

	@Override
	public void run(String... strings) throws Exception {
		workbookService.readData("template.xlsx");
	}
}
