package org.cellcore.code.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(
        basePackageClasses = {},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION,value = Controller.class)
)
public class ContextConfig {
}
