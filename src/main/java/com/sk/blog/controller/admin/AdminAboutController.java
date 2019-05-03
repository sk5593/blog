package com.sk.blog.controller.admin;

import com.sk.blog.bean.AboutMe;
import com.sk.blog.service.AboutService;
import com.sk.blog.utils.Commons;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.DoubleToIntFunction;

@Controller
public class AdminAboutController {
    Commons commons = new Commons();
    @Autowired
    AboutService aboutService;

    @GetMapping("/admin/about")
    public String redirectAbout(Model model)
    {
        List<AboutMe> allAbout = aboutService.getAllAbout();
        model.addAttribute("abouts",allAbout);
        model.addAttribute("commons",commons);
        return "admin/links";
    }
    /**
     * 保存关于
     */
    @PostMapping("/admin/about")
    @ResponseBody
    public Result saveAbout(AboutMe aboutMe)
    {
        if(aboutMe.getAid()!=null)
        {
            //更新
            Result result = aboutService.updateAbout(aboutMe);
            return result;

        }

        Result result = aboutService.insertAbout(aboutMe);
        return result;
    }

    /**
     * 删除关于
     * @return
     */
    @DeleteMapping("/admin/about")
    @ResponseBody
    public Result deleteAbout(Integer aid)
    {

        Result result = aboutService.deleteAbout(aid);
        return result;
    }
}
