package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Models.Service;
import ba.unsa.etf.pnwt.petcenter.Repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ServiceController {
    @Autowired
    private ServiceRepository serviceRepository;



    @GetMapping(path="/services")
    public @ResponseBody Iterable<Service> getService(){
        return serviceRepository.findAll();
    }

    @PostMapping("/service")
    Service newService( @RequestBody Service service) {
        return serviceRepository.save(service);
    }
}
