package com.ensaf.facturation.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * User class represents a user with credentials and other related information.
 * @author zhanafi
 */

//@AllArgsConstructor

//@Getter @Setter
//@RequiredArgsConstructor //<> @NoArgsConstructor
//@ToString
//@EqualsAndHashCode(of = "id")

@SuperBuilder
@RequiredArgsConstructor @AllArgsConstructor

@Data
@EqualsAndHashCode(callSuper = true, of = "id")
public class User extends Entity {

    /**
     * Username for the user.
     */
    private String username;

    /**
     * Password for the user account.
     */
    @ToString.Exclude
    private String password;
    
    /**
     * Flag indicating whether the user is enabled or disabled.
     */
    private boolean enabled;

    /**
     * The date and time when the user account was created.
     */
    private Date creationDate;
    
    /**
     * The date and time when the user's password will expire.
     */
    private Date passwordExpiryDate;
    
    @Singular
    private List<String> roles;
    
}
