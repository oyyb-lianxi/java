package com.example.service.impl;

import com.example.mapper.ProblemHelpMapper;
import com.example.model.domain.Notice;
import com.example.model.domain.ProblemHelp;
import com.example.model.dto.ProblemHelpDto;
import com.example.model.entity.Result;
import com.example.service.ProblemHelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class ProblemHelpServiceImpl implements ProblemHelpService {
    @Autowired
    ProblemHelpMapper problemHelpMapper;
    @Override
    public Result publishHelpInformation(ProblemHelp problemHelp) {
        Result result = new Result();
        int integer = problemHelpMapper.saveProblemHelp(problemHelp);
        if(integer == 1){
            result.setCode(200);
            result.setMsg("发布成功");
        }else {
            result.setCode(403);
            result.setMsg("发布失败");
        }
        return result;
    }
    @Override
    public Result getProblemHelpByConditions(ProblemHelpDto problemHelpDto) {
        Result result = new Result();
        if (problemHelpDto.getPage() != null && problemHelpDto.getPageSize() != null) {
            problemHelpDto.setOffSet((problemHelpDto.getPage() - 1) * problemHelpDto.getPageSize());
        }
        HashMap<String, Object> map = new HashMap();
        List<ProblemHelp> allProblemHelp = problemHelpMapper.getProblemHelpByConditions(problemHelpDto);
        int countProblemHelpByConditions = problemHelpMapper.countProblemHelpByConditions(problemHelpDto);
        map.put("problemHelpList",allProblemHelp);
        map.put("total",countProblemHelpByConditions);
        result.setCode(200);
        result.setData(map);
        return result;
    }


    @Override
    public Result updateProblemHelpById(ProblemHelp problemHelp) {
        Result result = new Result();
        int integer = problemHelpMapper.updateProblemHelpById(problemHelp);
        if(integer == 1){
            result.setCode(200);
            result.setMsg("修改成功");
        }else {
            result.setCode(403);
            result.setMsg("修改失败");
        }
        return result;
    }

    @Override
    public Result deleteProblemHelpById(ProblemHelp problemHelp) {
        Result result = new Result();
        int integer = problemHelpMapper.deleteProblemHelpById(problemHelp);
        if(integer == 1){
            result.setCode(200);
            result.setMsg("删除成功");
        }else {
            result.setCode(403);
            result.setMsg("删除失败");
        }
        return result;
    }
}
