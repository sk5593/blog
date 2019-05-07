package com.sk.blog;

import com.sk.blog.bean.Contents;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.dao.VisitorsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

	@Autowired
	ContentsMapper contentsMapper;
	@Test
	public void contextLoads() {


	}
	@Test
    @Transactional(rollbackFor = Exception.class)
	public void myTest ()
	{
	    try {
            Contents contents = new Contents();
            contents.setTitle("222");
            contents.setContent("223322");
            contentsMapper.insert(contents);

        }catch (Exception e)
        {
            throw  e;
        }

	}

}
