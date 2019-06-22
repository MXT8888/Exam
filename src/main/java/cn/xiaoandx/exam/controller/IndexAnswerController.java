/*
 * Copyright 2012-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.xiaoandx.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.xiaoandx.commons.utils.IdAndTimeUtil;
import cn.xiaoandx.exam.entity.Question;
import cn.xiaoandx.exam.entity.TotalCollcetSubject;
import cn.xiaoandx.exam.service.IndexAnswerService;
import cn.xiaoandx.exam.vo.AboutMe;
import cn.xiaoandx.exam.vo.AnswerRanking;
import cn.xiaoandx.exam.vo.AnswerUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**  
 * <p>用户答题相关操作API</p> 
 * @ClassName:IndexAnswerController   
 * @author: 伍富强   
 * @since: V0.2 
 * @version V0.2
 * 
 */
@RequestMapping("/v2/open/exam")
@RestController
@Api(tags = "用户答题操作相关API")
public class IndexAnswerController {
	@Autowired
	private IndexAnswerService indexAnswerService;
	
	/**
	 *<p>查询总成绩</p> 
	 * @Title: findSrcoeByOpenId    
	 * @version:V0.2     
	 * @param userId
	 * @return:TotalCollcetSubject
	 */
	@GetMapping(value = "/findResult/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET",value = "查询用户的总分",notes = "根据用户userId查询<br><br><b>@author 伍富强</b>")	
	public TotalCollcetSubject findSrcoeByOpenId(@ApiParam(value = "userID*必填",required = true) @PathVariable int userId) {
		return indexAnswerService.findSrcoeByOpenId(userId);
	}
	
	/**
	 *<p>查询排名（前50）APi</p> 
	 * @Title: findAllRanking    
	 * @version:V0.2     
	 * @return:List<AnswerRanking>
	 */
	@GetMapping(value = "/findRanking",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET",value = "查询排名",notes = "<br><br><b>@author 伍富强</b>")
	public List<AnswerRanking> findAllRanking() {
		return indexAnswerService.findRanking();
	}
	
	/**
	 *<p>随机抽出一道题（包括选项）</p>  
	 * @version:V0.2     
	 * @param id		随机id
	 * @return:Question	返回试题试题对象
	 */
	@GetMapping(value = "/findOneQuestion",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "随机抽出一道题(包括选项)", notes = "随机抽出一道题(包括选项)<br><br><b>@author 伍富强</b>")
	
	public Question findById() {
		return indexAnswerService.findById(IdAndTimeUtil.getQuestion());
	}
	
	/**
	 *<p>提交答案（判断正确更新答题记录数据）</p> 
	 * @Title: answer    
	 * @version:V0.2     
	 * @param answerUser			传入的答题数据
	 * @return:TotalCollcetSubject	一个当前XX的答题情况
	 */
	@PostMapping(value = "/submitAnswers",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST",value = "XX用户提交答案（判断正确更新答题记录数据）",notes = "需要传入topicID，userID，submit_Answers<br><br><b>@author 伍富强</b>")
	
	public TotalCollcetSubject answer(@ApiParam(value = "提交对象*必填",required = true) @RequestBody AnswerUser answerUser) {
			return indexAnswerService.answer(answerUser);
	}
	
	/**
	 *<p>获取用户的数据</p> 
	 * @Title: findAllById    
	 * @version:V0.2     
	 * @param userId	用户id
	 * @return:AboutMe	用户的数据对象
	 */
	@GetMapping(value = "/findMeById/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET",value = "获取关于用户的详细数据",notes = "也可以作为排行榜详细用户的数据的api<br><br><b>@author 伍富强</b>")
	
	public AboutMe findAllById(@ApiParam(value = "用户id*必填",required = true) @PathVariable("userId") Integer userId) {
		return indexAnswerService.findAllById(userId);
	}
	
}
