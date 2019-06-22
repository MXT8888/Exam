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

import cn.xiaoandx.exam.entity.Question;
import cn.xiaoandx.exam.entity.QuestionOption;


/**  
 * <p>随机出题 </p> 
 * @ClassName:ExamDao   
 * @author: Fuqiang.Wu
 * @since: V0.1 
 * @version V0.1
 */
@Component
public class ExamDao {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public Question findById(String id) {
		RowMapper<QuestionOption> rowMapper=new BeanPropertyRowMapper<QuestionOption>(QuestionOption.class);
		RowMapper<Question> questionRowMapper=new BeanPropertyRowMapper<Question>(Question.class);
		String questionSql = "SELECT ID,CONTENT,ANSWER,KNOWLEDGE_AREA,CREATED_DATE FROM t_question WHERE ID=?";
		String optionSql = "SELECT ID,`OPTION`,OPTION_CONTENT,QUESTION_ID,CREATED_DATE FROM t_question_option WHERE QUESTION_ID = ?";
		Question question = jdbcTemplate.queryForObject(questionSql, questionRowMapper, id);
		List<QuestionOption> list = jdbcTemplate.query(optionSql, rowMapper,id);
		question.setQuestionOptions(list);
		return question;
	}

}
