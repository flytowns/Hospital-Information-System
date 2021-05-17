package com.example.example.service;

import com.example.example.model.Doctor;
import com.example.example.model.Patient;

import java.util.List;

public interface PatientDoctorService {
    List<Patient> findPatientByDoctorId(Long id);
    List<Doctor> findDoctorByPatientId(Long id);
    void deleteInfoByDoctorId(Long id);
    void deleteInfoByPatientId(Long id);
    boolean isPatientExistInPatientDoctor(Long id);
    boolean isDoctorExistInPatientDoctor(Long id);
}
