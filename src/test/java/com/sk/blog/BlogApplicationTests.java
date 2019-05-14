package com.sk.blog;

import com.sk.blog.bean.Contents;

import com.sk.blog.dao.ContentsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

	@Autowired
	ContentsMapper contentsMapper;
	@Test
	public void contextLoads() {
      new Thread(()->{

      }).start();
	}
	@Test
	public void test01()
	{
		Set set = new HashSet();
		set.add("123");
		set.add("456");
		set.add("abc");
		System.out.println("set:"+set);

	}




	@Test
//    @Transactional(rollbackFor = Exception.class)
	public void myTest ()
	{

            Contents contents = new Contents();
            contents.setTitle("222");
            contents.setContent("223322");
            contentsMapper.insert(contents);

//            int x=10/0;


	}
	@Test
	public void Test03()
	{
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println(service);
		for (int x=0;x<2;x++)
		{
			//给了两个任务,启动两个线程
			service.execute(()->{
//                try {
//                    TimeUnit.MILLISECONDS.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
				System.out.println(Thread.currentThread().getName());
			});
		}
	}

}
