package ba.unsa.etf.pnwt.petcenter.Service;
import ba.unsa.etf.pnwt.petcenter.Model.User;
import ba.unsa.etf.pnwt.petcenter.Model.UserResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

        public abstract User createUser(User user);
        public abstract List<UserResponse> getAllUsers();
        public abstract User addRoleToUser(String username, String name);
        public Mono<User> getByUsername(String username);
        public abstract User registerUser(User user);
        public abstract User updateUser(UUID id, User user);
}

