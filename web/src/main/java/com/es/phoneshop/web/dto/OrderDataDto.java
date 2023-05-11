package com.es.phoneshop.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDataDto {

    @Pattern(regexp = "^[A-Z][-a-zA-Z]{1,30}$", message = "Wrong name format")
    private String firstName;
    @Pattern(regexp = "^[A-Z][-a-zA-Z]{1,30}$", message = "Wrong name format")
    private String lastName;
    @Size(min = 1, max = 100, message = "Should be between 1 and 200 symbols")
    private String address;
    @Pattern(regexp = "^\\+375 \\((17|29|33|44)\\) [0-9]{3}-[0-9]{2}-[0-9]{2}$",
            message = "Wrong phone format, should be +375 (17|29|33|44) XXX-XX-XX")
    private String phone;
    @Size(max = 300, message = "Max 300 symbols")
    private String additionalInfo;
}
