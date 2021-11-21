package meetup.demo.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableScheduling
@EnableCaching
@ComponentScan(basePackages = {"meetup.demo.*"})
public class AppConfig implements WebMvcConfigurer{
    

}
