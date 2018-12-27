package com.project;

import com.project.data.jpa.GenericRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@ComponentScan("com.project")
@EnableJpaRepositories(basePackages = {"com.project.*"},
		repositoryFactoryBeanClass = GenericRepositoryFactoryBean.class)
@EntityScan("com.project")
public class SearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchApplication.class, args);
	}

}

