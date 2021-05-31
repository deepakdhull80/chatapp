package com.mywork.chatapp.Controller;

import com.mywork.chatapp.Model.User;
import com.mywork.chatapp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Controller
public class SignupController {

    @Autowired
    UserService userService;

    @GetMapping("/signup")
    String getSignUp(HttpServletRequest request){
        if(request.getSession().getAttribute("userid")!=null){
            return "redirect:/dashboard";
        }
        return "signup";
    }

    @PostMapping("/signup")
    String postSignUp(@RequestParam String username, @RequestParam String password, ModelMap map) throws NoSuchAlgorithmException, IOException {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);

        user = userService.addUser(user);

        if(user==null){
            map.addAttribute("error",true);
            return "signup";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    String logout(HttpServletRequest request){

        request.getSession().setAttribute("userid",null);
        return "redirect:/login";
    }



}
