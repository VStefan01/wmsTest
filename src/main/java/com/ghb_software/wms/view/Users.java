package com.ghb_software.wms.view;


import com.ghb_software.wms.model.Group;
import com.ghb_software.wms.model.Role;
import com.ghb_software.wms.model.User;
import com.ghb_software.wms.service.GroupService;
import com.ghb_software.wms.service.UserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class Users {

    @Getter
    @Setter
    private User user;

    @Getter
    @Setter
    private List<UserGroupVO> userGroupVOList = Collections.emptyList();

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @PostConstruct
    public void init() {
        user = new User();
//        userGroupVOList = groupService.getAvailableGroups().stream().map(group -> {
//            UserGroupVO userGroupVO = new UserGroupVO();
//            userGroupVO.setGroup(group);
//            return userGroupVO;
//        }).collect(Collectors.toList());
    }

    public void registerUser() {
        List<Group> selectedGroups =userGroupVOList.stream().filter(UserGroupVO::isAdded).map(UserGroupVO::getGroup).collect(Collectors.toList());
        userService.registerUser(user, selectedGroups);
        user = new User();
    }
}
