package ba.unsa.etf.pnwt.petcenter.Controller;

import ba.unsa.etf.pnwt.petcenter.Model.SystemEvent;
import ba.unsa.etf.pnwt.petcenter.Repository.SystemEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class SystemEventController {

    @Autowired
    private SystemEventRepository systemEventRepository;

    @GetMapping(path = "/system-events")
    public  @ResponseBody
    Iterable<SystemEvent> getAllSystemEvents(){
        return systemEventRepository.findAll();
    }
}
