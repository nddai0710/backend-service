package vn.daijava.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserPasswordRequest implements Serializable {
    private Long id;
    private String password;
    private String confirmPassword;
}
