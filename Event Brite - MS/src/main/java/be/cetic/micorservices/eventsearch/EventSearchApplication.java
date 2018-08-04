package be.cetic.micorservices.eventsearch;

import java.util.HashMap;
import java.util.Map;
import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.component.swagger.DefaultCamelSwaggerServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EventSearchApplication {

    private static final String CAMEL_URL_MAPPING = "/api/*";
    private static final String CAMEL_SERVLET_NAME = "CamelServlet";

    public static void main(String[] args) {
        SpringApplication.run(EventSearchApplication.class, args);
    }
    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean registration =
                new ServletRegistrationBean(new CamelHttpTransportServlet(), CAMEL_URL_MAPPING);
        registration.setName(CAMEL_SERVLET_NAME);
        return registration;
    }

    @Bean
    public ServletRegistrationBean swaggerServlet() {
        ServletRegistrationBean swagger = new ServletRegistrationBean(new DefaultCamelSwaggerServlet(), "/api-doc/*");
        Map<String, String> params = new HashMap<>();
        params.put("base.path", "api");
        params.put("api.title", "EventSearch aPI");
        params.put("api.description", "this is an API to earch for near events");
        params.put("api.termsOfServiceUrl", "termsOfServiceUrl");
        params.put("api.license", "license");
        params.put("api.licenseUrl", "licenseUrl");
        swagger.setInitParameters(params);
        return swagger;
    }
}
