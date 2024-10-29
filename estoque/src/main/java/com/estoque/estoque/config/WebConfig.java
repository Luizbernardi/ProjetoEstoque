package com.estoque.estoque.config;

import com.estoque.estoque.converter.StringToDoubleConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToDoubleConverter stringToDoubleConverter;

    public WebConfig(StringToDoubleConverter stringToDoubleConverter) {
        this.stringToDoubleConverter = stringToDoubleConverter;
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addConverter(stringToDoubleConverter);
    }
}