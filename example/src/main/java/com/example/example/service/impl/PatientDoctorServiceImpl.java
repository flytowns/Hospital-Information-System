package com.example.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.mapper.DoctorMapper;
import com.example.example.mapper.PatientDoctorMapper;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.Doctor;
import com.example.example.model.Patient;
import com.example.example.model.PatientDoctor;
import com.example.example.service.PatientDoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PatientDoctorServiceImpl implements PatientDoctorService {
    @Autowired
    private   PatientDoctorMapper patientDoctorMapper;
    @Autowired
    private PatientMapper patientMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Override
    public List<Patient> findPatientByDoctorId(Long id) {
        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id",id);
        List<PatientDoctor>  list = patientDoctorMapper.selectList(queryWrapper);
        List<Patient> patientList = new ArrayList<>();
        for(PatientDoctor patientDoctor: list){
            Long patientId =  patientDoctor.getPatientId();
            patientList.add(patientMapper.selectById(patientId));
        }
        return patientList;
    }
    public List<Doctor> findDoctorByPatientId(Long id) {
        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id",id);
        List<PatientDoctor> list = patientDoctorMapper.selectList(queryWrapper);
        List<Doctor> doctorList = new ArrayList<>();
        for(PatientDoctor patientDoctor: list){
            Long doctorId = patientDoctor.getDoctorId();
            doctorList.add(doctorMapper.selectById(doctorId));
        }
        return doctorList;
    }
    public void deleteInfoByDoctorId(Long id){
        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id",id);
        patientDoctorMapper.delete(queryWrapper);
        // ?
    }
    public void deleteInfoByPatientId(Long id){
        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id",id);
        PatientDoctor patientDoctor = patientDoctorMapper.selectOne(queryWrapper);
        patientDoctorMapper.deleteById(patientDoctor.getId());
    }
    public boolean isPatientExistInPatientDoctor(Long id) {
        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id",id);
        PatientDoctor patientDoctor = patientDoctorMapper.selectOne(queryWrapper);
        if(patientDoctor==null){
            return false;
        }else{
            return true;
        }
    }
    public boolean isDoctorExistInPatientDoctor(Long id) {
        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id",id);
        List<PatientDoctor> list = patientDoctorMapper.selectList(queryWrapper);
        if(list.size()==0){
            return false;
        }else{
            return true;
        }
    }
}
