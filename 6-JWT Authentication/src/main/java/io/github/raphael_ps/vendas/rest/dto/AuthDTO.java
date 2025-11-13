package io.github.raphael_ps.vendas.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthDTO {

    private String login;
    private String password;

}
