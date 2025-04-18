package com.example.controller;

import cn.hutool.core.lang.mutable.Mutable;
import com.example.common.Result;
import com.example.entiity.Vaccine;
import com.example.service.SearchService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/search")
@CrossOrigin
@RestController
public class SearchController {

    @Resource
    SearchService searchService;

    @GetMapping("/searchvaccine/{findtext}")
    public Result searchvaccine(@PathVariable String findtext){
        List<Vaccine> V = searchService.searchmohu(findtext);
        return Result.success(V);
    }
}
