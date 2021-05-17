package com.example.example.service;

import com.example.example.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> findPatientByCardId(String card_id);
    List<Patient> findPatientByName(String name);
    Patient findPatientById(Long id);

}
