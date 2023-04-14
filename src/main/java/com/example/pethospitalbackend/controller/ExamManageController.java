package com.example.pethospitalbackend.controller;

import com.example.pethospitalbackend.annotation.AdminMethod;
import com.example.pethospitalbackend.domain.response.CommonResponse;
import com.example.pethospitalbackend.service.ExamManage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/examManage")
//说明接口文件
@Api(value = "用户管理", tags = "examManage")
public class ExamManageController {
    @Autowired
    private ExamManage examManage;

    @AdminMethod
    @ApiOperation(value = "添加单个试题")
    @RequestMapping(value = "/addOneQuestion", method = RequestMethod.POST)
    public CommonResponse addOneQuestion(@RequestParam("disease_type_id") Integer disease_id,
                                         @RequestParam("title") String title,
                                         @RequestParam("optionA") String optionA,
                                         @RequestParam("optionB") String optionB,
                                         @RequestParam("optionC") String optionC,
                                         @RequestParam("optionD") String optionD,
                                         @RequestParam("answer") String answer) {
        return examManage.addOneQuestion(disease_id, title, optionA, optionB, optionC, optionD, answer);
    }

    @AdminMethod
    @ApiOperation(value = "删除单个试题")
    @RequestMapping(value = "/deleteOneQuestion", method = RequestMethod.GET)
    public CommonResponse deleteOneQuestion(@RequestParam("question_id") Integer question_id) {
        return examManage.deleteOneQuestion(question_id);
    }

    //    @AdminMethod
    //    @ApiOperation(value = "批量删除试题")
    //    @RequestMapping(value = "/deleteMultiQuestions", method = RequestMethod.POST)
    //    public CommonResponse deleteMultiQuestions(@RequestParam("question_ids") String question_ids) {
    //        return examManage.deleteMultiQuestions(question_ids);
    //    }

    //    @AdminMethod
    //    @ApiOperation(value = "获取所有试题")
    //    @RequestMapping(value = "/getAllQuestions", method = RequestMethod.GET)
    //    public CommonResponse getAllQuestions() {
    //        return examManage.getAllQuestions();
    //    }

    @AdminMethod
    @ApiOperation(value = "试题搜索")
    @RequestMapping(value = "/searchQuestion", method = RequestMethod.POST)
    public CommonResponse searchQuestion(@RequestParam("disease_type_id") Integer disease_type_id,
                                         @RequestParam("search_text") String search_text,
                                         @RequestParam("currentPage") Integer cur) {
        return examManage.searchQuestion(disease_type_id,search_text,cur);
    }

    @AdminMethod
    @ApiOperation(value = "获取单个试题详情")
    @RequestMapping(value = "/getOneQuestion", method = RequestMethod.GET)
    public CommonResponse getOneQuestion(@RequestParam("question_id") Integer question_id) {
        return examManage.getOneQuestion(question_id);
    }

    @AdminMethod
    @ApiOperation(value = "修改单个试题")
    @RequestMapping(value = "/modifyOneQuestion", method = RequestMethod.POST)
    public CommonResponse modifyOneQuestion(@RequestParam("question_id") Integer question_id,
                                            @RequestParam("disease_type_id") Integer disease_id,
                                            @RequestParam("title") String title,
                                            @RequestParam("optionA") String optionA,
                                            @RequestParam("optionB") String optionB,
                                            @RequestParam("optionC") String optionC,
                                            @RequestParam("optionD") String optionD,
                                            @RequestParam("answer") String answer) {
        return examManage.modifyOneQuestion(question_id,disease_id, title, optionA, optionB, optionC, optionD, answer);
    }

    @AdminMethod
    @ApiOperation(value = "添加单张试卷")
    @RequestMapping(value = "/addOnePaper", method = RequestMethod.POST)
    public CommonResponse addOnePaper(@RequestParam("disease_type_id") Integer disease_id,
                                      @RequestParam("question_ids") String question_ids,
                                      @RequestParam("question_num") Integer questionNum,
                                      @RequestParam("question_point") Integer questionPoints,
                                      @RequestParam("name") String name) {
        return examManage.addOnePaper(disease_id,name,question_ids,questionNum,questionPoints);
    }

    @AdminMethod
    @ApiOperation(value = "删除单张试卷")
    @RequestMapping(value = "/deleteOnePaper", method = RequestMethod.GET)
    public CommonResponse deleteOnePaper(@RequestParam("paper_id") Integer paper_id) {
        return examManage.deleteOnePaper(paper_id);
    }

