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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import cn.xiaoandx.exam.entity.Question;
import lombok.extern.slf4j.Slf4j;

/**  
 * <p>试题dao层操作</p> 
 * @ClassName:QuestionDao   
 * @author: junzhi.liu 
 * @version V0.1
 */
@Component
@Slf4j
public class QuestionDao {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	/**
	 *<p>根据试题id查询试题</p>     
	 * @version:V0.1     
	 * @param id	试题id
	 * @return:Question	返回试题内容对象
	 */
	public Question findById(String id) {
		RowMapper<Question> questionRowMapper=new BeanPropertyRowMapper<Question>(Question.class);
		String questionSql = "SELECT ID,CONTENT,ANSWER,KNOWLEDGE_AREA,CREATED_DATE "
				+ "FROM t_question WHERE ID=?";
		return jdbcTemplate.queryForObject(questionSql, questionRowMapper, id);
	}

	/** 
	 *<p>添加试题</p> 
	 * @version:V0.1     
	 * @param question    试题对象
	 * @return:number	添加试题成功返回1，失败返回0
	 */
	public int addQuestion(Question question) {
		String addSql = "INSERT INTO `t_question` (`ID`,`CONTENT`,`ANSWER`,`KNOWLEDGE_AREA`,`CREATED_DATE`) VALUES (?,?,?,?,?)";
		int number = jdbcTemplate.update(addSql,question.getID(),question.getCONTENT(),question.getANSWER(),question.getKNOWLEDGE_AREA(),question.getCREATED_DATE());
		log.info("添加试题"+String.valueOf(number));
		return number;
	}

	/**  
	 *<p>修改试题</p>     
	 * @version:V0.1     
	 * @param question    包含修改后的新试题对象
	 * @return:number	修改试题成功返回1，失败返回0
	 */
	public int updateQuestion(Question question) {
		String sql = "UPDATE  `t_question` SET `CONTENT` = ?, `ANSWER` = ? WHERE `ID` = ?";
		int number = jdbcTemplate.update(sql,question.getCONTENT(),question.getANSWER(),question.getID());
		log.info("修改试题"+String.valueOf(number));
		return number;
	}

	/**  
	 *<p>删除试题</p> 
	 * @Title: deleteQuestion    
	 * @version:V0.1     
	 * @param id    试题id
	 * @return:number	删除试题成功返回1，失败返回0
	 */
	public int deleteQuestion(String id) {
		String sql = "DELETE FROM `t_question` WHERE `ID` = ?";
		int number = jdbcTemplate.update(sql,id);
		log.info("删除试题"+String.valueOf(number));
		return number;
	}
}
