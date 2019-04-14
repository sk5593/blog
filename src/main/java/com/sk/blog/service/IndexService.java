package com.sk.blog.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.ContentsExample;
import com.sk.blog.dao.ContentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexService {
    @Autowired
    ContentsMapper contentsMapper;
    public PageInfo<Contents> getAllContents(Integer page,Integer size)
    {
        ContentsExample contentsExample = new ContentsExample();
        ContentsExample.Criteria publish = contentsExample.createCriteria().andStatusEqualTo("publish");
        PageHelper.startPage(page,size);
        List<Contents> contents = contentsMapper.selectByExample(contentsExample);

        PageInfo<Contents> pageInfo =new PageInfo<>(contents);
        return pageInfo;
    }

}
