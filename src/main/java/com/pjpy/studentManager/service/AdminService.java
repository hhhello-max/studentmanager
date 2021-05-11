package com.pjpy.studentManager.service;

import com.pjpy.studentManager.domain.Admin;


public interface AdminService {

    Admin findByAdmin(Admin admin);


    int editPswdByAdmin(Admin admin);
}
