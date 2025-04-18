package com.example.mapper;


import com.example.entiity.Order;
import com.example.entiity.Params;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderMapper extends Mapper<Order> {

@Select("select * from orderinfo where user_name = #{username}")
List<Order> fetchorderinfobyusername(String username);

    List<Order> fetchorderlist(@Param("params") Params params);

}
