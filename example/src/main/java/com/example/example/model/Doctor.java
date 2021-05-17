package com.example.example.model;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Doctor {
    @TableId(value="id",type= IdType.AUTO)
    private Long id;
    private String name;
    private Integer age;
    private String tele;
    private String gender; // 0:girl 1:boy
    private String cardId;

    private String userName;
    private String userPassword;
}
