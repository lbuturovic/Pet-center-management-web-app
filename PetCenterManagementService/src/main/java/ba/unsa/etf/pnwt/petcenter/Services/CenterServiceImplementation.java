package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Exception.NotAllowedException;
import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Exception.UserAlreadyAdded;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.PetRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.ServiceRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.MessagingConfig;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.dto.CenterBasic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class CenterServiceImplementation implements CenterService {

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate template;


    public String addCenter(Center center) {
        Iterable<Center> c = null;
        if (center.getUser() != null)
            c = centerRepository.findCenterByUser(center.getUser().getId());
        if (c==null) {
            try {
                center.setPetNo(0);
                center.setActive(true);
                Center _center = centerRepository.save(center);
                Integer centerId = _center.getId();
                CenterBasic info = new CenterBasic(centerId, _center.getCity() + " " + _center.getAddress(), "add");
                template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, info);
                template.convertAndSend(MessagingConfig.EXCHANGE_CENTER_RESERVATION, MessagingConfig.ROUTING_KEY_CENTER_RESERVATION, info);
            }
            catch (Exception e){
                throw new NotFoundException("Error!");
            }
            return "Center successfully added!";
        } else {
            throw new UserAlreadyAdded("User has already been added to other center!");
        }
    }

    @Override
    public String updateCenter(Integer id, Center center) {
        Center c = centerRepository.findById(id).orElseThrow(() -> new NotFoundException("Center with id = " + id + " does not exist!"));
        c.setCapacity(center.getCapacity());
        c.setPetNo(center.getPetNo());
        c.setCity(center.getCity());
        c.setAddress(center.getAddress());
        c.setPhoneNumber(center.getPhoneNumber());
        centerRepository.save(c);
        return "Center with id = " + id +" successfully updated!";
    }

    @Transactional
    @Override
    public String deleteCenter(Integer id) {
     /* Center c = centerRepository.findById(id).orElseThrow(() -> new NotFoundException("Center with id = " + id + " does not exist!"));
        for (ba.unsa.etf.pnwt.petcenter.Models.Service s: c.getServices()) {
            s.getCenters().remove(c);
            c.getServices().remove(s);
            serviceRepository.save(s);
        }
        centerRepository.deleteById(id);*/
        Center c = centerRepository.findById(id).orElseThrow(() -> new NotFoundException("Center with id = " + id + " does not exist!"));
        c.setActive(false);
        centerRepository.save(c);
        CenterBasic info = new CenterBasic(id, "", "delete");
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, info);
        template.convertAndSend(MessagingConfig.EXCHANGE_CENTER_RESERVATION, MessagingConfig.ROUTING_KEY_CENTER_RESERVATION, info);
        return "Center with id = " + id +" successfully deleted!";
    }

    @Override
    public Iterable<Center> getCenters() {
        return centerRepository.findByActive(true);
    }

    public Center getCenter(Integer id) {
        return centerRepository.findById(id).orElseThrow(()->new NotFoundException("Center with id = " + id + " does not exist!"));
    }

    @Override
    public Iterable<Center> findCenterByServiceId(Integer id) {
        return centerRepository.findCenterByService(id);
    }

    @Override
    public void updateCenterPatched(Center centerPatched) {
        centerRepository.save(centerPatched);
    }

    @Override
    public String addPetToCenter(Integer centerId, Integer petId) {
        Center c = centerRepository.findById(centerId).orElseThrow(()->new NotFoundException("Center with id = " + centerId + " does not exist!"));
        Pet p = petRepository.findById(petId).orElseThrow(()->new NotFoundException("Pet with id = " + petId + " does not exist!"));
        if (c.getPetNo() == c.getCapacity())
            return "The center with id = " + centerId + " is full!";
        p.setCenter(c);
        int petNo = c.getPetNo();
        c.setPetNo(petNo + 1);
        petRepository.save(p);
        centerRepository.save(c);
        return "Pet with id = " + petId + " successfully added in center with id = " + centerId + "!";

    }

    @Override
    public Map<String, Object> checkIfCenterIsFree(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        Center c = centerRepository.findById(id).orElseThrow(()->new NotFoundException("Center with id = " + id + " does not exist!"));
        if (c.getPetNo() == c.getCapacity()){
            map.put("avaliable",false);
            return map;
        }
        map.put("avaliable",true);
        return map;
    }

    @Override
    public String addUserForCenter(Integer centerId, Integer userId) {
        Center c = centerRepository.findById(centerId).orElseThrow(()->new NotFoundException("Center with id = " + centerId + " does not exist!"));
        User u = userRepository.findById(userId).orElseThrow(()->new NotFoundException("User with id = " + userId + " does not exist!"));
        HashMap<String, UUID> params = new HashMap<>();
        params.put("id", UUID.fromString(u.getUUID()));
        ResponseEntity<String> response = restTemplate.getForEntity("http://user-service/api/users/{id}", String.class, params);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        JsonNode roleNode = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        roleNode = root.path("role");
        JsonNode role = roleNode.path("name");
        if (!role.asText().equals("Zaposlenik"))
            throw new NotAllowedException("Ne možete dodati user-a koji nije zaposlenik u centar!");
        c.setUser(u);
        centerRepository.save(c);
        return "User with id = " + userId + " successfully added to center with id = " + centerId + "!";
    }
}
