package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.Role;
import ba.unsa.etf.pnwt.petcenter.Model.User;
import ba.unsa.etf.pnwt.petcenter.Repository.RoleRepository;
import ba.unsa.etf.pnwt.petcenter.Repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    MockMvc mockMvc;

    private User u1, u2, u3;
    private Role r1, r2, r3;

   /* @BeforeAll
    void setup() {
        r1 = new Role("Administrator");
        r2 = new Role("Zaposlenik");
        r3 = new Role("Klijent");
        r1 = roleRepo.save(r1);
        r2 = roleRepo.save(r2);
        r3 = roleRepo.save(r3);
        u1 = new User("33lejla","sifra123","Lejla","Buturovic",r1,"lbuturovic1@etf.unsa.ba");
        u2 = new User("vildy","Laptop!","Vildana","Beglerovic",r2,"vbegelrovi2@etf.unsa.ba");
        u3 = new User("vedo","sifra12345","Vedad","Beglerovic",r2,"vbegelrovi1@etf.unsa.ba");
        u1 = userRepo.save(u1);
        u2 = userRepo.save(u2);
        u3 = userRepo.save(u3);
    }

    @Test
    void getAllUsersByRoleId() throws Exception {
        mockMvc.perform(get(String.format("/api/role/%d/users", r1.getRoleID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("Lejla"))
                .andExpect(jsonPath("$.[0].email").value("lbuturovic1@etf.unsa.ba"));
    }
    @Test
    void addUserSuccessfully() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "    \"username\":\"lejla33\",\n" +
                                "    \"password\":\"sifrasifrica\",\n" +
                                "    \"firstName\":\"Lejla\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"lejla@etf.unsa.ba\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("lejla33"));
    }

    @Test
    void addUserWithInvalidEmail() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "    \"username\":\"lejlaaa\",\n" +
                                "    \"password\":\"sifrasifrica\",\n" +
                                "    \"firstName\":\"Lejla\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"lbuturovic1\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Email should be valid; "));
    }

    @Test
    void addUserWithInvalidEmail2() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "    \"username\":\"lejla123123\",\n" +
                                "    \"password\":\"sifr\",\n" +
                                "    \"firstName\":\"Lejla\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"lbuturovic1@etf.unsa.ba\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password must have at least 5 characters; "));
    }

    @Test
    void addUserWithInvalidUsername() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "    \"username\":\"le\",\n" +
                                "    \"password\":\"sifrasifrica\",\n" +
                                "    \"firstName\":\"Lejla\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"buurovi1@etf.unsa.ba\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username must have at lest 3 characters; "));
    }

    @Test
    void addUserWithInvalidName() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "    \"username\":\"lelelee\",\n" +
                                "    \"password\":\"sifrasifrica\",\n" +
                                "    \"firstName\":\"L\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"buurvic1@etf.unsa.ba\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("First name must be between 2 and 30 characters long; "));
    }

    @Test
    void addUserWithoutUsername() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "    \"password\":\"sifrasifrica\",\n" +
                                "    \"firstName\":\"Lejla\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"lbvic1@etf.unsa.ba\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username cannot be null; "));
    }

    @Test
    void addUserWithoutPassword() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "        \"username\":\"353lejla\",\n" +
                                "            \"firstName\":\"Lejla\",\n" +
                                "            \"lastName\": \"Buturovic\",\n" +
                                "            \"email\": \"lbutu1@etf.unsa.ba\"\n" +
                                "    }")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Password cannot be null; "));
    }

    @Test
    void addUserWithInvalidRoleID() throws Exception {
        String id = "9999999";
        mockMvc.perform(post(String.format("/api/role/%s/users", id))
                        .content("{\n" +
                                "    \"username\":\"lejla33\",\n" +
                                "    \"password\":\"sifrasifrica\",\n" +
                                "    \"firstName\":\"Lejla\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"lbuturo1@etf.unsa.ba\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Role with id = " + id + " does not exist!"));
    }

    @Test
    void getAllUsersByRoleIdNotExistingInUsers() throws Exception {
        mockMvc.perform(get(String.format("/api/role/%d/users", r3.getRoleID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void getUser() throws Exception {
        u1 = new User("33lejla","sifra123","Lejla","Buturovic",r1,"lbuturovic1@etf.unsa.ba");
        userRepo.save(u1);
        mockMvc.perform(get(String.format("/api/users/%s", u1.getUserID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("33lejla"));
    }

    @Test
    void getUserInvalidArgument() throws Exception {
        mockMvc.perform(get(String.format("/api/users/kk"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Method Argument Type Mismatch Exception"));
    }

    @Test
    void getUserInvalidId() throws Exception {
        mockMvc.perform(get(String.format("/api/users/1758722e-0e14-4f9f-80bd-bf41a862597"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void deleteUserInvalidId() throws Exception {
        String id = "1758722e-0e14-4f9f-80bd-0bf41a862597";
        mockMvc.perform(delete(String.format("/api/users/%s", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(jsonPath("$.message").value("User with id = " + id + " does not exist!"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    void deleteUser() throws Exception {
        UUID id = u3.getUserID();
        mockMvc.perform(delete(String.format("/api/users/%s", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteUsersByRole() throws Exception {
        Integer id = r1.getRoleID();
        mockMvc.perform(delete(String.format("/api/role/%s/users", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void addUserWithExistingUsername() throws Exception {
        mockMvc.perform(post(String.format("/api/role/%d/users", r1.getRoleID()))
                        .content("{\n" +
                                "    \"username\":\"33lejla\",\n" +
                                "    \"password\":\"sifrasifrica\",\n" +
                                "    \"firstName\":\"Lejla\",\n" +
                                "    \"lastName\": \"Buturovic\",\n" +
                                "    \"email\": \"lbuturovic1@etf.unsa.ba\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username or email already exists!"));
    }*/












}
