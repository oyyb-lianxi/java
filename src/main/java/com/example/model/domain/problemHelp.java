package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 问题帮助表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class problemHelp  extends BasePojo {
    /**
     * id
     */
    private Long id;
    /**
     * title
     */
    private String title;
    /**
     * type 1学生家长问题 2老师问题
     */
    private String type;
    /**
     * content
     */
    private String content;
}