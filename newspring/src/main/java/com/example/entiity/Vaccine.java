package com.example.entiity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "vaccineinfo")
@Data
@ToString
@NoArgsConstructor
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer vaccine_id;

    private String bianhao;

    private String vaccine_name;

    private String vaccine_datil;

    private String vaccine_pic;

    private Integer vaccine_count;
}
