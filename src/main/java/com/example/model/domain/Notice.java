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
     * id
     */
    private String title;
    /**
     * id
     */
    private String content;
}