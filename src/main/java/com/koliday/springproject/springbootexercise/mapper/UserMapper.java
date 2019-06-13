package com.koliday.springproject.springbootexercise.mapper;

import com.koliday.springproject.springbootexercise.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insertUser(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
