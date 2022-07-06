package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CenterController {
    @Autowired
    private CenterRepository centerRepository;

    @GetMapping(path="/centers")
    public @ResponseBody Iterable<Center> getAllCenters(){
        return centerRepository.findAll();
    }

    @PostMapping("/center")
    Center newCenter(@RequestBody Center center) {
        return centerRepository.save(center);
    }
}
