package com.example.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.example.model.Doctor;
import com.example.example.model.Patient;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorMapper extends BaseMapper<Doctor> {

}
