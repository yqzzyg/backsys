package com.neusoft.mid.ec.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@Configuration
@PropertySources({@PropertySource("config/socialSecurity-${spring.profiles.active}.properties"),
        @PropertySource("config/socialSecurityCard-${spring.profiles.active}.properties"),
        @PropertySource("config/assess-${spring.profiles.active}.properties"),
        @PropertySource("config/assess-${spring.profiles.active}.properties"),
        @PropertySource("config/employment-${spring.profiles.active}.properties"),
        @PropertySource("config/judicial-${spring.profiles.active}.properties"),
        @PropertySource("config/tax-${spring.profiles.active}.properties"),
        @PropertySource("config/health-${spring.profiles.active}.properties"),
        @PropertySource("config/edu-${spring.profiles.active}.properties"),
        @PropertySource("config/essc-${spring.profiles.active}.properties"),
        @PropertySource("config/jgQuery-${spring.profiles.active}.properties"),
        @PropertySource("config/province-${spring.profiles.active}.properties"),
        @PropertySource("config/construction-${spring.profiles.active}.properties"),
        @PropertySource("config/civilAdministration-${spring.profiles.active}.properties"),
        @PropertySource("config/reservedFound-${spring.profiles.active}.properties"),
        @PropertySource("config/householdAdministration-${spring.profiles.active}.properties")})
public class PropertiesConfig {


    public PropertiesConfig(){
    }

}
