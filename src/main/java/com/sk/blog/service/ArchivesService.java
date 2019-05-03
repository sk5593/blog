package com.sk.blog.service;

import com.sk.blog.bean.Archives;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.ContentsExample;
import com.sk.blog.dao.ContentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@Service
public class ArchivesService {
    @Autowired
    ContentsMapper contentsMapper;

    public List<Archives> getAllArchives()
    {
        List<Archives> archives = contentsMapper.selectUnixTime();
        return archives;
    }


}
