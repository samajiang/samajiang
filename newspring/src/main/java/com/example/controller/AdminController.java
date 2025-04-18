package com.example.controller;

import com.example.common.Result;
import com.example.entiity.Admin;

import com.example.entiity.Params;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@CrossOrigin
@RequestMapping("/admin")
@RestController
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Resource
    AdminService adminService;

    @PostMapping("/register")
    public Result register(@RequestBody Admin admin) {
        System.out.println(admin);
        adminService.add(admin);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody Admin admin) {
        Admin Loginuser = adminService.login(admin);
        return Result.success(Loginuser);
    }

    @PostMapping("/updata")
    public Result save(@RequestBody Admin admin) {
        if (admin.getUser_id() == null) {
            adminService.add(admin);

        } else {
            adminService.update(admin);
        }
        return Result.success();
    }


    @GetMapping("/search")
    public Result findbysearch(Params params) {
        log.info("拦截器已放行,可以调用查询管理员信息接口");
        PageInfo<Admin> info = adminService.findbusearch(params);
        return Result.success(info);
    }

    //这个id是从前端传过来的要删除行的用户id
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id) {
        adminService.delete(id);
        return Result.success();
    }

    // 配置文件中定义的图片保存路径
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * 处理头像上传请求
     * @param file 上传的文件
     * @param userId 用户ID
     * @return 上传结果
     */
    @PostMapping("/upload/avatar")
    public ResponseEntity<?> uploadAvatar(
            @RequestParam("avatar") MultipartFile file,
            @RequestParam("userId") String userId) {

        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "请选择要上传的文件");
                return ResponseEntity.badRequest().body(error);
            }

            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "只能上传图片文件");
                return ResponseEntity.badRequest().body(error);
            }

            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + extension;

            // 确保上传目录存在
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 保存文件到服务器
            Path path = Paths.get(uploadPath + File.separator + newFileName);
            Files.write(path, file.getBytes());

            // 保存文件信息到数据库
            String fileUrl = "/images/" + newFileName;
            adminService.updateUserAvatar(userId, fileUrl);

            // 返回成功响应
            Map<String, String> response = new HashMap<>();
            response.put("message", "头像上传成功");
            response.put("avatarUrl", fileUrl);

            return ResponseEntity.ok(response);

        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "文件上传失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "服务器错误: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }



    /**
     * 获取用户头像
     * @param username 用户名字
     * @return 用户头像URL
     */
    @GetMapping("/avatar/{username}")
    public ResponseEntity<?> getUserAvatar(@PathVariable String username) {
        try {
            String avatarUrl = adminService.getUserAvatar(username);
            log.info("收到获取头像请求 - userId: {}", username);
            log.info(avatarUrl+"");
            if (avatarUrl == null) {
                return ResponseEntity.notFound().build();
            }
            Map<String, String> response = new HashMap<>();
            response.put("avatarUrl", avatarUrl);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "获取头像失败: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }

//    后端登录接口
    @PostMapping("/loginpc")
    public Result loginpc(@RequestBody Admin admin){
        Admin Loginuser = adminService.loginpc(admin);
        return Result.success(Loginuser);
    }


}
