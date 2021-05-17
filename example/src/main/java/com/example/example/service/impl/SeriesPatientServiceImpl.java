package com.example.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.example.mapper.SeriesMapper;
import com.example.example.mapper.SeriesPatientMapper;
import com.example.example.model.Patient;
import com.example.example.model.PatientDoctor;
import com.example.example.model.Series;
import com.example.example.model.SeriesPatient;
import com.example.example.service.SeriesPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class SeriesPatientServiceImpl implements SeriesPatientService {
    @Autowired
    private SeriesPatientMapper seriesPatientMapper;
    @Autowired
    private SeriesMapper seriesMapper;
    @Override
    public List<Series> findSeriesByPatientId(Long id) {
        QueryWrapper<SeriesPatient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patient_id",id); //PatientId?
        List<SeriesPatient>  list = seriesPatientMapper.selectList(queryWrapper);
        List<Series> seriesList = new ArrayList<>();
        for(SeriesPatient seriesPatient: list){
            Long seriesId =  seriesPatient.getSeriesId();
            seriesList.add(seriesMapper.selectById(seriesId));
        }
        return seriesList;
    }
}
