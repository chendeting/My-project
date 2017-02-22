package com.ad.web.controller;

import com.ad.dao.AdminUserDao;
import com.ad.entity.AdminUser;
import com.ad.entity.filed.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/train")
public class UserController {
    @Autowired
    private AdminUserDao adminUserDao;

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    //返回一个字符串类型的方法
    public String info(Model model, Principal principal) {
        model.addAttribute("user", adminUserDao.findByUserName(principal.getName()));
        return "info";
    }
   //修改用户信息
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    @ResponseBody
    public String update(Model model, Principal principal, UserParam newUser) {
        String result;
        try {
            AdminUser user = adminUserDao.findByUserName(principal.getName());
            user.setName(newUser.getName());
            user.setCardNo(newUser.getCardNo());
            user.setEmail(newUser.getEmail());
            user.setPhone(newUser.getPhone());
            if (!newUser.getPassword().equals("")) {
                user.setShaPassword(newUser.getPassword());
                result = "0";
            } else {
                result = "1";
            }
            model.addAttribute("user", user);
            adminUserDao.save(user);
        } catch (Exception e) {
            result = "2";
        }
        return result;
    }

}
