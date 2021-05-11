package com.pjpy.studentManager.mapper;

import com.pjpy.studentManager.domain.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {

    Admin findByAdmin(Admin admin);


    int editPswdByAdmin(Admin admin);
}
