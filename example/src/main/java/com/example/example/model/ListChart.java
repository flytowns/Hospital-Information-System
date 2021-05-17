package com.example.example.model;

import lombok.Data;

import java.util.List;

@Data
public class ListChart {
    private List<String> names;
    private List<Long> values;
    private List<Chart> list;
}
