package com.example.blogserver.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/16
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Permision {
    private String name;
    private String id;
    private String url;
    private String descript;
}
