package com.project.learningz.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "payment.momo")
@Getter
@Setter
public class MomoProperties {
    private String environment;

    private String returnUrl;

    private String notifyURL;
}
