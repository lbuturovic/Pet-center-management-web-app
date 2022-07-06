package ba.unsa.etf.pnwt.petcenter;

//import ba.unsa.etf.pnwt.Interceptor.SystemEventHandlerInterceptor;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//@EnableRabbit
@SpringBootApplication
@Configuration
public class PetCenterManagementServiceApplication {

    @LoadBalanced
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
/*
    @Bean
    public SystemEventHandlerInterceptor systemEventsHandlerInterceptor(){
        return new SystemEventHandlerInterceptor();
    }
    @Bean
    public WebMvcConfigurer adapter() {
        return new WebMvcConfigurer() {

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(systemEventsHandlerInterceptor());
            }
        };
    }*/

    public static void main(String[] args) {
        SpringApplication.run(PetCenterManagementServiceApplication.class, args);
    }

}
