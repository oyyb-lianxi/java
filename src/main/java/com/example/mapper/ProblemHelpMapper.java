package com.example.mapper;

import com.example.model.domain.Notice;
import com.example.model.domain.ProblemHelp;
import com.example.model.dto.ProblemHelpDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProblemHelpMapper {
    int saveProblemHelp(ProblemHelp problemHelp);

    int countProblemHelpByConditions(ProblemHelp problemHelp);

    List<ProblemHelp> getProblemHelpByConditions(ProblemHelpDto problemHelpDto);

    int updateProblemHelpById(ProblemHelp problemHelp);

    int deleteProblemHelpById(ProblemHelp problemHelp);
    @Select("SELECT * FROM problemHelp  WHERE id = #{id}")
    ProblemHelp getProblemHelpById(@Param("id") Integer id);
}
