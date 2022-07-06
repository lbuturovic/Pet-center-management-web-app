package ba.unsa.etf.pnwt.petcenter.rabbitmq;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Service.CenterService;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.dto.CenterBasic;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
public class QueueConsumer {

    @Autowired
    CenterService centerService;
    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void receive(CenterBasic centerInfo) {
        Center center = new Center(centerInfo.getName(), centerInfo.getId());
        if (centerInfo.getMessage().equals("add")) {

            try {
                centerService.createCenter(center);
            } catch (Exception e) {
                System.out.println("Greska u dodavanju!");
                System.out.println(e.getMessage());
                CenterBasic info = new CenterBasic(centerInfo.getId(), centerInfo.getName(), "delete");
                template.convertAndSend(MessagingConfig.REVERSE_EXCHANGE, MessagingConfig.REVERSE_ROUTING_KEY, info);

            }
        }
        else if (centerInfo.getMessage().equals("delete"))
        {
            try {
                centerService.deleteCentar(centerInfo.getId());
            } catch (Exception e) {
                System.out.println("Greska u briss!");
                System.out.println(e.getMessage());

            }
        }
    }

}
