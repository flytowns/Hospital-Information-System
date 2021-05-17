package com.example.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.core.Result;
import com.example.example.mapper.PatientDoctorMapper;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.Doctor;
import com.example.example.model.Patient;
import com.example.example.model.PatientDoctor;
import com.example.example.service.impl.PatientDoctorServiceImpl;
import com.example.example.service.impl.PatientServiceImpl;
import com.example.example.utils.JsonXMLUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/pat")
public class PatientController {

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientServiceImpl patientService;

    @PostMapping("/check_patient_password")
    @ResponseBody
    public Result checkPatientPassword(@RequestBody Map<String, Object> PatientParm){
        String card_id = null;
        String user_password = null;
        try {
            card_id =(String) PatientParm.get("card_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user_password=(String) PatientParm.get("user_password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Patient> listpatient = patientService.findPatientByCardId(card_id);
        if(listpatient.size()==0){
            return Result.fail(-2,"该患者不存在或尚未注册");
        }
        if(listpatient.get(0).getUserPassword().equals(user_password)){
            return Result.success("登陆成功");
        }else{
            return Result.fail(-3,"密码错误");
        }
    }
    
    @PostMapping("/add_patient_info")
    @ResponseBody
    public Result addPatientInfo(@RequestBody Patient patient){

        //去重同样的身份证号

        //把想查的表构造出来
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id",patient.getCardId());

        Patient patient1 = patientMapper.selectOne(queryWrapper);
        if(patient1!=null){
            return Result.fail(-4,"患者身份证号已经存在");
        }
        patientMapper.insert(patient);
        return Result.success("插入成功");
    }

    @PostMapping("/update_patient_info")
    @ResponseBody
    public Result updatePatientInfo(@RequestBody Patient patient){

        //看身份证号是否存在
        //把想查的表构造出来
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id",patient.getCardId());

        Patient patient1 = patientMapper.selectOne(queryWrapper);
        if(patient1==null){//不存在 直接插入
            patientMapper.insert(patient);
            return Result.success("患者身份证号不存在，直接插入完成");
        }
        //存在 update
        patient.setId(patient1.getId());
        patientMapper.updateById(patient);
        return Result.success("更新成功");
    }
}
