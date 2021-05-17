package com.example.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.mapper.DoctorMapper;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.Doctor;
import com.example.example.model.Patient;
import com.example.example.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    @Autowired
    private DoctorMapper doctorMapper;

    public Doctor findDoctorById(Long id){
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return doctorMapper.selectOne(queryWrapper);
    }

    public List<Doctor> findDoctorByCardId(String card_id){
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id",card_id);
        return doctorMapper.selectList(queryWrapper);
    }

    public List<Doctor> findDoctorByName(String name){
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",name);
        return doctorMapper.selectList(queryWrapper);
    }

}
