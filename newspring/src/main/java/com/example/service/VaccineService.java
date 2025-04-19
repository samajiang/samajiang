package com.example.service;

import com.example.entiity.Admin;
import com.example.entiity.Params;
import com.example.entiity.Vaccine;
import com.example.mapper.VaccineMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VaccineService {
    @Resource
    VaccineMapper vaccineMapper;
    public List<Vaccine> findvaccine(Vaccine vaccine) {
        List<Vaccine> list = vaccineMapper.findvaccine(vaccine);
        return list;
    }

    public void add(Vaccine vaccine){
        vaccineMapper.insertSelective(vaccine);
    }

    public void deletevaccine(Integer id) {
        vaccineMapper.deleteByPrimaryKey(id);
    }

    public void update(Vaccine vaccine) {
        vaccineMapper.updateByPrimaryKeySelective(vaccine);
    }

    public PageInfo<Vaccine> searchvaccine(Params params) {
        //        开启分页
        PageHelper.startPage(params.getPNum(), params.getPSize());
//接下来查询会自动按照当前开启的分页设置查询
        List<Vaccine> list = vaccineMapper.searchvac(params);
        return PageInfo.of(list);
    }

    public Vaccine searchbyid(Integer vaccineid) {
        Vaccine vaccine = vaccineMapper.searchbyid(vaccineid);
        return vaccine;
    }

    public void savevacccinepic(String vaccinename, String fileUrl) {
        Vaccine vaccine = vaccineMapper.searchbyname(vaccinename);
        vaccine.setVaccine_pic(fileUrl);
//        更新疫苗图片
        vaccineMapper.uploadpic(vaccinename,fileUrl);
        return;
    }

    public Vaccine findvaccinebyname(String vaccinename) {
        Vaccine vaccine = vaccineMapper.searchbyname(vaccinename);
        return vaccine;
    }

    public List<Vaccine> fetchbievaccine() {
        List<Vaccine> bievaccine = vaccineMapper.fetchbievaccine();
        return bievaccine;
    }

    public Vaccine searchbyname(String vaccinename) {
        Vaccine va = vaccineMapper.searchbyname(vaccinename);
        return va;
    }
}
