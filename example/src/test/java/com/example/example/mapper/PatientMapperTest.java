package com.example.example.mapper;

import com.example.example.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest

class PatientMapperTest {
    @Autowired
    private PatientMapper patientMapper;

    @Test
    public void x(){
        Patient patient = patientMapper.selectById(1);
        assertNotNull(patient);
        log.info(patient.getId()+":"+patient.getAge()+":"+patient.getName()+":"+patient.getGender());
    }
}