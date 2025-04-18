package com.example.service;

import cn.hutool.core.lang.mutable.Mutable;
import com.example.entiity.Vaccine;
import com.example.mapper.SearchMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchService {

    @Resource
    SearchMapper searchMapper;

    public List<Vaccine> searchmohu(String findtext) {
        List<Vaccine> V = searchMapper.searchmohu(findtext);
        return V;
    }
}
