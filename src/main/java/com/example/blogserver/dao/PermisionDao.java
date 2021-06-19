package com.example.blogserver.dao;

import com.example.blogserver.entity.Permision;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/16
 */
@Repository
public class PermisionDao {
    public List<Permision> findAll() {
        List<Permision> permisionsList = new ArrayList<>();
        Permision permision1 = new Permision();
        permision1.setId("1");
        permision1.setUrl("/customer/**");
        permision1.setName("customer");
        Permision permision2 = new Permision();
        permision2.setId("2");
        permision2.setUrl("/user/**");
        permision2.setName("all");
        permisionsList.add(permision1);
        permisionsList.add(permision2);
        return permisionsList;
    }
}
