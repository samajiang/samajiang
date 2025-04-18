package com.example.entiity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Time;

@Table(name = "orderinfo")
@Data
@ToString
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//表示自增
    private Integer order_id;
    private String order_name;
    private String order_time;
    private String order_phone;
    private String order_vaccine;
    private String order_addr;

    private String user_name;
}