    //    @AdminMethod
    //    @ApiOperation(value = "批量删除试卷")
    //    @RequestMapping(value = "/deleteMultiPapers", method = RequestMethod.POST)
    //    public CommonResponse deleteMultiPapers(@RequestParam("paper_ids") String paper_ids) {
    //        return examManage.deleteMultiPapers(paper_ids);
    //    }

    //    @AdminMethod
    //    @ApiOperation(value = "获取所有试卷")
    //    @RequestMapping(value = "/getAllPapers", method = RequestMethod.GET)
    //    public CommonResponse getAllPapers() {
    //        return examManage.getAllPapers();
    //    }

    @AdminMethod
    @ApiOperation(value = "获取单张试卷详情")
    @RequestMapping(value = "/getOnePaper", method = RequestMethod.GET)
    public CommonResponse getOnePaper(@RequestParam("paper_id") Integer paper_id) {
        return examManage.getOnePaper(paper_id);
    }

    @AdminMethod
    @ApiOperation(value = "试卷搜索")
    @RequestMapping(value = "/searchPaper", method = RequestMethod.POST)
    public CommonResponse searchPaper(@RequestParam("disease_type_id") Integer disease_type_id,
                                      @RequestParam("search_text") String search_text,
                                      @RequestParam("currentPage") Integer cur) {
        return examManage.searchPaper(disease_type_id,search_text,cur);
    }

    @AdminMethod
    @ApiOperation(value = "修改单张试卷")
    @RequestMapping(value = "/modifyOnePaper", method = RequestMethod.POST)
    public CommonResponse modifyOnePaper(@RequestParam("paper_id") Integer paper_id,
                                         @RequestParam("question_ids") String question_ids,
                                         @RequestParam("question_num") Integer questionNum,
                                         @RequestParam("question_point") Integer questionPoints,
                                         @RequestParam("name") String name) {
        return examManage.modifyOnePaper(paper_id,name,question_ids,questionNum,questionPoints);
    }

    @AdminMethod
    @ApiOperation(value = "添加单场考试")
    @RequestMapping(value = "/addOneExam", method = RequestMethod.POST)
    public CommonResponse addOneExam(@RequestParam("paper_id") Integer paper_id,
                                     @RequestParam("start_time") String start_time,
                                     @RequestParam("end_time") String end_time,
                                     @RequestParam("authority") Integer authority,
                                     @RequestParam("name") String name) {
        return examManage.addOneExam(paper_id,name,start_time,end_time,authority);
    }

    @AdminMethod
    @ApiOperation(value = "删除单场考试")
    @RequestMapping(value = "/deleteOneExam", method = RequestMethod.GET)
    public CommonResponse deleteOneExam(@RequestParam("exam_id") Integer ExamId) {
        return examManage.deleteOneExam(ExamId);
    }

    //    @AdminMethod
    //    @ApiOperation(value = "批量删除考试")
    //    @RequestMapping(value = "/deleteMultiExams", method = RequestMethod.POST)
    //    public CommonResponse deleteMultiExams(@RequestParam("examIds") String ExamIds) {
    //        return examManage.deleteMultiExams(ExamIds);
    //    }

    //    @AdminMethod
    //    @ApiOperation(value = "获取所有考试")
    //    @RequestMapping(value = "/getAllExams", method = RequestMethod.GET)
    //    public CommonResponse getAllExams() {
    //        return examManage.getAllExams();
    //    }

    @AdminMethod
    @ApiOperation(value = "获取单场考试详情")
    @RequestMapping(value = "/getOneExam", method = RequestMethod.GET)
    public CommonResponse getOneExam(@RequestParam("exam_id") Integer ExamId) {
        return examManage.getOneExam(ExamId);
    }

    @AdminMethod
    @ApiOperation(value = "修改单场考试")
    @RequestMapping(value = "/modifyOneExam", method = RequestMethod.POST)
    public CommonResponse modifyOneExam(@RequestParam("exam_id") Integer ExamId,
                                        @RequestParam("paper_id") Integer paper_id,
                                        @RequestParam("start_time") String start_time,
                                        @RequestParam("end_time") String end_time,
                                        @RequestParam("authority") Integer authority,
                                        @RequestParam("exam_name") String name) {
        return examManage.modifyOneExam(ExamId,paper_id,name,start_time,end_time,authority);
    }

    @AdminMethod
    @ApiOperation(value = "考试搜索")
    @RequestMapping(value = "/searchExam", method = RequestMethod.POST)
    public CommonResponse searchExam(@RequestParam("search_text") String search_text,
                                     @RequestParam("currentPage") Integer cur) {
        return examManage.searchExam(search_text,cur);
    }
}
