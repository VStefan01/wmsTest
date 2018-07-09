package com.ghb_software.wms.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

/**
 * Enumerated {@link User} roles.
 */
@Getter
@Setter
@Entity
public class Role {
//    SUPER_ADMIN("role.superAdmin"),
//    ADMIN("role.admin"),
//    INVENTORY("role.inventory"),
//    BILLING("role.billing");


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String label;

    private String description;

    public String authority() {
        return "ROLE_" + this.name;
    }
}
