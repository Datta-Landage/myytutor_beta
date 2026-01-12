package com.myytutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = "com.myytutor")
public class MyyTutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyyTutorApplication.class, args);
	}

    @org.springframework.context.annotation.Bean
    org.springframework.boot.CommandLineRunner runSlugBackfill(com.myytutor.service.TeacherService teacherService) {
        return args -> {
            try {
                teacherService.backfillSlugs();
            } catch (Exception e) {
                System.err.println("[CRITICAL] Slug backfill failed: " + e.getMessage());
            }
        };
    }

}
