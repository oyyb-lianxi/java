package com.example.model.vo;

import com.example.model.domain.ProblemHelp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemHelpVo {
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
    private Long created;
    private Long updated;
}
