package com.example.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.example.model.Patient;
import com.example.example.model.PatientDoctor;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientDoctorMapper extends BaseMapper<PatientDoctor> {

}
