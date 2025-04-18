//package com.example.Utils;
//
//import cn.hutool.core.date.DateUnit;
//import cn.hutool.core.date.DateUtil;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.example.service.AdminService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.Date;
//
///**
// * 用来生成token的
// */
//@Component
//public class JWTTokenUtils {
//    private static AdminService staticadminService;
//    private static final Logger log = LoggerFactory.getLogger(JWTTokenUtils.class);
//
//    @Resource
//    private AdminService adminService;
//
//    @PostConstruct
//    public void setUserService() {
//        staticadminService = adminService;
//    }
//
//    //生成Token
//    public static String getToken(String adminId, String sign) {
//        return JWT.create().withAudience(adminId)//将userid保存到token中作为载荷
//                .withExpiresAt(DateUtil.offsetHour(new Date(), 2))//两小时后token过期
//                .sign(Algorithm.HMAC256(sign));//以password作为token的密钥
//    }
//
//
//}
