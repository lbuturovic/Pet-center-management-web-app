package ba.unsa.etf.pnwt.petcenter.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private UUID uuid;

    public AuthResponse(String accessToken, String refreshToken, UUID uuid) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.uuid = uuid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

}