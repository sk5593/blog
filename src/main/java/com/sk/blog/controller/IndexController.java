package com.sk.blog.controller;

import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Contents;
import com.sk.blog.service.IndexService;
import com.sk.blog.utils.Commons;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired IndexService indexService;
    @RequestMapping("/")
    /**
     *
     * @param model
     * @param page 当前页
     * @param size 每页显示的文档数量
     * @return
     * 2019/4/8
     */

  public String index(Model model, Integer page, @RequestParam(defaultValue = "9") Integer size)
  {

    return this.page(1,size,model);

  }
  @GetMapping("/page/{p}")
  public String page(@PathVariable Integer p, @RequestParam(defaultValue = "9") Integer size, Model model)
  {
      PageInfo<Contents> allContents = indexService.getAllContents(p, size);
      model.addAttribute("articles",allContents);
      Commons commons=new Commons();
      model.addAttribute("commons",commons);
      return "index/index";

  }

    /**
     *
     * @return 关于我页面跳转
     */
  @RequestMapping("/aboutMe")
    public String about(Model model)
  {
      Commons commons=new Commons();
      model.addAttribute("commons",commons);
      return "/index/aboutme";
  }

}
