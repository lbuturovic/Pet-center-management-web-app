package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUuid (UUID uuid);
    @Query(value = "DELETE FROM user  WHERE uuid =:id", nativeQuery = true)
    void deleteUserById(@Param("id") UUID id);


}
