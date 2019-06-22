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
package cn.xiaoandx.exam.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.xiaoandx.commons.utils.IdAndTimeUtil;
import cn.xiaoandx.exam.entity.AnswerRecord;
import cn.xiaoandx.exam.entity.Question;
import cn.xiaoandx.exam.vo.AnswerUser;


/**  
 * <p>答题记录表</p> 
 * @ClassName:AnswerRecordDao   
 * @author: 伍富强  
 * @since: V0.2 
 * @version V0.2
 */
@Component
public class AnswerRecordDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	/**
	 * <p>答题后更新答题记录表</p> 
	 * @Title: addQuestion    
	 * @version:V0.2     
	 * @param question
	 * @param answerUser
	 * @return 
	 */
	public int addQuestion(Question question, AnswerUser answerUser) {
		String sql = "INSERT INTO  `answer_record` "
				+ "(`topicID`,`submit_Answers`,`judge_answer`,`userID`,`correct_answer`,`answer_time`,`knowledge_point`,`score`) "
				+ "VALUES (?,?,?,?,?,?,?,?)";
		int number = jdbcTemplate.update(sql,answerUser.getTopicID(),
				answerUser.getSubmit_Answers(),(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 0 : 1),
				answerUser.getUserID(),question.getANSWER(),IdAndTimeUtil.getNewDate(),question.getKNOWLEDGE_AREA(),
				(question.getANSWER().equalsIgnoreCase(answerUser.getSubmit_Answers()) ? 10 : -5));
		return number;
	}

	/**  
	 *<p>查询xx'用户最擅长的三个知识领域</p> 
	 * @Title: findKDByUserId    
	 * @version:V0.2     
	 * @param userId
	 * @return    
	 * @return:List<AnswerRecord>
	 */
	public List<AnswerRecord> findKDByUserId(Integer userID) {
		RowMapper<AnswerRecord> rowMapper=new BeanPropertyRowMapper<AnswerRecord>(AnswerRecord.class);
		String optionSql = "SELECT userID,`knowledge_point` ,COUNT(knowledge_point) AS number "
				+ "FROM  `answer_record`  "
				+ "WHERE userID = ?  AND judge_answer = 0  "
				+ "GROUP BY knowledge_point "
				+ "ORDER BY number DESC LIMIT 0,3";
		return jdbcTemplate.query(optionSql, rowMapper,userID);
	}
}
