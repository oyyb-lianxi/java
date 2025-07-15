package com.example.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 公告表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice  extends BasePojo {
    /**
     * id
     */
    private Long id;
    /**
     * title
     */
    private String title;
    /**
     * content
     */
    private String content;
}