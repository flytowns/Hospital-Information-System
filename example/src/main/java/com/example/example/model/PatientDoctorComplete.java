package com.example.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class PatientDoctorComplete {
    @TableId(value="id",type= IdType.AUTO)
    private Long id;
    private Long doctorId;
    private String doctorCardId;
    private String doctorName;
    private Long patientId;
    private String patientCardId;
    private String patientName;
}
