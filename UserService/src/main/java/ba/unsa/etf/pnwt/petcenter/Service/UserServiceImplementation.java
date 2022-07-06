package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.ApiError;
import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.Role;
import ba.unsa.etf.pnwt.petcenter.Model.User;
import ba.unsa.etf.pnwt.petcenter.Model.UserResponse;
import ba.unsa.etf.pnwt.petcenter.Repository.RoleRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.UserRepository;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.MessagingConfig;
import ba.unsa.etf.pnwt.petcenter.rabbitmq.dto.UserBasic;
import ba.unsa.etf.pnwt.petcenter.security.PBKDF2Encoder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
@Transactional
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PBKDF2Encoder passwordEncoder;
    @Autowired
    private RabbitTemplate template;

    @Override
    public User createUser(User user) {
        if (!userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isEmpty())
            throw new ApiError("Validation", "Username or email already exists!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role r = roleRepository.findByName("ROLE_EMPLOYEE");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        user.setRoles(roles);
        roleRepository.save(r);
        return userRepository.save(user);
    }

    public Mono<User> getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user==null){
            throw new RuntimeException("User not found!");
        }
        return Mono.justOrEmpty(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponse = new ArrayList<>();
        for (User u: users) {
            UserResponse ur = new UserResponse();
            ur.setUserID(u.getUserID());
            ur.setFirstName(u.getFirstName());
            ur.setLastName(u.getLastName());
            ur.setPassword(u.getPassword());
            ur.setUsername(u.getUsername());
            ur.setEmail(u.getEmail());
            ur.setRole(u.getRolesByName().get(0));
            userResponse.add(ur);
        }
        return userResponse;
    }

    @Override
    public User addRoleToUser(String username, String name) {
        Role role = roleRepository.findByName(name);
        if (role == null)
            throw new ApiError("Not found", "Role with name does not exist!");
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new ApiError("Not found", "User with username does not exist!");
        user.getRoles().add(role);
        roleRepository.save(role);
        return null;
    }

    @Override
    public User registerUser(User user) {
        if (!userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isEmpty())
            throw new ApiError("Validation", "Username or email already exists!");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role r = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(r);
        user.setRoles(roles);
        roleRepository.save(r);
        user = userRepository.save(user);
        try {
            UserBasic info = new UserBasic(user.getUserID().toString(), user.getEmail(), "add");
            template.convertAndSend(MessagingConfig.EXCHANGE_USER_RESERVATION, MessagingConfig.ROUTING_KEY_USER_RESERVATION, info);
            template.convertAndSend(MessagingConfig.EXCHANGE_USER_DESIRED, MessagingConfig.ROUTING_KEY_USER_DESIRED, info);
            System.out.println(info.getId());
        }
        catch (Exception e){
            System.out.println(e);
        }
        return user;
    }

    @Override
    public User updateUser(UUID id, User user) {
        User u = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id = " + id + " does not exist!"));
        if (!user.getEmail().equals(u.getEmail())) {
            if (!userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail()).isEmpty())
                throw new ApiError("Validation", "Username or email already exists!");
        }
        u.setUsername(user.getUsername());
        u.setPassword(passwordEncoder.encode(user.getPassword()));
        u.setFirstName(user.getFirstName());
        u.setLastName(user.getLastName());
        u.setEmail(user.getEmail());
        return userRepository.save(u);

    }
}