package com.example.mapper;

import com.example.entiity.GongGao;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GonggaoMapper extends Mapper<GongGao> {

    @Select("select * from gonggaoinfo")
    List<GongGao> fetchgonggao();

}
