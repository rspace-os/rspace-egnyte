package com.researchspace.egnyte.api.clients.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String access_token;
    private int expires_in;
    private String token_type;

}
