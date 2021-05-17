package com.example.example.service;

import com.example.example.model.Doctor;
import com.example.example.model.Patient;

import java.util.List;

public interface DoctorService {
    List<Doctor> findDoctorByCardId(String card_id);
    List<Doctor> findDoctorByName(String name);
    Doctor findDoctorById(Long id);
}
