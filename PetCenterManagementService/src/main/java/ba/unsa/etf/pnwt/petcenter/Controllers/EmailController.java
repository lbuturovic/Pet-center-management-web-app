package ba.unsa.etf.pnwt.petcenter.Controllers;


import ba.unsa.etf.pnwt.petcenter.Emails.EmailDetails;
import ba.unsa.etf.pnwt.petcenter.Emails.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableSwagger2
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping("/sendMail")
    public String
    sendMail(@RequestBody EmailDetails details)
    {
        String status
                = emailService.sendSimpleMail(details);

        return status;
    }

}