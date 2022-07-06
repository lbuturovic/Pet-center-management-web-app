package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query(value = "SELECT * FROM pet WHERE owner_id = :id", nativeQuery = true)
    public Iterable<Pet> findPetsByOwner(@Param("id") Integer id);

    @Query(value = "SELECT * FROM pet WHERE center_id = :id and status = 'ABANDONED'", nativeQuery = true)
    public Iterable<Pet> findPetsByCenter(@Param("id") Integer id);

    @Query(value = "SELECT * FROM pet p WHERE  p.status = 'ABANDONED' and p.species='DOG'", nativeQuery = true)
    public Iterable<Pet> findAbandonedDogs();

    @Query(value = "SELECT * FROM pet p WHERE  p.status = 'ABANDONED' and p.species='CAT'", nativeQuery = true)
    public Iterable<Pet> findAbandonedCats();

    @Query(value = "SELECT * FROM pet p WHERE  p.status = 'ABANDONED'", nativeQuery = true)
    public Iterable<Pet> findAbandonedPets();


}