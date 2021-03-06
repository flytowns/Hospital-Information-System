package com.example.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.core.Result;
import com.example.example.mapper.DoctorMapper;
import com.example.example.mapper.PatientDoctorMapper;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.Admin;
import com.example.example.model.Doctor;
import com.example.example.model.Patient;
import com.example.example.model.PatientDoctor;
import com.example.example.service.PatientDoctorService;
import com.example.example.service.PatientService;
import com.example.example.service.impl.DoctorServiceImpl;
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
@RequestMapping("/doc")
public class DoctorController {
    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientDoctorMapper patientDoctorMapper;

    @Autowired
    private PatientDoctorServiceImpl patientDoctorService;

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private DoctorServiceImpl doctorService;

    @PostMapping("/check_doctor_password")
    @ResponseBody
    public Result checkDoctorPassword(@RequestBody Map<String, Object> DoctorParm){
        String card_id = null;
        String user_password = null;
        try {
            card_id =(String) DoctorParm.get("card_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user_password=(String) DoctorParm.get("user_password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Doctor> list = doctorService.findDoctorByCardId(card_id);
        if(list.size()==0){
            return Result.fail(-2,"?????????????????????????????????");
        }
        if(list.get(0).getUserPassword().equals(user_password)){
            return Result.success("????????????");
        }else{
            return Result.fail(-3,"????????????");
        }
    }

    @PostMapping("/add_diagnosis")
    @ResponseBody
    public Result addPatientDiagnosis(@RequestBody Map<String, Object> PatientParm ){

        String card_id = null;
        String diagnose = null;
        try {
            card_id =(String) PatientParm.get("card_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            diagnose=(String)PatientParm.get("diagnose");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Patient> listpatient = patientService.findPatientByCardId(card_id);
        if(listpatient.size()==0){
            return Result.fail("????????????????????????");
        }
        listpatient.get(0).setDiagnose(diagnose);
        patientMapper.updateById(listpatient.get(0));
        return Result.success("??????????????????");
    }

    @GetMapping("/read_patient_info/{card_id}")
    @ResponseBody
    public Result readPatientInfo(@PathVariable(name = "card_id") String card_id){
        List<Patient> listpatient = patientService.findPatientByCardId(card_id);
        if(listpatient.size()==0){
            return Result.fail("????????????????????????");
        }
        return Result.success(listpatient.get(0));
    }

    @GetMapping("/read_patient_doctor_info/{card_id}")
    @ResponseBody
    public Result readPatientDoctorInfo(@PathVariable(name = "card_id") String card_id){
        List<Patient> listpatient = patientService.findPatientByCardId(card_id);
        if(listpatient.size()==0){
            return Result.fail("????????????????????????");
        }
        List<Doctor> list = patientDoctorService.findDoctorByPatientId(listpatient.get(0).getId());
        if(list.size()==0){
            return Result.fail("????????????????????????");
        }
        return Result.success(list);
    }

    @PostMapping("/add_patient_doctor_info")
    @ResponseBody
    public Result addPatientDoctorInfo(@RequestBody Map<String, Object> PatientDoctorParm ) {
        String card_id = null;
        Long doctor_id = null;
        try {
            card_id =(String) PatientDoctorParm.get("card_id");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            doctor_id=Long.valueOf((Integer)PatientDoctorParm.get("doctor_id")) ;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //??????card_id??????patient?????????patient_id???doctor_id???????????????patient_doctor???
        List<Patient> listpatient = patientService.findPatientByCardId(card_id);
        if(listpatient.size()==0){
            return Result.fail("????????????????????????");
        }
        PatientDoctor patientDoctor=new PatientDoctor();
        patientDoctor.setPatientId(listpatient.get(0).getId());
        patientDoctor.setDoctorId(doctor_id);
        patientDoctorMapper.insert(patientDoctor);
        return Result.success("????????????");
    }

    @PostMapping("/add_doctor_info")
    @ResponseBody
    public Result addDoctorInfo(@RequestBody Doctor doctor ){

        //???????????????????????????

        //???????????????????????????
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id",doctor.getCardId());

        Doctor doctor1 = doctorMapper.selectOne(queryWrapper);
        if(doctor1!=null){
            return Result.fail(-4,"??????????????????????????????");
        }
        doctorMapper.insert(doctor);
        return Result.success("????????????");
    }

//    @PostMapping("/update_doctor_info")
//    @ResponseBody
//    public Result updateDoctorInfo(@RequestBody Doctor doctor){
//
//        //???????????????????????????
//        //???????????????????????????
//        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("card_id",doctor.getCardId());
//
//        Doctor doctor1 = doctorMapper.selectOne(queryWrapper);
//        if(doctor1==null){//????????? ????????????
//            doctorMapper.insert(doctor);
//            return Result.success("????????????????????????????????????????????????");
//        }
//        //?????? update
//        doctor.setId(doctor1.getId());
//        doctorMapper.updateById(doctor);
//        return Result.success("????????????");
//    }



}