package com.example.mapper;

import com.example.entiity.Admin;
import com.example.entiity.Params;
import com.example.service.AdminService;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdminMapper extends Mapper<Admin> {

    // 添加一个静态的Logger
    Logger logs = LoggerFactory.getLogger(AdminMapper.class);



    List<Admin> findbysearch(@Param("params") Params params);

    // 使用@Options注解添加日志
    @Options(useGeneratedKeys = true)
    @Select("select * from userinfo where user_name = #{username} limit 1")
     Admin doFindByUserId(@Param("username") String username);


    @Select("select * from userinfo where user_name = #{user_name} and password = #{password} limit 1")
    Admin findbynameandpassword(@Param("user_name") String user_name,@Param("password") String password);

    /**
     * 更新用户头像
     * @param userId 用户ID
     * @param avatarUrl 头像URL
     * @return 更新的行数
     */
    @Update("UPDATE userinfo SET avatar_url = #{avatarUrl} WHERE user_name = #{userId}")
    int updateAvatarUrl(@Param("userId") String userId, @Param("avatarUrl") String avatarUrl);


    // 使用default方法来添加日志
    default Admin findByUserId(String username) {
        logs.info("Mapper层接收到的userId: {}", username);
        // 打印SQL语句
        logs.info("即将执行SQL: SELECT * FROM users WHERE user_name = '{}'", username);
        Admin user = doFindByUserId(username);
        logs.info("查询结果: {}", user);
        return user;
    }

}
