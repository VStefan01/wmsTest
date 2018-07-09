package com.ghb_software.wms.view;


import com.ghb_software.wms.model.Group;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGroupVO {

    private Group group;

    private boolean added;
}
