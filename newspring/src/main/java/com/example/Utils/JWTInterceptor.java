//package com.example.Utils;
//
//import cn.hutool.core.util.StrUtil;
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.example.entiity.Admin;
//import com.example.exception.CustomException;
//import com.example.service.AdminService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
///**
// * JWT拦截器（验证前端发送请求所携带的token是否合法）
// */
//@Component
//public class JWTInterceptor implements HandlerInterceptor {
//    private static final Logger log = LoggerFactory.getLogger(JWTInterceptor.class);
//
//    @Resource
//    AdminService adminService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
////        1从http请求的header中获取token
//        String token = request.getHeader("token");
//        if (StrUtil.isBlank(token)) {
////             请求头如果没有token，就去参数里边再拿一遍   /api/admin?token=xxxxx
//            token = request.getParameter("token");
//        }
////        2,开始验证token
//        if (StrUtil.isBlank(token)) {
//            throw new CustomException("无token，请重新登录");
//        }
////        获取token中的adminid
//        String adminID;
//        Admin admin;
//        try {
//            adminID = JWT.decode(token).getAudience().get(0);
////            根据token中的adminid查询数据库
//            admin = adminService.findbyid(Integer.parseInt(adminID));
//        } catch (Exception e) {
//            String errMag = "token验证失败，请重新登录";
//            log.error(errMag + ",token=" + token, e);
//            throw new CustomException(errMag);
//        }
//        if (admin == null) {
//            throw new CustomException("用户不存在，请重新登录");
//        }
//        try {
////           用户密码加签验证 token
//            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(admin.getPassword())).build();
//            jwtVerifier.verify(token);//验证token
//        } catch (JWTVerificationException e) {
//    throw new CustomException("token验证失败，请重新登录");
//        }
////        验证拦截器是否拦截
//        log.info("token验证成功");
//
//        return true;
//    }
//}
