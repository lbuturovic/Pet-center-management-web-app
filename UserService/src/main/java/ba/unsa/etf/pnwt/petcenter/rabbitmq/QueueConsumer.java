/*package ba.unsa.etf.pnwt.petcenter.rabbitmq;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import ba.unsa.etf.pnwt.petcenter.Services.CenterService;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.dto.CenterBasic;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.dto.UserBasic;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class QueueConsumer {

    @Autowired
    CenterService centerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = MessagingConfig.QUEUE_CENTER_RESERVATION)
    public void receiveCenter(CenterBasic centerInfo) {
        Center center = new Center(centerInfo.getName(), centerInfo.getId());
        if (centerInfo.getMessage().equals("add")) {

            try {
                centerService.createCenter(center);
            } catch (Exception e) {
                System.out.println("Greska u dodavanju!");
                System.out.println(e.getMessage());
                CenterBasic info = new CenterBasic(centerInfo.getId(), centerInfo.getName(), "delete");
                template.convertAndSend(MessagingConfig.REVERSE_EXCHANGE_CENTER_RESERVATION, MessagingConfig.REVERSE_ROUTING_KEY_CENTER_RESERVATION, info);

            }
        }
        else if (centerInfo.getMessage().equals("delete"))
        {
            try {
                centerService.deleteCentar(centerInfo.getId());
            } catch (Exception e) {
                System.out.println("Greska u brisanju!");
                System.out.println(e.getMessage());

            }
        }
    }

    @RabbitListener(queues = MessagingConfig.QUEUE_USER_RESERVATION)
    public void receiveUser(UserBasic userInfo) {
        User user = new User(userInfo.getId(), userInfo.getEmail(), null);
        if (userInfo.getMessage().equals("add")) {

            try {
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println("Greska u dodavanju!");
            }
        }
        else if (userInfo.getMessage().equals("delete"))
        {
            try {
                userRepository.deleteUserById(userInfo.getId());
            } catch (Exception e) {
                System.out.println("Greska u brisanju!");
                System.out.println(e.getMessage());
            }
        }
    }

}*/
