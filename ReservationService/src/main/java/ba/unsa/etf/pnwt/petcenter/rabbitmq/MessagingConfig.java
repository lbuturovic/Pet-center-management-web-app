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

    public static final String QUEUE_CENTER_RESERVATION = "center_reservation";
    public static final String EXCHANGE_CENTER_RESERVATION = "center_exchange_reservation";
    public static final String ROUTING_KEY_CENTER_RESERVATION = "center_routingKey_exchange_reservation";

    public static final String REVERSE_QUEUE_CENTER_RESERVATION = "center_reservation_reverse";
    public static final String REVERSE_EXCHANGE_CENTER_RESERVATION = "center_exchange_reservation_reverse";
    public static final String REVERSE_ROUTING_KEY_CENTER_RESERVATION = "center_routingKey_exchange_reservation_reverse";

    public static final String QUEUE_USER_RESERVATION = "user_reservation";
    public static final String EXCHANGE_USER_RESERVATION = "user_exchange_reservation";
    public static final String ROUTING_KEY_USER_RESERVATION = "user_routingKey_exchange_reservation";

    @Bean
    public Queue queueUserReservation() {
        return new Queue(QUEUE_USER_RESERVATION);
    }

    @Bean
    public TopicExchange exchangeUserReservation() {
        return new TopicExchange(EXCHANGE_USER_RESERVATION);
    }

    @Bean
    public Binding bindingUserReservation(Queue queueUserReservation, TopicExchange exchangeUserReservation) {
        return BindingBuilder.bind(queueUserReservation).to(exchangeUserReservation).with(ROUTING_KEY_USER_RESERVATION);
    }

    @Bean
    public Queue queueReservation() {
        return new Queue(QUEUE_CENTER_RESERVATION);
    }

    @Bean
    public Queue queueReservationR() {
        return new Queue(REVERSE_QUEUE_CENTER_RESERVATION);
    }

    @Bean
    public TopicExchange exchangeReservation() {
        return new TopicExchange(EXCHANGE_CENTER_RESERVATION);
    }

    @Bean
    public TopicExchange exchangeReservationR() {
        return new TopicExchange(REVERSE_EXCHANGE_CENTER_RESERVATION);
    }


    @Bean
    public Binding bindingReservation(Queue queueReservation, TopicExchange exchangeReservation) {
        return BindingBuilder.bind(queueReservation).to(exchangeReservation).with(ROUTING_KEY_CENTER_RESERVATION);
    }

    @Bean
    public Binding bindingReservationR(Queue queueReservationR, TopicExchange exchangeReservationR) {
        return BindingBuilder.bind(queueReservationR).to(exchangeReservationR).with(REVERSE_ROUTING_KEY_CENTER_RESERVATION);
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

