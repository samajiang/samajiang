package com.example.service;

import com.example.entiity.GongGao;
import com.example.mapper.GonggaoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GonggaoService {

    @Resource
    GonggaoMapper gonggaoMapper;

    public List<GongGao> searchgongg() {
        List<GongGao> gg = gonggaoMapper.fetchgonggao();
        return gg;
    }

    public void updatagonggao(GongGao gongGao) {
        gonggaoMapper.updateByPrimaryKeySelective(gongGao);
    }

    public void addgonggao(GongGao gongGao) {
        gonggaoMapper.insertSelective(gongGao);
    }

    public void deletegonggaobyid(Integer gonggaoid) {
        gonggaoMapper.deleteByPrimaryKey(gonggaoid);
    }
}
