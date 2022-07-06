package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.DesiredPet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface DesiredPetRepository extends JpaRepository<DesiredPet, Integer> {

    @Query(value = "SELECT * FROM desired_pet d WHERE  d.species = 'DOG' ", nativeQuery = true)
    @Modifying
    @Transactional
    Iterable<DesiredPet> findAllDogs();

    @Query(value = "SELECT * FROM desired_pet d WHERE  d.species = 'CAT' ", nativeQuery = true)
    @Modifying
    @Transactional
    Iterable<DesiredPet> findAllCats();

    @Query(value = "SELECT * FROM desired_pet d WHERE  d.age = 'Junior' ", nativeQuery = true)
    @Modifying
    @Transactional
    Iterable<DesiredPet> findAllJuniors();
}
