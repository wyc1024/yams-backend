package me.chn.yams.module.system.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginParam {

    @Size(min = 4, max = 10, message = "{range}")
    @NotBlank(message = "{required}")
    private String username;

    @NotBlank(message = "{required}")
    private String password;

}
