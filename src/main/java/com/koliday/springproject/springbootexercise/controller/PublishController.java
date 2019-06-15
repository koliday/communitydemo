package com.koliday.springproject.springbootexercise.controller;

import com.koliday.springproject.springbootexercise.Model.Question;
import com.koliday.springproject.springbootexercise.Model.User;
import com.koliday.springproject.springbootexercise.mapper.QuestionMapper;
import com.koliday.springproject.springbootexercise.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(HttpServletRequest request,
                            @RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title==null || "".equals(title)){
           model.addAttribute("error","title不能为空");
           return "publish";
        }
        if(description==null || "".equals(description)){
            model.addAttribute("error","description不能为空");
            return "publish";
        }
        if(tag==null || "".equals(tag)){
            model.addAttribute("error","tag不能为空");
            return "publish";
        }



        Cookie[] cookies=request.getCookies();
        User user=null;
        if(cookies!=null){
            for(Cookie cookie:cookies){
                if("token".equals(cookie.getName())){
                    String token=cookie.getValue();
                    user=userMapper.findByToken(token);
                    break;
                }
            }
        }
        if(user==null){
            model.addAttribute("error","用户信息错误！");
            return "publish";
        }else{
            int creator=user.getId();
            Question question=new Question(title,description,System.currentTimeMillis(),
                    System.currentTimeMillis(),creator,tag);
            questionMapper.publish(question);
            return "redirect:/";
        }

    }
}
