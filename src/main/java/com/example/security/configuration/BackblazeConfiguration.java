package com.example.security.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "backblaze.b2")
@Data
public class BackblazeConfiguration {

    private String accountId;
    private String applicationKeyId;
    private String applicationKey;

}
