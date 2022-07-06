package ba.unsa.etf.pnwt.petcenter.Controller;

import ba.unsa.etf.pnwt.petcenter.Model.Center;
import ba.unsa.etf.pnwt.petcenter.Model.Form;
import ba.unsa.etf.pnwt.petcenter.Service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@EnableSwagger2
@RequestMapping(path = "/api")
public class CenterController {

    @Autowired
    CenterService centerService;
    @PostMapping("/center" ) //done
    public ResponseEntity<Center> createCenter(@Valid @RequestBody Center center) {
        return new ResponseEntity<>(centerService.createCenter(center), HttpStatus.CREATED);
    }

    @GetMapping("/centers")
    public ResponseEntity<List<Center>> getAllCenters() {
        List<Center> centers = centerService.getCenters();
        if (centers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(centers, HttpStatus.OK);
    }
}
