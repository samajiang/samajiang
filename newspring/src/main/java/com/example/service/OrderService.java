package com.example.service;

import com.example.entiity.Admin;
import com.example.entiity.Order;
import com.example.entiity.Params;
import com.example.exception.CustomException;
import com.example.mapper.OrderMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderService {

    @Resource
    OrderMapper orderMapper;

    public void ordering(Order order) {
        if(order.getOrder_name() == null || "".equals(order.getOrder_name())){
            throw new CustomException("预定人名字不能为空");
        }
        if(order.getOrder_time() == null || "".equals(order.getOrder_time())){
            throw new CustomException("预定时间不能为空");
        }
       orderMapper.insertSelective(order);
    }

    public List<Order> fetchorderinfobyusername(String username) {
        List<Order> order = orderMapper.fetchorderinfobyusername(username);
        return order;
    }

    public void deleteorderbyid(Integer orderid) {
        orderMapper.deleteByPrimaryKey(orderid);
    }

    public PageInfo<Order> fetchorderlist(Params params) {
        //        开启分页
        PageHelper.startPage(params.getPegeNum(), params.getPageSize());
//接下来查询会自动按照当前开启的分页设置查询
        List<Order> list = orderMapper.fetchorderlist(params);
        return PageInfo.of(list);
    }

    public void updatabyid(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }
}
