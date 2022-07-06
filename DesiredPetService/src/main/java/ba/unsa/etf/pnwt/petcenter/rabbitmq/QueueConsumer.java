package ba.unsa.etf.pnwt.petcenter.rabbitmq;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.dto.UserBasic;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class QueueConsumer {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = MessagingConfig.QUEUE_USER_DESIRED)
    public void receiveUser(UserBasic userInfo) {
        User user = new User(userInfo.getId());
        user.setEmail(userInfo.getEmail());
        if (userInfo.getMessage().equals("add")) {

            try {
                userRepository.save(user);
            } catch (Exception e) {
                System.out.println("Greska u dodavanju!");
            }
        }
   /*     else if (userInfo.getMessage().equals("delete"))
        {
            try {
                userRepository.deleteUserByUUID(userInfo.getId());
            } catch (Exception e) {
                System.out.println("Greska u brisanju!");
                System.out.println(e.getMessage());
            }
        }*/
    }

}
