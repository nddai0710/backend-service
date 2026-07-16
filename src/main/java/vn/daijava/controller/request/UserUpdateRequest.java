package vn.daijava.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.daijava.common.Gender;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class UserUpdateRequest implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
    private List<AddressRequest> address;
}
