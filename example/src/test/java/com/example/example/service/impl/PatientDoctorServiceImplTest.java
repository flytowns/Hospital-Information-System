package com.example.example.service.impl;

import com.example.example.model.Doctor;
import com.example.example.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class PatientDoctorServiceImplTest {
    @Autowired
    private PatientDoctorServiceImpl patientDoctorService;

    @Test
    public void x(){
      List<Doctor> list = patientDoctorService.findDoctorByPatientId(3L);
      assertEquals(1,list.size());
      assertEquals(2L,list.get(0).getId());
    }
    @Test
    public void y(){
        List<Patient> list = patientDoctorService.findPatientByDoctorId(1L);
        assertEquals(2,list.size());
    }
}