package com.koliday.springproject.springbootexercise.mapper;

import com.koliday.springproject.springbootexercise.Model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into user(account_id,name,token,gmt_create,gmt_modified) values(#{accountId},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insertUser(User user);
}
