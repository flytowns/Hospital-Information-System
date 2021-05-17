package com.example.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.core.Result;
import com.example.example.mapper.DoctorMapper;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.Doctor;
import com.example.example.model.Patient;
import com.example.example.service.PatientDoctorService;
import com.example.example.service.impl.PatientDoctorServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/patdoc")
public class PatientDoctorController {
    @Autowired
    private PatientDoctorServiceImpl patientDoctorService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PatientMapper patientMapper;

    //查
    @GetMapping("/finddoc/{id}")
    public Result findDoctorByPatientId(@PathVariable(name = "id") Long id){
        List<Doctor> list = patientDoctorService.findDoctorByPatientId(id);
        return Result.success(list);
    }

    @GetMapping("/findalldoc")
    public Result findAllDoctor(){
        List<Doctor> list =  doctorMapper.selectList(new QueryWrapper<>());
        return  Result.success(list);
    }

    @GetMapping("/findpat/{id}")
    public Result findPatientByDoctorId(@PathVariable(name = "id") Long id){
        List<Patient> list = patientDoctorService.findPatientByDoctorId(id);
        return Result.success(list);
    }

    @GetMapping("/findallpat")
    public Result findAllPatient(){
        List<Patient> list =  patientMapper.selectList(new QueryWrapper<>());
        return  Result.success(list);
    }

}
