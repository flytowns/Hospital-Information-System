package com.example.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.Patient;
import com.example.example.model.PatientDoctor;
import com.example.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientMapper patientMapper;

    public Patient findPatientById(Long id){
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return patientMapper.selectOne(queryWrapper);
    }

    public List<Patient> findPatientByCardId(String card_id){
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id",card_id);
        return patientMapper.selectList(queryWrapper);
    }

    public List<Patient> findPatientByName(String name) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return patientMapper.selectList(queryWrapper);
    }
}
