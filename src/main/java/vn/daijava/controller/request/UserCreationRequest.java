package vn.daijava.controller.request;

import lombok.Getter;
import lombok.Setter;
import vn.daijava.common.Gender;
import vn.daijava.common.UserType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UserCreationRequest implements Serializable {
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
    private UserType type;
    private List<AddressRequest> address;
}
