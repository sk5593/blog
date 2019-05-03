package com.sk.blog.service;

import com.sk.blog.bean.Categories;
import com.sk.blog.bean.Contents;
import com.sk.blog.bean.ContentsExample;
import com.sk.blog.dao.ContentsMapper;
import com.sk.blog.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
public class CategoryService {
    ContentsExample example = new ContentsExample();

    @Autowired
    ContentsMapper contentsMapper;

    /**
     * 获取所有的类别的id和类别名
     *
     * @return
     */
    public List<Categories> getAllCategories() {
        List<Categories> allCategories = contentsMapper.getAllCategories();
        return allCategories;
    }

    /**
     * 根据id获取对应的分类数量
     *
     * @param id
     * @return
     */
    public Integer selectCountByCategories(Integer id) {
        Integer integer = contentsMapper.selectCountByCategories(id);
        return integer;
    }

    /**
     * 根据name获取id
     */
    public Integer selectIdByName(String name) {
        Integer kid = contentsMapper.selectIdByName(name);
        return kid;
    }

    /**
     * 根据分类名获取对应的数量
     */
    public Integer selectCountByCategory(String cname) {
        Integer count = contentsMapper.selectCountByCategory(cname);
        return count;
    }

    /**
     * 保存分类
     */

    public Result saveCategory(String cname) {

        contentsMapper.saveCategory(cname);
        return Result.ok();

    }
    /**
     * 删除分类
     */
    public Result deleteCategory(Integer kid)
    {
        contentsMapper.deleteCategory(kid);
        return Result.ok();
    }
    /**
     * 修改分类
     */
    @Transactional
    public Result updateCategory(String cname,Integer kid)
    {
       try {
           contentsMapper.updateCategory(cname,kid);
           return Result.ok();
       }
       catch (Exception e){
           TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
           return Result.msg("修改分类失败！");
       }

    }
}
