package ba.unsa.etf.pnwt.petcenter.Service;

import ba.unsa.etf.pnwt.petcenter.Exceptions.NotFoundException;
import ba.unsa.etf.pnwt.petcenter.Model.Role;
import ba.unsa.etf.pnwt.petcenter.Repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RoleServiceTest {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    MockMvc mockMvc;

    private  Role r1, r2, r3;

   /* @BeforeEach
    void setup() {
        r1 = new Role("Administrator");
        r2 = new Role("Zaposlenik");
        r3 = new Role("Klijent");
        r1 = roleRepo.save(r1);
        r2 = roleRepo.save(r2);
        r3 = roleRepo.save(r3);
    }

    @Test
    void getAllRolees() throws Exception {
        mockMvc.perform(get("/api/role")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("Administrator"))
                .andExpect(jsonPath("$.[1].name").value("Zaposlenik"))
                .andExpect(jsonPath("$.[2].name").value("Klijent"));
    }

    @Test
    void addRoleWithInvalidName() throws Exception {
        mockMvc.perform(post("/api/role")
                        .content("{\n" +
                                "    \"name\": \"\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Name of role is mandatory; "));
    }

    @Test
    void addRoleSuccessfully() throws Exception {
        mockMvc.perform(post("/api/role")
                        .content("{\n" +
                                "    \"name\": \"Obicni korisnik\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Obicni korisnik"));
    }

    @Test
    void getRole() throws Exception {
        mockMvc.perform(get(String.format("/api/role/%d", r1.getRoleID()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Administrator"));
    }

    @Test
    void getRoleInvalidArgument() throws Exception {
        mockMvc.perform(get(String.format("/api/role/kk"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Method Argument Type Mismatch Exception"));
    }

    @Test
    void getRoleInvalidId() throws Exception {
        mockMvc.perform(get(String.format("/api/role/%d", r3.getRoleID() + 110))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void updateRole() throws Exception {
        Integer id = r2.getRoleID();
        mockMvc.perform(put(String.format("/api/role/%d", id))
                        .content("{\n" +
                                "    \"name\": \"Admin\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Admin"));


    }

    @Test
    void updateRoleInvalidId() throws Exception {
        mockMvc.perform(put(String.format("/api/role/%d", r3.getRoleID()+100))
                        .content("{\n" +
                                "    \"name\": \"Admin\"\n" +
                                "}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));
    }

    @Test
    void deleteRole() throws Exception {
        Integer id = r3.getRoleID();
        mockMvc.perform(delete(String.format("/api/role/%d", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void deleteRoleInvalidId() throws Exception {
        Integer id = r3.getRoleID()+1034;
        mockMvc.perform(delete(String.format("/api/role/%d", id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(jsonPath("$.message").value("Role with id = " + id + " does not exist!"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException));

    }

    @Test
    void deleteAllRole() throws Exception {
        mockMvc.perform(delete(String.format("/api/role"))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }*/
}
