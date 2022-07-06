package ba.unsa.etf.pnwt.petcenter.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String QUEUE = "center";
    public static final String EXCHANGE = "center_exchange";
    public static final String ROUTING_KEY = "center_routingKey";

    public static final String REVERSE_QUEUE = "revert_center";
    public static final String REVERSE_EXCHANGE = "revert_center_exchange";
    public static final String REVERSE_ROUTING_KEY = "revert_center_routingKey";


    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    @Bean
    public Queue queueR() {
        return new Queue(REVERSE_QUEUE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public TopicExchange exchangeR() {
        return new TopicExchange(REVERSE_EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingR(Queue queueR, TopicExchange exchangeR) {
        return BindingBuilder.bind(queueR).to(exchangeR).with(REVERSE_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}


