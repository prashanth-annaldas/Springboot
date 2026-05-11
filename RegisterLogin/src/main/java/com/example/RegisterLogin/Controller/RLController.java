package com.example.RegisterLogin.Controller;

import com.example.RegisterLogin.Model.RLModel;
import com.example.RegisterLogin.Repository.DAOModel;
import com.mysql.cj.callback.UsernameCallback;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RLController {
    @Autowired
    private DAOModel DAOModel;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String uname, @RequestParam("pass") String pass, Model model){
        RLModel rlModel = new RLModel();
        rlModel.setUsername(uname);
        rlModel.setPass(pass);
        if(DAOModel.searchRegister(rlModel)) {
            DAOModel.save(rlModel);
            return "redirect:/login";
        }
        else {
            model.addAttribute("failedMsg", uname +" already exists!!");
            return "/register";
        }
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String uname, @RequestParam("pass") String pass, Model model){
        RLModel rlModel = new RLModel();
        rlModel.setUsername(uname);
        rlModel.setPass(pass);
        if(DAOModel.searchLogin(rlModel)){
            return "redirect:/home/" + uname;
        }
        else {
            model.addAttribute("failMsg", "Login Failed");
            return "login";
        }
    }

    @GetMapping("/forgot")
    public String forgotPage(){
        return "forgot";
    }

    @PostMapping("/forgot")
    public String forgotPass(@RequestParam("username") String uname, @RequestParam("password") String pass, @RequestParam("confPass") String cPass, Model model){
        RLModel rlModel = new RLModel();
        rlModel.setUsername(uname);
        rlModel.setPass(pass);
        rlModel.setcPass(cPass);
        if(!pass.equals(cPass)){
            model.addAttribute("msg", "Password not match");
            return "forgot";
        }
        if(!DAOModel.userExists(uname)){
            model.addAttribute("msg", "User not exists!!");
            return "/forgot";
        }
        DAOModel.changePassword(uname, pass);
        model.addAttribute("msg", "Password updated successfully");
        return "login";
    }

    @GetMapping("/home/{username}")
    public String home(@PathVariable("username") String uname, Model model) {
        model.addAttribute("welcomeMsg", "Welcome to Home " + uname);
        model.addAttribute("username", uname);
        return "home";
    }


    @GetMapping("/deleteAcc/{username}")
    public String deleteAcc(@PathVariable("username") String uname){
        DAOModel.deleteAcc(uname);
        return "register";
    }
}
