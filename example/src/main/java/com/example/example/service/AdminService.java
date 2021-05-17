package com.example.example.service;
import com.example.example.model.Admin;

public interface AdminService {
    Admin findAdminByUserName(String user_name);
}
