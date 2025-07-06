package com.example.model.domain;

//import com.baomidou.mybatisplus.annotation.FieldFill;
//import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class BasePojo implements Serializable {
//    @TableField(fill = FieldFill.INSERT) //自动填充
    private LocalDateTime created;
//    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updated;
}

