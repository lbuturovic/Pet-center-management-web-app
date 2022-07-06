package ba.unsa.etf.pnwt.petcenter.Controller;

import ba.unsa.etf.pnwt.petcenter.Model.AuthRequest;
import ba.unsa.etf.pnwt.petcenter.Model.AuthResponse;
import ba.unsa.etf.pnwt.petcenter.Model.Message;
import ba.unsa.etf.pnwt.petcenter.Model.RefreshTokenRequest;
import ba.unsa.etf.pnwt.petcenter.Service.UserService;
import ba.unsa.etf.pnwt.petcenter.security.JWTUtil;
import ba.unsa.etf.pnwt.petcenter.security.PBKDF2Encoder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@RestController
public class AuthenticationREST {

    private JWTUtil jwtUtil;
    private PBKDF2Encoder passwordEncoder;
    private UserService userService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return userService.getByUsername(ar.getUsername())
                .filter(userDetails -> passwordEncoder.encode(ar.getPassword()).equals(userDetails.getPassword()))
                .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails), jwtUtil.generateRefreshToken(userDetails), userDetails.getUserID())))
                .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @PostMapping("/refresh-token")
    public Mono<ResponseEntity<AuthResponse>> refreshToken(@RequestBody RefreshTokenRequest rtr) {
        try {
            String username = jwtUtil.getUsernameFromToken(rtr.getRefreshToken());
            return userService.getByUsername(username)
                    .map(userDetails -> ResponseEntity.ok(new AuthResponse(jwtUtil.generateToken(userDetails), rtr.getRefreshToken(), userDetails.getUserID())))
                    .switchIfEmpty(Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
        } catch (Exception e) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }
    }

}