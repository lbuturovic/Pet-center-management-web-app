package ba.unsa.etf.pnwt.petcenter.Services;

import ba.unsa.etf.pnwt.petcenter.Models.Reservation;
import ba.unsa.etf.pnwt.petcenter.Models.Visit;
import ba.unsa.etf.pnwt.petcenter.Request.AddReservationRequest;

import java.time.LocalDateTime;
import java.util.Date;


public  interface ReservationService {
    public abstract Iterable<Reservation> findReservationsForCenter(Integer id);

    public abstract Iterable<Reservation> findReservationsForService(Integer id);

    public abstract Iterable<Reservation> findReservationsForUser(Integer id);


    public abstract Reservation addReservation(Reservation reservation);
    public abstract Reservation addVisit(int centerId, String userId, Visit visit);
}
