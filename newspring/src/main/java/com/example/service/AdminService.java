package com.example.service;

//import com.example.Utils.JWTTokenUtils;
import com.example.common.Result;
import com.example.controller.AdminController;
import com.example.entiity.Admin;

import com.example.entiity.Params;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Resource
    AdminMapper adminMapper;


    public PageInfo<Admin> findbusearch(Params params) {
//        开启分页
        PageHelper.startPage(params.getPegeNum(), params.getPageSize());
//接下来查询会自动按照当前开启的分页设置查询
        List<Admin> list = adminMapper.findbysearch(params);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
//        用户名不能为空
        if (admin.getUser_name() == null || "".equals(admin.getUser_name())) {
            throw new CustomException("用户名不能为空");
        }
//        进行重复判断，不允许重复的管理员名字，根据用户名查询
        Admin username = adminMapper.findByUserId(admin.getUser_name());
        if (username != null) {
//            说明已经有此用户名，提示前台不允许添加
            throw new CustomException("该用户已存在");
        }
//        初始化一个密码
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            admin.setPassword("123456");
        }
//        mybatis自带的增加数据接口
        adminMapper.insertSelective(admin);
    }

    public void update(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }

    public void delete(int id) {
        adminMapper.deleteByPrimaryKey(id);
    }

    public Admin login(Admin admin) {
//        进行非空判断
        if (admin.getUser_name() == null || "".equals(admin.getUser_name())) {
            throw new CustomException("用户名不能为空");
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            throw new CustomException("密码不能为空");
        }
//        从数据库查用户名和密码
        Admin user = adminMapper.findbynameandpassword(admin.getUser_name(), admin.getPassword());
        if (user == null) {
//            没查出来用户
            throw new CustomException("用户名或密码输入错误");
        }
//        成功查到用户
//        查到用户后利用用户id和密码生成token
//        String token = JWTTokenUtils.getToken(user.getId().toString(), user.getPassword());
//        user.setToken(token);
        return user;
    }

//    通过id查询
    public Admin findbyid(Integer adminID){
        return adminMapper.selectByPrimaryKey(adminID);
    }

    /**
     * 更新用户头像
     * @param userId 用户ID
     * @param avatarUrl 头像URL
     */
    @Transactional
    public void updateUserAvatar(String userId, String avatarUrl) {
        Admin user = adminMapper.findByUserId(userId);
        user.setAvatar_url(avatarUrl);
        // 更新头像
        adminMapper.updateAvatarUrl(userId, avatarUrl);
    }

    /**
     * 获取用户头像URL
     * @param USERID 用户ID
     * @return 头像URL
     */
    public String getUserAvatar(String username) {
        log.info("用户名"+username);
        Admin user = adminMapper.findByUserId(username);
        log.info(user.getAvatar_url());
        return user.getAvatar_url();
    }

    public Admin loginpc(Admin admin) {
        //        进行非空判断
        if (admin.getUser_name() == null || "".equals(admin.getUser_name())) {
            throw new CustomException("用户名不能为空");
        }
        if (admin.getPassword() == null || "".equals(admin.getPassword())) {
            throw new CustomException("密码不能为空");
        }
        //        从数据库查用户名和密码
        Admin usera = adminMapper.findbynameandpassword(admin.getUser_name(), admin.getPassword());
        if (usera == null) {
//            没查出来用户
            throw new CustomException("用户名或密码输入错误");
        }else{
            if(usera.getRoot().equals("否")){
                throw new CustomException("不是管理员,请联系管理员申请权限");
            }else {
                return usera;
            }
        }

    }

    public Admin fetchuserbyname(String username) {
        Admin user = adminMapper.fetchuserbyname(username);
        return user;
    }
}
