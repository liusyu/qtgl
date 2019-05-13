package qtgl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true)
@Configuration
@ImportResource(locations = {"classpath*:spring/applicationContext*.xml"})
public class WebConfig extends WebMvcConfigurationSupport {
}
