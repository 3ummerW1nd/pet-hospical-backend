package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.ExamService;
import com.example.pethospitalbackend.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam")
//说明接口文件
@Api(value = "在线测试", tags = "exam")
public class ExamController {
    @Autowired
    private ExamService examService;

    @ApiOperation(value = "获取所有考试")
    @RequestMapping(value = "/getAllExams", method = RequestMethod.POST)
    public CommonResponse getAllExams(@RequestHeader("Authorization") String token,
                                      @RequestParam("currentPage") Integer currentPage) {
        int user_id = TokenUtil.getUserRoleFromToken(token).getId();
        return examService.getAllExams(user_id, currentPage);
    }

    @ApiOperation(value = "更新用户考试信息")
    @RequestMapping(value = "/updateUserExam", method = RequestMethod.POST)
    public CommonResponse updateUserExam(@RequestHeader("Authorization") String token,
                                         @RequestParam("exam_id") Integer exam_id,
                                         @RequestParam("history_score") Integer history_score) {
        int user_id = TokenUtil.getUserRoleFromToken(token).getId();
        return examService.updateUserExam(user_id, exam_id,history_score);
    }


}
