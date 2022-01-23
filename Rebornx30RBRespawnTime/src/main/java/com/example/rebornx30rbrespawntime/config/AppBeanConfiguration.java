package com.example.rebornx30rbrespawntime.config;

import org.modelmapper.ModelMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeanConfiguration {

    @Bean
    public WebDriver driver() {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/static/drivers/geckodriver.exe");
        FirefoxBinary firefoxBinary = new FirefoxBinary();
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);
        options.setHeadless(true);
        return new FirefoxDriver(options);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
