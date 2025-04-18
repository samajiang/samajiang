package com.example.entiity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "gonggaoinfo")
@Data
@ToString
@NoArgsConstructor
public class GongGao {
    @Id//表示id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//表示自增
    private Integer gonggao_id;
    private String gonggao_name;
    private String gonggao_pic;

}
