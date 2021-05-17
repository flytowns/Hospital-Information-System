package com.example.example.service;

import com.example.example.model.Series;

import java.util.List;

public interface SeriesPatientService {
    List<Series> findSeriesByPatientId(Long id);
}
