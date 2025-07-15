package com.example.model.dto;

import com.example.model.domain.ProblemHelp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor  // 满参构造方法
@NoArgsConstructor  // 无参构造方法
public class ProblemHelpDto extends ProblemHelp {
    /**
     * 分页开始点
     */
    private Integer offSet;
    /**
     * 每页数量
     */
    private Integer pageSize;
    /**
     * 页码
     */
    private Integer page;
}
