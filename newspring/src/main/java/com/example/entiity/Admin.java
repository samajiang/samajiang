package com.example.entiity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.*;
@Table(name = "userinfo")
@Data
@ToString
@NoArgsConstructor
public class Admin {
    @Id//表示id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//表示自增
    private Integer user_id;

    private String user_name;


    private String password;


    private String sex;


    private Integer age;


    private String phone;

    private String root;

    @Transient
    private String token;
@Nullable
    private String avatar_url;


}
