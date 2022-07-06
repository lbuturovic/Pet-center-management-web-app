package ba.unsa.etf.pnwt.petcenter;

//import ba.unsa.etf.pnwt.Interceptor.SystemEventHandlerInterceptor;
import ba.unsa.etf.pnwt.petcenter.Exceptions.RestTemplateResponseErrorHandler;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableRabbit
@Configuration
@SpringBootApplication //(exclude = {DataSourceAutoConfiguration.class })
public class ReportPetServiceApplication {
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
        RestTemplate restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
        return restTemplate;
    }
  /*  @Bean
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
        SpringApplication.run(ReportPetServiceApplication.class, args);
    }
}
