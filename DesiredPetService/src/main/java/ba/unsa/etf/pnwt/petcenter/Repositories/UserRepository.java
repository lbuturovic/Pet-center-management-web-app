package ba.unsa.etf.pnwt.petcenter.Repositories;

import ba.unsa.etf.pnwt.petcenter.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUUID(String uuid);

    void deleteUserByUUID(String id);
}
