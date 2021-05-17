package com.example.example.controller;

import java.io.File;
import java.io.IOException;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.example.core.Result;
import com.example.example.mapper.PatientMapper;
import com.example.example.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@CrossOrigin
public class UploadController {
    /**
     * 上传文件
     *
     * @param multipartFile
     * @return 文件存储路径
     */
    @Autowired
    private PatientMapper patientMapper;

    @PostMapping("/upload")
    @ResponseBody
    public Result upload(HttpServletRequest request, @RequestParam(value="patient_card_id")  String patient_card_id, @RequestParam("file") MultipartFile multipartFile) {
        //判断这个病人之前有无影像
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", patient_card_id);
        Patient patient = patientMapper.selectOne(queryWrapper);
        if(patient == null){
            return Result.fail(-2,"患者身份证号不存在");
        }
        //有的话 删除之前的文件
        if(!patient.getSeriesPath().equals("")){
            File myFilePath = new File(patient.getSeriesPath());
            myFilePath.delete();
        }
        //重写数据库
        String filePath = "D:\\IdeaProject\\example\\medicalImage\\"+ multipartFile.getOriginalFilename();

        patient.setSeriesPath(filePath);
        patientMapper.updateById(patient);

        // 文件存储位置，文件的目录要存在才行，可以先创建文件目录，然后进行存储
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 文件存储
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success("上传成功");
    }
}

