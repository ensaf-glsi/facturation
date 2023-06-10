package com.ensaf.facturation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Getter;
import lombok.experimental.Delegate;

public class ApplicationConfig {

    @Delegate
	private Properties properties;
    
    @Getter
    private static ApplicationConfig instance = new ApplicationConfig();
    
    private ApplicationConfig() {
    	try {
            properties = new Properties();
            InputStream inputStream = ApplicationConfig.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
	}

//    public static String getProperty(String key) {
//        return properties.getProperty(key);
//    }
}
