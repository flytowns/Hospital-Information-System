package com.example.example.model;

import lombok.Data;

@Data
public class Chart {
    private Long value = 0L ;  //value
    private String name; //key

    public Chart(Long value, String name) {
        this.value = value;
        this.name = name;
    }
}
