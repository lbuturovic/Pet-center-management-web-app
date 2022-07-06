package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;


public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    @Query(value = "SELECT * FROM reservation  WHERE  center_id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    public Iterable<Reservation> findReservationsForCenter(@Param("id") Integer id);


    @Query(value = "SELECT * FROM reservation  WHERE  service_id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    public Iterable<Reservation> findReservationsForService(@Param("id") Integer id);

    @Query(value = "SELECT * FROM reservation  WHERE  user_id = :id", nativeQuery = true)
    @Modifying
    @Transactional
    Iterable<Reservation> findReservationsForUser(@Param("id") Integer id);


}
