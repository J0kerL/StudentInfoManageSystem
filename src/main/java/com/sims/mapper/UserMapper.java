package com.sims.mapper;

import com.github.pagehelper.Page;
import com.sims.model.dto.user.PageQueryDTO;
import com.sims.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Diamond
 * @create 2025-11-12 16:22
 */
public interface UserMapper {
    @Select("select *from sys_user where username = #{username}")
    User findByUsername(String username);

    Page<User> page(PageQueryDTO pageQueryDTO);

    @Insert("INSERT INTO sys_user (username, password, real_name, phone, email, user_type, status, create_time, update_time) " +
            "VALUES (#{username}, #{password}, #{realName}, #{phone}, #{email}, #{userType}, #{status}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Select("select *from sys_user where id = #{userId}")
    User findById(Long userId);

    void update(@Param("user") User user, @Param("currentUserId") Long currentUserId);
}
