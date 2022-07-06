package ba.unsa.etf.pnwt.petcenter.rabbitmq;

import ba.unsa.etf.pnwt.petcenter.Services.CenterService;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.MessagingConfig;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.dto.CenterBasic;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

    @Autowired
    CenterService centerService;

    @RabbitListener(queues = MessagingConfig.REVERSE_QUEUE)
    public void receiveCenterReport(CenterBasic centerInfo){
        if (centerInfo.getMessage().equals("delete")) {

            try {
                centerService.deleteCenter(centerInfo.getId());
            } catch (Exception e) {
                System.out.println("Greska u brisanju!");
                System.out.println(e.getMessage());

            }
        }

    }

    @RabbitListener(queues = MessagingConfig.REVERSE_QUEUE_CENTER_RESERVATION)
    public void receiveCenterReservation(CenterBasic centerInfo){
        if (centerInfo.getMessage().equals("delete")) {

            try {
                centerService.deleteCenter(centerInfo.getId());
            } catch (Exception e) {
                System.out.println("Greska u brisanju!");
                System.out.println(e.getMessage());

            }
        }

    }
}
