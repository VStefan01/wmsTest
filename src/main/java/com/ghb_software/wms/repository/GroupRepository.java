package com.ghb_software.wms.repository;

import com.ghb_software.wms.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long>{
    Group findOneByName(String s);
}
