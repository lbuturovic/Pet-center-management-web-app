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

    public static final String QUEUE_USER_DESIRED = "user_desired";
    public static final String EXCHANGE_USER_DESIRED = "user_exchange_desired";
    public static final String ROUTING_KEY_USER_DESIRED = "user_routingKey_exchange_desired";

    @Bean
    public Queue queueUserDesired() {
        return new Queue(QUEUE_USER_DESIRED);
    }

    @Bean
    public TopicExchange exchangeUserDesired() {
        return new TopicExchange(EXCHANGE_USER_DESIRED);
    }

    @Bean
    public Binding bindingUserDesired(Queue queueUserDesired, TopicExchange exchangeUserDesired) {
        return BindingBuilder.bind(queueUserDesired).to(exchangeUserDesired).with(ROUTING_KEY_USER_DESIRED);
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

