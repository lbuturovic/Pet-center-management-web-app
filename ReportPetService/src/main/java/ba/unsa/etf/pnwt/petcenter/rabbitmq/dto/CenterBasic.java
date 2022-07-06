package ba.unsa.etf.pnwt.petcenter.rabbitmq.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CenterBasic {

    private Integer id;
    private String name;
    private String message;
}

