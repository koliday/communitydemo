package com.koliday.springproject.springbootexercise.controller;

import com.koliday.springproject.springbootexercise.Model.User;
import com.koliday.springproject.springbootexercise.dto.AccessTokenDTO;
import com.koliday.springproject.springbootexercise.dto.GitHubUser;
import com.koliday.springproject.springbootexercise.mapper.UserMapper;
import com.koliday.springproject.springbootexercise.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accesstoken=gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser=gitHubProvider.getUser(accesstoken);
        if(gitHubUser!=null){
            //登录成功
            User user=new User();
            user.setToken(UUID.randomUUID().toString());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getLogin());
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insertUser(user);
            request.getSession().setAttribute("user",user);
            return "redirect:/";//重定向到index，而没有后面一长串字符
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
