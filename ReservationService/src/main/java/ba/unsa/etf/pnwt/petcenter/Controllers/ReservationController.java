package ba.unsa.etf.pnwt.petcenter.Controllers;

import ba.unsa.etf.pnwt.petcenter.Exception.ApiError;
import ba.unsa.etf.pnwt.petcenter.Models.Reservation;
import ba.unsa.etf.pnwt.petcenter.Models.User;
import ba.unsa.etf.pnwt.petcenter.Models.Visit;
import ba.unsa.etf.pnwt.petcenter.Repositories.ReservationRepository;
import ba.unsa.etf.pnwt.petcenter.Repositories.UserRepository;
import ba.unsa.etf.pnwt.petcenter.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController

public class ReservationController {


    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationService reservationService;

    @GetMapping(path="/reservations")
    public @ResponseBody Iterable<Reservation> getAllReservations(){
        return reservationRepository.findAll();
    }

    @PostMapping("/reservation")
    Reservation newReservation(@RequestBody Reservation reservation) {
        if(reservation.getEndDate()==null) reservationRepository.save(reservationService.addReservation(reservation));
        if(reservation.getEndDate().isBefore(reservation.getStartDate())) throw new ApiError("Validation", "Invalid dates");

        return reservationRepository.save(reservation);

    }

    @PostMapping("/reservation/user/{uuid}")
    Reservation newReservationUser(@PathVariable String uuid, @RequestBody Reservation reservation) {
        if(reservation.getEndDate()==null) reservationRepository.save(reservationService.addReservation(reservation));
        if(reservation.getEndDate().isBefore(reservation.getStartDate())) throw new ApiError("Validation", "Invalid dates");
        User u = userRepository.findByUuid(UUID.fromString(uuid));
        reservation.setUser(u);
        return reservationRepository.save(reservation);

    }

    @PostMapping("/visit/{centerId}/{userId}")
    Reservation newVisit(@PathVariable int centerId, @PathVariable String userId, @RequestBody Visit visit) {

        return reservationService.addVisit(centerId, userId, visit);

    }

    @GetMapping(path="/reservations/center/{id}")
    public @ResponseBody Iterable<Reservation> findReservationsForCenter(@PathVariable Integer id){
        return reservationService.findReservationsForCenter(id);
    }

    @GetMapping(path="/reservations/service/{id}")
    public @ResponseBody Iterable<Reservation> findReservationsForService(@PathVariable Integer id){
        return reservationService.findReservationsForService(id);
    }

    @GetMapping(path="/reservations/user/{id}")
    public @ResponseBody Iterable<Reservation> findReservationsForUser(@PathVariable Integer id){
        return reservationService.findReservationsForUser(id);
    }


}
