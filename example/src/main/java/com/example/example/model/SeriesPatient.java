package com.example.example.model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SeriesPatient {
    @TableId(value="id",type= IdType.AUTO)
    private Long id;
    private Long seriesId;
    private Long patientId;
}
