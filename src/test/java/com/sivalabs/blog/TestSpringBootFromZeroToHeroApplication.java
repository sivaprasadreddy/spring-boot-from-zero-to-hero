package com.sivalabs.blog;

import org.springframework.boot.SpringApplication;

public class TestSpringBootFromZeroToHeroApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringBootFromZeroToHeroApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
