package ba.unsa.etf.pnwt.petcenter.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserBasic {

    private String id;
    private String email;
    private String message;
}
