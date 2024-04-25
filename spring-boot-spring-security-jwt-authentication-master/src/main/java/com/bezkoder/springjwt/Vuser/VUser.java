package com.bezkoder.springjwt.Vuser;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VUser {

    private String username;
    private String email;
    private String password;
    private String status;
}
