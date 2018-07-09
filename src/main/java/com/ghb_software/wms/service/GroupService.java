package com.ghb_software.wms.service;


import com.ghb_software.wms.model.Group;
import com.ghb_software.wms.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group getAdminGroup() {
        return groupRepository.findOneByName("group.admin");
    }
}
