package com.example.example.controller;

import com.baomidou.mybatisplus.core.assist.ISqlRunner;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.core.Result;
import com.example.example.mapper.AdminMapper;
import com.example.example.mapper.DoctorMapper;
import com.example.example.mapper.PatientDoctorMapper;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.*;
import com.example.example.service.DoctorService;
import com.example.example.service.PatientDoctorService;
import com.example.example.service.PatientService;
import com.example.example.service.impl.AdminServiceImpl;
import com.example.example.service.impl.DoctorServiceImpl;
import com.example.example.service.impl.PatientDoctorServiceImpl;
import com.example.example.service.impl.PatientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.print.Doc;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.Serializable;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PatientServiceImpl patientService;

    @Autowired
    private DoctorServiceImpl doctorService;

    @Autowired
    private PatientDoctorServiceImpl patientDoctorService;

    @Autowired
    private DoctorMapper doctorMapper;

    @Autowired
    private PatientMapper patientMapper;

    @Autowired
    private PatientDoctorMapper patientDoctorMapper;

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private AdminMapper adminMapper;

    @PostMapping("/check_admin_password")
    @ResponseBody
    public Result checkAdminPassword(@RequestBody Map<String, Object> AdminParm) {
        String user_name = null;
        String user_password = null;
        try {
            user_name = (String) AdminParm.get("user_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            user_password = (String) AdminParm.get("user_password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        Admin admin = adminService.findAdminByUserName(user_name);
        if (admin == null) {
            return Result.fail(-2, "该管理员不存在");
        }
        if (admin.getUserPassword().equals(user_password)) {
            return Result.success("登陆成功");
        } else {
            return Result.fail(-3, "密码错误");
        }
    }

    @GetMapping("/read_patient_info_by_card_id/{card_id}")
    @ResponseBody
    public Result readPatientInfoByCardId(@PathVariable(name = "card_id") String card_id) {
        List<Patient> list = patientService.findPatientByCardId(card_id);
        if (list.size() == 0) {
            return Result.fail("该身份证号不存在");
        }
        return Result.success(list);
    }

    @GetMapping("/read_patient_info_by_name/{name}")
    @ResponseBody
    public Result readPatientInfoByName(@PathVariable(name = "name") String name) {
        List<Patient> list = patientService.findPatientByName(name);
        if (list.size() == 0) {
            return Result.fail("该名字不存在");
        }
        return Result.success(list);
    }

    @GetMapping("/read_doctor_info_by_card_id/{card_id}")
    @ResponseBody
    public Result readDoctorInfoByCardId(@PathVariable(name = "card_id") String card_id) {
        List<Doctor> list = doctorService.findDoctorByCardId(card_id);
        if (list.size() == 0) {
            return Result.fail("该身份证号不存在");
        }
        return Result.success(list);
    }

    @GetMapping("/read_doctor_info_by_name/{name}")
    @ResponseBody
    public Result readDoctorInfoByName(@PathVariable(name = "name") String name) {
        List<Doctor> list = doctorService.findDoctorByName(name);
        if (list.size() == 0) {
            return Result.fail("该名字不存在");
        }
        return Result.success(list);
    }

    @GetMapping("/read_patient_doctor_info_by_patient_card_id/{card_id}")
    @ResponseBody
    public Result readPatientDoctorInfoByPatientCardId(@PathVariable(name = "card_id") String card_id) {
        List<Patient> listp = patientService.findPatientByCardId(card_id);
        if (listp.size() == 0) {
            return Result.fail(-2, "该身份证号不存在");
        }
        List<Doctor> list = patientDoctorService.findDoctorByPatientId(listp.get(0).getId());
        if (list.size() == 0) {
            return Result.fail(-5, "该患者未选择医生");
        }

        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id", listp.get(0).getId());

        PatientDoctor patientDoctor = patientDoctorMapper.selectOne(queryWrapper);

        //todo:直接返回 patientdoctorcomplete的list 下面同理
        List<PatientDoctorComplete> list1 = new ArrayList<PatientDoctorComplete>();

        //只有一个 所以不用循环
        PatientDoctorComplete patientDoctorComplete = new PatientDoctorComplete();
        patientDoctorComplete.setId(patientDoctor.getId());
        patientDoctorComplete.setDoctorId(patientDoctor.getDoctorId());
        patientDoctorComplete.setPatientId(patientDoctor.getPatientId());

        Patient patient = patientService.findPatientById(patientDoctor.getPatientId());
        Doctor doctor = doctorService.findDoctorById(patientDoctor.getDoctorId());

        patientDoctorComplete.setDoctorCardId(doctor.getCardId());
        patientDoctorComplete.setDoctorName(doctor.getName());

        patientDoctorComplete.setPatientCardId(patient.getCardId());
        patientDoctorComplete.setPatientName(patient.getName());

        list1.add(patientDoctorComplete);


        return Result.success(list1);
    }

    @GetMapping("/read_patient_info_by_doctor_card_id/{card_id}")
    @ResponseBody
    public Result readPatientInfoByDoctorCardId(@PathVariable(name = "card_id") String card_id) {
        List<Doctor> list0 = doctorService.findDoctorByCardId(card_id);
        if (list0.size() == 0) {
            return Result.fail(-2, "该身份证号不存在");
        }
        List<Patient> list = patientDoctorService.findPatientByDoctorId(list0.get(0).getId());
        if (list.size() == 0) {
            return Result.fail(-5, "该医生未选择患者");
        }

        return Result.success(list);
    }


    @GetMapping("/read_patient_doctor_info_by_doctor_card_id/{card_id}")
    @ResponseBody
    public Result readPatientDoctorInfoByDoctorCardId(@PathVariable(name = "card_id") String card_id) {
        List<Doctor> list0 = doctorService.findDoctorByCardId(card_id);
        if (list0.size() == 0) {
            return Result.fail(-2, "该身份证号不存在");
        }
        List<Patient> list = patientDoctorService.findPatientByDoctorId(list0.get(0).getId());
        if (list.size() == 0) {
            return Result.fail(-5, "该医生未选择患者");
        }

        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doctor_id", list0.get(0).getId());

        List<PatientDoctor> patientDoctorList = patientDoctorMapper.selectList(queryWrapper);

        //todo:直接返回 patientdoctorcomplete的list 下面同理
        List<PatientDoctorComplete> list1 = new ArrayList<PatientDoctorComplete>();

        //有多个 循环
        for (PatientDoctor patientDoctor : patientDoctorList) {
            PatientDoctorComplete patientDoctorComplete = new PatientDoctorComplete();
            patientDoctorComplete.setId(patientDoctor.getId());
            patientDoctorComplete.setDoctorId(patientDoctor.getDoctorId());
            patientDoctorComplete.setPatientId(patientDoctor.getPatientId());

            Patient patient = patientService.findPatientById(patientDoctor.getPatientId());
            Doctor doctor = doctorService.findDoctorById(patientDoctor.getDoctorId());

            patientDoctorComplete.setDoctorCardId(doctor.getCardId());
            patientDoctorComplete.setDoctorName(doctor.getName());

            patientDoctorComplete.setPatientCardId(patient.getCardId());
            patientDoctorComplete.setPatientName(patient.getName());

            list1.add(patientDoctorComplete);
        }
        return Result.success(list1);
    }

    @PostMapping("/update_patient_info")
    @ResponseBody
    public Result updatePatientInfo(@RequestBody Patient patient){

        //看身份证号是否存在
        //把想查的表构造出来
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",patient.getId());

        Patient patient1 = patientMapper.selectOne(queryWrapper);
        if(patient1==null){//不存在 修改失败
            //patientMapper.insert(patient);
            return Result.fail("患者身份证号不存在，修改失败");
        }

        QueryWrapper<Patient> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("card_id", patient.getCardId());
        Patient patient2 = patientMapper.selectOne(queryWrapper1);
        if(patient2 != null && patient2.getId()!=patient.getId()){ //新的身份证号已被注册，修改失败
            return Result.fail("修改失败");
        }

        //存在 update
        patient.setId(patient1.getId());
        //patient.setUserPassword(patient1.getUserPassword());
        patientMapper.updateById(patient);
        return Result.success("更新成功");
    }

    //根据参数doctor的card_id进行更新 不许更新id
    @PostMapping("/update_doctor_info")
    @ResponseBody
    public Result updateDoctorInfo(@RequestBody Doctor doctor) {

        //看身份证号是否存在
        //把想查的表构造出来
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", doctor.getId());

        Doctor doctor1 = doctorMapper.selectOne(queryWrapper);
        if (doctor1 == null) {//不存在 修改失败
            // doctorMapper.insert(doctor);
            return Result.fail("修改失败");
        }

        QueryWrapper<Doctor> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("card_id", doctor.getCardId());
        Doctor doctor2 = doctorMapper.selectOne(queryWrapper1);
        if (doctor2 != null && doctor2.getId()!=doctor.getId()) { //新的身份证号已被注册，修改失败
            return Result.fail("修改失败");
        }

        //存在 update
        doctor.setId(doctor1.getId());
        //doctor.setUserPassword(doctor1.getUserPassword());
        doctorMapper.updateById(doctor);
        return Result.success("更新成功");
    }

    @PostMapping("/update_patient_doctor_info")
    @ResponseBody
    public Result updatePatientDoctorInfo(@RequestBody PatientDoctorComplete patientDoctorComplete){
        //通过patient_card_id和doctor_card_id找到新的patient_id (patient) 和doctor_id (doctor)
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", patientDoctorComplete.getPatientCardId());
        Patient patient = patientMapper.selectOne(queryWrapper);
        if(patient == null){
            return Result.fail("更新失败");
        }

        QueryWrapper<Doctor> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("card_id", patientDoctorComplete.getDoctorCardId());
        Doctor doctor = doctorMapper.selectOne(queryWrapper1);
        if(doctor == null){
            return Result.fail("更新失败");
        }

        //查看该patient_id是否在表中出现过（原id那层除外） （一个患者只能有一个医生的设定）
        QueryWrapper<PatientDoctor> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("patient_id",patient.getId());
        PatientDoctor patientDoctor = patientDoctorMapper.selectOne(queryWrapper2);
        if(patientDoctor != null && !patientDoctor.getId().equals(patientDoctorComplete.getId())){
            return Result.fail("更新失败");
        }

        //查看是否存在patient_id和doctor_id没变的情况（造成重复）
        if(patient.getId() == patientDoctor.getPatientId() && doctor.getId() == patientDoctor.getDoctorId()){
            return Result.fail("更新失败");
        }

        //将patient_id和doctor_id替换数据库的patientDoctor表
        QueryWrapper<PatientDoctor> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("id",patientDoctorComplete.getId());
        PatientDoctor patientDoctor1 = patientDoctorMapper.selectOne(queryWrapper3);
        patientDoctor1.setDoctorId(doctor.getId());
        patientDoctor1.setPatientId(patient.getId());
        patientDoctorMapper.updateById(patientDoctor1);
        return Result.success("更新成功");
    }

    @PostMapping("/add_patient_doctor_info")
    @ResponseBody
    public Result addPatientDoctorInfo(@RequestBody PatientDoctorComplete patientDoctorComplete ){

        //去重同样的身份证号

//        //把想查的表构造出来
//        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("card_id",doctor.getCardId());
//
//        Doctor doctor1 = doctorMapper.selectOne(queryWrapper);
//        if(doctor1!=null){
//            return Result.fail(-4,"医生身份证号已经存在");
//        }
//        doctorMapper.insert(doctor);
//        return Result.success("插入成功");

        //通过patient_card_id和doctor_card_id找到新的patient_id (patient) 和doctor_id (doctor)
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", patientDoctorComplete.getPatientCardId());
        Patient patient = patientMapper.selectOne(queryWrapper);
        if(patient == null){
            return Result.fail(-2,"添加失败");
        }

        QueryWrapper<Doctor> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("card_id", patientDoctorComplete.getDoctorCardId());
        Doctor doctor = doctorMapper.selectOne(queryWrapper1);
        if(doctor == null){
            return Result.fail(-6,"添加失败");
        }

        //查看该patient_id是否在表中出现过 （一个患者只能有一个医生的设定）
        QueryWrapper<PatientDoctor> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("patient_id",patient.getId());
        PatientDoctor patientDoctor = patientDoctorMapper.selectOne(queryWrapper2);
        if(patientDoctor != null){
            return Result.fail(-4,"添加失败");
        }


        //将patient_id和doctor_id替换数据库的patientDoctor表
        PatientDoctor patientDoctor1 = new PatientDoctor();
        patientDoctor1.setDoctorId(doctor.getId());
        patientDoctor1.setPatientId(patient.getId());
        patientDoctorMapper.insert(patientDoctor1);
        return Result.success("插入成功");



    }



    //通过patient的id去修改这个表 第一个参数为patient的id 第二个参数为想改为的doctor的id
    //因为一个patient只能对应于一个doctor 而一个doctor能对应于多个patient
    @PostMapping("/update_patient_doctor_info_by_patient_card_id/{patient_card_id}/{doctor_card_id}")
    @ResponseBody
    public Result updatePatientDoctorInfoByPatientCardId(@PathVariable(name = "patient_card_id") String patient_card_id, @PathVariable(name = "doctor_card_id") String doctor_card_id) {
        List<Patient> listpatient1 = patientService.findPatientByCardId(patient_card_id);
        if (listpatient1.size() == 0) {
            return Result.fail("该患者身份证号不存在");
        }
        List<Doctor> list1 = doctorService.findDoctorByCardId(doctor_card_id);
        if (list1.size() == 0) {
            return Result.fail("该医生身份证号不存在");
        }

        QueryWrapper<PatientDoctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id", listpatient1.get(0).getId());
        PatientDoctor patientDoctor1 = patientDoctorMapper.selectOne(queryWrapper);
        if (patientDoctor1 == null) {
            patientDoctor1 = new PatientDoctor();
            patientDoctor1.setPatientId(listpatient1.get(0).getId());
            patientDoctor1.setDoctorId(list1.get(0).getId());
            patientDoctorMapper.insert(patientDoctor1);
            return Result.success("该患者之前未选择医生，已添加成功");
        }
        patientDoctor1.setDoctorId(list1.get(0).getId());
        patientDoctorMapper.updateById(patientDoctor1);
        return Result.success("更新成功");
    }



    @DeleteMapping("/delete_doctor_info_list")
    @ResponseBody
    public Result deleteDoctorInfoList(@RequestBody List<Doctor> list) {
        for (Doctor doctor : list) {
            doctorMapper.deleteById(doctor.getId());
            if (patientDoctorService.isDoctorExistInPatientDoctor(doctor.getId())) {
                patientDoctorService.deleteInfoByDoctorId(doctor.getId());
            }
        }
        return Result.success("删除成功");
    }

    @DeleteMapping("/delete_patient_info_list")
    @ResponseBody
    public Result deletePatientInfoList(@RequestBody List<Patient> list) {
        for (Patient patient : list) {
            patientMapper.deleteById(patient.getId());
            if (patientDoctorService.isPatientExistInPatientDoctor(patient.getId())) {
                patientDoctorService.deleteInfoByPatientId(patient.getId());
            }
        }
        return Result.success("删除成功");
    }

    @DeleteMapping("/delete_patient_doctor_info_list")
    @ResponseBody
    public Result deletePatientDoctorInfoList(@RequestBody List<PatientDoctor> list) {
        for (PatientDoctor patientDoctor : list) {
            patientDoctorMapper.deleteById(patientDoctor.getId());
        }
        return Result.success("删除成功");
    }


    @GetMapping("/get_doctor_age_data")
    @ResponseBody
    public Result getDoctorAgeData() {
        List<Doctor> list = doctorMapper.selectList(new QueryWrapper<Doctor>());
        /*
        0-20:0
        20-30:1
        30-40:2
        40-50:3
        50-60:4
        60-70:5
        70以上:6
         */
        List<Chart> listres = new ArrayList<>(8);
        listres.add(new Chart(0L,"under 20"));
        listres.add(new Chart(0L,"20-30"));
        listres.add(new Chart(0L,"30-40"));
        listres.add(new Chart(0L,"40-50"));
        listres.add(new Chart(0L,"50-60"));
        listres.add(new Chart(0L,"60-70"));
        listres.add(new Chart(0L,"above 70"));
        for(Doctor doctor : list){
            int age = doctor.getAge();
            if(age<20) {
                listres.get(0).setValue(listres.get(0).getValue() + 1);
            }else if(age>=20 && age<30){
                listres.get(1).setValue(listres.get(1).getValue() + 1);
            }else if(age>=30 && age<40){
                listres.get(2).setValue(listres.get(2).getValue() + 1);
            }else if(age>=40 && age<50){
                listres.get(3).setValue(listres.get(3).getValue() + 1);
            }else if(age>=50 && age<60){
                listres.get(4).setValue(listres.get(4).getValue() + 1);
            }else if(age>=60 && age<70){
                listres.get(5).setValue(listres.get(5).getValue() + 1);
            }else if(age>=70){
                listres.get(6).setValue(listres.get(6).getValue() + 1);
            }
        }
        Iterator<Chart> iterator = listres.iterator();
        while(iterator.hasNext()){
            Chart chart = iterator.next();
            if(chart.getValue()==0L){
                iterator.remove();
            }
        }
        return Result.success(listres);
    }

    @GetMapping("/get_patient_age_data")
    @ResponseBody
    public Result getPatientAgeData() {
        List<Patient> list = patientMapper.selectList(new QueryWrapper<Patient>());
        /*

        10-20:0
        20-30:1
        30-40:2
        40-50:3
        50-60:4
        60-70:5
        70-80:6
        80-90:7
        90-100:8
        100以上:9
        0-10：10

         */
        List<Chart> listres = new ArrayList<>(11);
        listres.add(new Chart(0L,"10-20"));
        listres.add(new Chart(0L,"20-30"));
        listres.add(new Chart(0L,"30-40"));
        listres.add(new Chart(0L,"40-50"));
        listres.add(new Chart(0L,"50-60"));
        listres.add(new Chart(0L,"60-70"));
        listres.add(new Chart(0L,"70-80"));
        listres.add(new Chart(0L,"80-90"));
        listres.add(new Chart(0L,"90-100"));
        listres.add(new Chart(0L,"above 100"));
        listres.add(new Chart(0L,"0-10"));
        for(Patient patient : list){
            int age = patient.getAge();
            if(age>=10 && age<20) {
                listres.get(0).setValue(listres.get(0).getValue() + 1);
            }else if(age>=20 && age<30){
                listres.get(1).setValue(listres.get(1).getValue() + 1);
            }else if(age>=30 && age<40){
                listres.get(2).setValue(listres.get(2).getValue() + 1);
            }else if(age>=40 && age<50){
                listres.get(3).setValue(listres.get(3).getValue() + 1);
            }else if(age>=50 && age<60){
                listres.get(4).setValue(listres.get(4).getValue() + 1);
            }else if(age>=60 && age<70){
                listres.get(5).setValue(listres.get(5).getValue() + 1);
            }else if(age>=70 && age<80){
                listres.get(6).setValue(listres.get(6).getValue() + 1);
            }else if(age>=80 && age<90){
                listres.get(7).setValue(listres.get(7).getValue() + 1);
            }else if(age>=90 && age<100){
                listres.get(8).setValue(listres.get(8).getValue() + 1);
            }else if(age>=100){
                listres.get(9).setValue(listres.get(9).getValue() + 1);
            }else if(age>=0 && age<10){
                listres.get(10).setValue(listres.get(10).getValue() + 1);
            }
        }
        Iterator<Chart> iterator = listres.iterator();
        while(iterator.hasNext()){
            Chart chart = iterator.next();
            if(chart.getValue()==0L){
                iterator.remove();
            }
        }
        return Result.success(listres);
    }

    @GetMapping("/get_patient_diagnose_data")
    @ResponseBody
    public Result getPatientDiagnoseData() {
        List<Patient> list = patientMapper.selectList(new QueryWrapper<Patient>());
        Map<String,Long> map = new HashMap<>();
        for(Patient patient : list) {
            String diagnose = patient.getDiagnose();
            if (!map.containsKey(diagnose)) {
                map.put(diagnose, 1L);
            } else {
                map.put(diagnose, map.get(diagnose) + 1);
            }
        }
        ListChart listChart = new ListChart();
        List<Chart> lists = new ArrayList<>();
        List<String> names = new ArrayList<>();
        List<Long> values = new ArrayList<>();
        for(Map.Entry<String,Long> entry : map.entrySet()){
            String name = entry.getKey();
            Long value = entry.getValue();
            lists.add(new Chart(value,name));
            names.add(name);
            values.add(value);
        }
        listChart.setList(lists);
        listChart.setNames(names);
        listChart.setValues(values);
        return Result.success(listChart);
    }

    @GetMapping("/get_all_doctor_info")
    @ResponseBody
    /*
        返回医生的全部信息，用一个list进行储存，用于前端页面中管理员查看医生表单。
     */
    public Result getAllDoctorInfo() {
        List<Doctor> list = doctorMapper.selectList(new QueryWrapper<Doctor>());
//        for (Doctor doctor : list) {
//            doctor.setUserPassword(""); //加密
//        }
        return Result.success(list);
    }


    @GetMapping("/get_all_patient_info")
    @ResponseBody
    /*
        返回患者的全部信息，用一个list进行储存，用于前端页面中管理员查看患者表单。
     */
    public Result getAllPatientInfo() {
        List<Patient> list = patientMapper.selectList(new QueryWrapper<Patient>());
//        for (Patient patient : list) {
//            patient.setUserPassword(""); //加密
//        }
        return Result.success(list);
    }


    @GetMapping("/get_all_patient_doctor_complete_info")
    @ResponseBody
    public Result getAllPatientDoctorInfo() {
        List<PatientDoctor> list = patientDoctorMapper.selectList(new QueryWrapper<PatientDoctor>());
        List<PatientDoctorComplete> list1 = new ArrayList<PatientDoctorComplete>();
        for (PatientDoctor patientDoctor : list) {
            PatientDoctorComplete patientDoctorComplete = new PatientDoctorComplete();
            patientDoctorComplete.setId(patientDoctor.getId());
            patientDoctorComplete.setDoctorId(patientDoctor.getDoctorId());
            patientDoctorComplete.setPatientId(patientDoctor.getDoctorId());

            Patient patient = patientService.findPatientById(patientDoctor.getPatientId());
            Doctor doctor = doctorService.findDoctorById(patientDoctor.getDoctorId());

            patientDoctorComplete.setDoctorCardId(doctor.getCardId());
            patientDoctorComplete.setDoctorName(doctor.getName());

            patientDoctorComplete.setPatientCardId(patient.getCardId());
            patientDoctorComplete.setPatientName(patient.getName());

            list1.add(patientDoctorComplete);

        }
        return Result.success(list1);
    }



    @DeleteMapping("/delete_patient_doctor_info_by_patient_card_id/{patient_card_id}")
    @ResponseBody
    public Result deletePatientDoctorInfoByPatientCardId(@PathVariable(name = "patient_card_id") String card_id) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", card_id);
        Patient patient = patientMapper.selectOne(queryWrapper);
        if (patient == null) {
            return Result.fail("该患者身份证号不存在");
        }
        //删除patientDoctor表信息
        if (patientDoctorService.isPatientExistInPatientDoctor(patient.getId())) {
            patientDoctorService.deleteInfoByPatientId(patient.getId());
        }
        return Result.success("删除成功");

    }


    @GetMapping("/download/{patient_card_id}")
    @ResponseBody
    public ResponseEntity<FileSystemResource> download(@PathVariable(name = "patient_card_id") String patient_card_id) {

        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id",patient_card_id);
        Patient patient = patientMapper.selectOne(queryWrapper);
        File file = new File(patient.getSeriesPath());


        String[] paths = patient.getSeriesPath().split("\\\\");
        String fileName = paths[paths.length - 1];

        if (file.exists()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", "attachment; filename=" + fileName);
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", new Date().toString());
            headers.add("ETag", String.valueOf(System.currentTimeMillis()));
            return ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
        }
        return null;
    }


    //根据参数doctor的card_id进行更新 不许更新id
    @PostMapping("/delete_series_by_patient_card_id/{patient_card_id}")
    @ResponseBody
    public Result deleteSeries(@PathVariable(name = "patient_card_id") String patient_card_id) {

        //看身份证号是否存在
        //把想查的表构造出来
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", patient_card_id);
        Patient patient = patientMapper.selectOne(queryWrapper);
        if(patient == null){
            return Result.fail(-2,"患者身份证号不存在");
        }
        if(patient.getSeriesPath().equals("")){
            return Result.fail(-7,"尚未上传影像");
        }

        //todo 删除路径下的文件
        File myFilePath = new File(patient.getSeriesPath());
        myFilePath.delete();

        patient.setSeriesPath("");
        patientMapper.updateById(patient);
        return Result.success("删除成功");
    }

    @GetMapping("/is_series_exist/{patient_card_id}")
    @ResponseBody
    public Result isSeriesExist(@PathVariable(name = "patient_card_id") String patient_card_id) {

        //看身份证号是否存在
        //把想查的表构造出来
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", patient_card_id);
        Patient patient = patientMapper.selectOne(queryWrapper);
        if(patient == null){
            return Result.fail(-2,"患者身份证号不存在");
        }
        if(patient.getSeriesPath().equals("")){
            return Result.fail(-7,"尚未上传影像");
        }
        return Result.success("影像已经存在");
    }










    //下方代码
    //
    //
    //
    //好像没用上
    @DeleteMapping("/delete_patient_info_by_card_id/{card_id}")
    @ResponseBody
    public Result deletePatientInfoByCardId(@PathVariable(name = "card_id") String card_id) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", card_id);
        Patient patient = patientMapper.selectOne(queryWrapper);
        if (patient == null) {
            return Result.fail("该患者身份证号不存在");
        }
        patientMapper.deleteById(patient.getId());
        //删除patientDoctor表信息
        if (patientDoctorService.isPatientExistInPatientDoctor(patient.getId())) {
            patientDoctorService.deleteInfoByPatientId(patient.getId());
        }
        return Result.success("删除成功");
    }

    @DeleteMapping("/delete_patient_doctor_info_by_doctor_card_id/{doctor_card_id}")
    @ResponseBody
    public Result deletePatientDoctorInfoByDoctorCardId(@PathVariable(name = "doctor_card_id") String card_id) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", card_id);
        Doctor doctor = doctorMapper.selectOne(queryWrapper);
        if (doctor == null) {
            return Result.success("该医生身份证号不存在");
        }
        //删除patientDoctor表信息
        if (patientDoctorService.isDoctorExistInPatientDoctor(doctor.getId())) {
            patientDoctorService.deleteInfoByDoctorId(doctor.getId());
        }
        return Result.success("删除成功");
    }

    @DeleteMapping("/delete_doctor_info_by_card_id/{card_id}")
    @ResponseBody
    public Result deleteDoctorInfoByCardId(@PathVariable(name = "card_id") String card_id) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", card_id);
        Doctor doctor = doctorMapper.selectOne(queryWrapper);
        if (doctor == null) {
            return Result.success("该医生身份证号不存在");
        }
        doctorMapper.deleteById(doctor.getId());
        //删除patientDoctor表信息

        if (patientDoctorService.isDoctorExistInPatientDoctor(doctor.getId())) {
            patientDoctorService.deleteInfoByDoctorId(doctor.getId());
        }
        return Result.success("删除成功");
    }
}