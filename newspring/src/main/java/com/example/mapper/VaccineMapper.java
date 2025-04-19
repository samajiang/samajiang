package com.example.mapper;

import com.example.entiity.Params;
import com.example.entiity.Vaccine;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface VaccineMapper extends Mapper<Vaccine> {
    @Select("select * from vaccineinfo")
    List<Vaccine> findvaccine(Vaccine vaccine);

    @Select("select * from vaccineinfo")
    List<Vaccine> searchvac(@Param("params") Params params);

@Select("select * from vaccineinfo where vaccine_id = #{vaccineid}")
    Vaccine searchbyid(Integer vaccineid);

    @Update("UPDATE vaccineinfo SET vaccine_pic = #{fileUrl} WHERE vaccine_name = #{vaccinename}")
    void uploadpic(@Param("vaccinename") String vaccinename, @Param("fileUrl") String fileUrl);

@Select("select * from vaccineinfo where vaccine_name = #{vaccinename}")
    Vaccine searchbyname(@Param("vaccinename") String vaccinename);
@Select("select * from vaccineinfo")
    List<Vaccine> fetchbievaccine();
}
