package org.cellcore.code.config;

import org.cellcore.code.service.ServiceMarker;
import org.cellcore.code.shared.SessionContextMarker;
import org.codehaus.jackson.map.DeserializationConfig;
import org.springframework.context.annotation.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
/**
 * enable MVC context
 */
@EnableWebMvc
/**
 * use a placeholder marker instead of a string to define the packages to scan for components
 * avoid to scan Configurations
 */
@ComponentScan(
        basePackageClasses = {ServiceMarker.class, SessionContextMarker.class},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class)}
)
/**
 * import the configuration defined in other Spring configuration beans
 */
@Import({JpaContextConfig.class})
public class MvcContextConfig extends WebMvcConfigurerAdapter {
    @Bean
    public MappingJacksonJsonView mappingJacksonJsonView() {
        MappingJacksonJsonView mappingJacksonJsonView = new MappingJacksonJsonView();
        return mappingJacksonJsonView;
    }

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new MappingJacksonHttpMessageConverter());
    }

    /**
     * expose web resources
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    /**
     * redirect bare base url to index.html
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
          registry.addViewController("/").setViewName("redirect:index.html");
    }


    /**
     * add json serialization
     * @return
     */
    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver() {
        Map<String, String> mediaTypes = new HashMap<String, String>();
        mediaTypes.put("json", "application/json");
        org.springframework.web.servlet.view.ContentNegotiatingViewResolver contentNegotiatingViewResolver = new ContentNegotiatingViewResolver();
        contentNegotiatingViewResolver.setMediaTypes(mediaTypes);
        return contentNegotiatingViewResolver;
    }

    /**
     * specify an object mapper
     * @return
     */
    @Bean
    public org.codehaus.jackson.map.ObjectMapper objectMapper() {
        org.codehaus.jackson.map.ObjectMapper objectMapper = new org.codehaus.jackson.map.ObjectMapper();
        /**
         * specify what happens when there are unknown properties
         */
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    /**
     * MVC views are jsp files
     * @return
     */
    @Bean
    public InternalResourceViewResolver configureInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        /**
         * this line is not necessary, but it's the one that should be changed to use another
         * template technology
         */
        resolver.setViewClass(JstlView.class);
        resolver.setSuffix(".jsp");
        return resolver;
    }

}
