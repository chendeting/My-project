package com.ad.web.controller;

import com.ad.dao.AdminUserDao;
import com.ad.entity.AdminUser;
import com.ad.entity.Role;
import com.ad.entity.RoleName;
import com.ad.entity.filed.UserParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/train")
public class LoginController {

    @Autowired
    private AdminUserDao adminUserDao;

    @RequestMapping("/login")
    public String login(@RequestParam(required = false, defaultValue = "0") int error, Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
            return "redirect:/train/index";
        }
        if (error == 1) {
            model.addAttribute("message", "用户名或密码错误");
        }
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        SecurityContextHolder.clearContext();
        return "login";
    }

    @RequestMapping(value = "/regist", method = RequestMethod.GET)
    public String regist() {
        return "register";
    }

    @RequestMapping(value = "/ajax-valide-username", method = RequestMethod.POST)
    @ResponseBody
    public boolean validateUsername(String username) {
        return adminUserDao.findByUserName(username) == null;
    }

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    public String addUser(UserParam userParam, RedirectAttributes redirectAttributes) {

        try {
            AdminUser adminUser = new AdminUser();
            adminUser.setUserName(userParam.getUsername());
            adminUser.setName(userParam.getName());
            adminUser.setPhone(userParam.getPhone());
            adminUser.setEmail(userParam.getEmail());
            adminUser.setShaPassword(userParam.getPassword());
            adminUser.setCardNo(userParam.getCardNo());
            Set<Role> roles = new HashSet<Role>();
            Role role = new Role();
            role.setRoleName(RoleName.admin);
            roles.add(role);
            adminUser.setRoles(roles);
            adminUserDao.save(adminUser);
            redirectAttributes.addFlashAttribute("registInfo", "注册成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("registInfo", e.getMessage());
        }
        return "redirect:/train/login";
    }
}
