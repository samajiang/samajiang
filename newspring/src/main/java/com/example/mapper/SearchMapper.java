package com.example.mapper;

import cn.hutool.core.lang.mutable.Mutable;
import com.example.entiity.Vaccine;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface SearchMapper extends Mapper<Vaccine> {

    List<Vaccine> searchmohu(@Param("findtext") String findtext);
}
