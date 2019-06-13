package com.koliday.springproject.springbootexercise.controller;

import com.koliday.springproject.springbootexercise.dto.AccessTokenDTO;
import com.koliday.springproject.springbootexercise.dto.GitHubUser;
import com.koliday.springproject.springbootexercise.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String client_id;
    @Value("${github.client.secret}")
    private String client_secret;
    @Value("${github.redirect.uri}")
    private String redirect_uri;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirect_uri);
        accessTokenDTO.setState(state);
        String accesstoken=gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser user=gitHubProvider.getUser(accesstoken);
        System.out.println(user.toString());
        return "index";
    }
}