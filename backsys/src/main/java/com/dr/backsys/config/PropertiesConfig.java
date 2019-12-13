package com.dr.backsys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({@PropertySource("config/HNczrk.properties")})
public class PropertiesConfig {

    public PropertiesConfig(){
    }

}
