package com.sk.blog.controller;

import com.sk.blog.bean.Archives;
import com.sk.blog.bean.Contents;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.service.ArchivesService;
import com.sk.blog.service.ArticleService;
import com.sk.blog.service.IndexService;
import com.sk.blog.utils.Commons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.util.List;


@Controller
public class ArchivesController {
    Commons commons=new Commons();
    @Autowired
    IndexService indexService;

    /**
     * 2019/4/14
     * 根据时间 查询出相应文章，和文章数量
     * 1.创建一个实体类，包括 year mouth count articles,一个实体类对应了一个月份的文章集合
     * 2.
     * @return
     */
    @Autowired
    ArchivesService archivesService;
    @Autowired
    ContentsMapper contentsMapper;
    @RequestMapping("/archives")

    public String showArchives(Model model) {
        //根据月份分组，并获得每月的文章数量和对应的月份
        List<Archives> allArchives = archivesService.getAllArchives();

        //根据月份查询文章内容
        for (Archives archives: allArchives
                ) {
            String month = archives.getData();

            List<Contents> contents = contentsMapper.getContentsByMonths(month);

            archives.setArticleList(contents);

//            时间格式化
//            substring 切割字符串 如果只有一个参数，从该参数开始，返回后面的字符串
//            留前不留后
            String smonth = String.valueOf(month);
            String year = smonth.substring(0, 4);
            year+="年";
            String m = smonth.substring(4);
            m+="月";
            String newTime=year+m;
            archives.setData(newTime);


        }

        model.addAttribute("commons",commons);
        model.addAttribute("archives",allArchives);
        return "index/archives";
    }
}
