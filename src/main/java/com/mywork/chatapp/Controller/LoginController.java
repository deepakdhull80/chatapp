package com.mywork.chatapp.Controller;

import com.mywork.chatapp.Model.User;
import com.mywork.chatapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.security.NoSuchAlgorithmException;

@Controller
public class LoginController {


    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request){
        if(request.getSession().getAttribute("userid")!=null){
            return "redirect:/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(ModelMap modelMap,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password ,
                            HttpServletRequest request) throws NoSuchAlgorithmException, IOException {

        User user = userService.validateUser(username,password);
        if(user != null){

            HttpSession session = request.getSession();
            session.setAttribute("userid",String.valueOf(user.getId()));
            return "redirect:/dashboard";
        }
        modelMap.addAttribute("hasError",true);

        return "login";
    }






}
