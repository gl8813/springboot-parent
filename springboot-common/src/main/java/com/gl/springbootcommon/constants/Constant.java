package com.gl.springbootcommon.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Configuration
@ConfigurationProperties(prefix = "constant")
@PropertySource(value = "classpath:/constant.properties")
@Data
//@Component
public class Constant {
    private String name;

    private String sex;
}
