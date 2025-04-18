package com.example.controller;


import com.example.common.Result;
import com.example.entiity.Order;
import com.example.entiity.Params;
import com.example.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RequestMapping("/order")
@RestController
public class OrderController {

    @Resource
    OrderService orderService;

//    预定功能
    @PostMapping("/ordering")
    public Result ordering(@RequestBody Order order){
        System.out.println(order);
        orderService.ordering(order);
        return Result.success("预定成功");
    }

//    根据用户名获取预定信息
    @GetMapping("/{username}")
    public Result fetchorderinfobyusername(@PathVariable String username){
        List<Order> order = orderService.fetchorderinfobyusername(username);
        return Result.success(order);
    }

//    根据预定信息id删除预定信息
    @DeleteMapping("/deleteorderbyid/{orderid}")
    public Result deleteorderbyid(@PathVariable Integer orderid){
        orderService.deleteorderbyid(orderid);
        return Result.success();
    }

//    后台分页查询预约信息
    @GetMapping("/fetchorderlist")
    public Result fetchorderlist(Params params){
        PageInfo<Order> orderlist = orderService.fetchorderlist(params);
        return Result.success(orderlist);
    }

//    后台根据id更新预定信息
    @PostMapping("/updatabyid")
    public Result updatabyid(@RequestBody Order order){

      orderService.updatabyid(order);

        return Result.success();
    }
}
