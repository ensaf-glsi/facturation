package com.ensaf.facturation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Getter;
import lombok.experimental.Delegate;

public class AppProperties {

//    @Delegate
	private Properties properties;
    
	@Getter
    private static AppProperties instance = new AppProperties();
    
    private AppProperties() {
    	try {
            properties = new Properties();
            InputStream inputStream = AppProperties.class.getClassLoader().getResourceAsStream("application.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
	}

    public String getProperty(String key) {
		return properties.getProperty(key);
	}

	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
