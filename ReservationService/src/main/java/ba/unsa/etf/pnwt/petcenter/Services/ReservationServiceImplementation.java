package ba.unsa.etf.pnwt.petcenter.Services;


import ba.unsa.etf.pnwt.petcenter.Exception.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Models.Center;
import ba.unsa.etf.pnwt.petcenter.Models.Reservation;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Models.Visit;
import ba.unsa.etf.pnwt.petcenter.Repositories.CenterRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.ReservationRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.ServiceRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ReservationServiceImplementation implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Iterable<Reservation> findReservationsForCenter(Integer id) {
        return reservationRepository.findReservationsForCenter(id);
    }

    @Override
    public Iterable<Reservation> findReservationsForService(Integer id) {
        return reservationRepository.findReservationsForService(id);
    }

    @Override
    public Iterable<Reservation> findReservationsForUser(Integer id) {
        return reservationRepository.findReservationsForUser(id);
    }

    @Override
    public Reservation addReservation(Reservation reservation) {
        HashMap<String,Integer> params = new HashMap<>();
        params.put("id", (int) reservation.getService().getId());
        ResponseEntity<ba.unsa.etf.pnwt.petcenter.Models.Service> response = restTemplate.getForEntity("http://pet-center-management-service/api/service/{id}", ba.unsa.etf.pnwt.petcenter.Models.Service.class,params);
        LocalDateTime end = reservation.getStartDate().plusMinutes(response.getBody().getDuration());
        reservation.setEndDate(end);
        return reservation;
    }


    @Override
    public Reservation addVisit(int centerId, String userId, Visit visit) {
        Reservation r = new Reservation();
        Center c = centerRepository.findById(centerId);
        r.setCenter(centerRepository.findById(centerId));
        User u = userRepository.findByUuid(UUID.fromString(userId));
        if (u != null)
            r.setUser(u);
        else
            throw new NotFoundException("User with  id = " + userId + "does not exist!");
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        r.setStartDate(LocalDateTime.parse(visit.getStartDate(), customFormatter));
        ba.unsa.etf.pnwt.petcenter.Models.Service s = serviceRepository.findByName("Posjeta");
        LocalDateTime end = r.getStartDate().plusMinutes(s.getDuration());
        r.setEndDate(end);
        if (s!=null)
        r.setService(s);
        reservationRepository.save(r);
        return r;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


}

