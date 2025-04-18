package com.example.controller;

import com.example.common.Result;
import com.example.entiity.Params;
import com.example.entiity.Vaccine;
import com.example.exception.CustomException;
import com.example.service.VaccineService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@CrossOrigin
@RequestMapping("/vaccine")
@RestController
public class VaccineController {

    @Resource
    VaccineService vaccineService;

    @GetMapping("/searchvaccine")
    public Result findvaccine(Vaccine vaccine){
        List<Vaccine> list = vaccineService.findvaccine(vaccine);
        return Result.success(list);
    }

    @GetMapping("/pagesearchvaccine")
    public Result searchvaccine(Params params){
        PageInfo<Vaccine> infovaccine = vaccineService.searchvaccine(params);
        return Result.success(infovaccine);
    }

    @PostMapping("/addvaccine")
    public Result addvaccine (@RequestBody Vaccine vaccine){
        if(vaccine.getVaccine_id() == null){
            vaccineService.add(vaccine);
        }else{
            vaccineService.update(vaccine);
        }
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        vaccineService.deletevaccine(id);
        return Result.success();
    }

    @GetMapping("/searchbyid/{vaccineid}")
    public Result searchbyid(@PathVariable Integer vaccineid){
        Vaccine vaccine = vaccineService.searchbyid(vaccineid);
        return Result.success(vaccine);
    }

}
