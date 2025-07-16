package com.example.service;

import com.example.model.domain.ProblemHelp;
import com.example.model.dto.ProblemHelpDto;
import com.example.model.entity.Result;

public interface ProblemHelpService {
    Result publishHelpInformation(ProblemHelp problemHelp);
    Result getProblemHelpByConditions(ProblemHelpDto problemHelp);
    Result updateProblemHelpById(ProblemHelp problemHelp);
    Result deleteProblemHelpById(ProblemHelp problemHelp);

    Result getProblemHelpById(Integer id);
}
