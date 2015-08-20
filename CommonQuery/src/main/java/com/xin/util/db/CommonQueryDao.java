package com.xin.util.db;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.xin.pojo.User;
import com.xin.util.db.IRule;
import com.xin.util.db.JSONQueryParser;


/**
 * 自定义条件查询 测试Dao类
 * 
 * @author aooob
 *
 */
public class CommonQueryDao {
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<User> query(String jsonQueryString) {

		String sql = "select * from USER t ";
		List<User> result;
		 IRule rule = JSONQueryParser.parseJSON(jsonQueryString);
		 if(rule != null && rule.toExp().a.trim().length()>0){
			 
			 sql = sql + " where "+ rule.toExp().a;
			 result =(List<User>) jdbcTemplate.<User>query(sql, rule.toExp().b.toArray(new Object[]{}) , new  BeanPropertyRowMapper<User>(User.class));
		 }
		else {
			 sql += " limit  ?,? ";
			 result = (List<User>) jdbcTemplate.<User>query(sql, new Object[]{},  new  BeanPropertyRowMapper<User>(User.class));
		}
		
		return result;
	}
	
	public List<User> queryByPage(String jsonQueryString,int pageNumber,int pageSize){
		String sql = "select * from USER t ";
		List<User> result;
		 IRule rule = JSONQueryParser.parseJSON(jsonQueryString);
		 if(rule != null && rule.toExp().a.trim().length()>0){
			 
			 sql = sql + " where "+ rule.toExp().a;
			 sql += " limit  ?,? ";
			 
			 List<Object> para = rule.toExp().b;
			 Object[] objs = new Object[para.size()+2];
				int i=0;
				for(;i<para.size();i++){
					objs[i] = para.get(i);
				}
				objs[i++] = (pageNumber-1)*pageSize;
				objs[i] = pageSize;
				result =(List<User>) jdbcTemplate.<User>query(sql, objs , new  BeanPropertyRowMapper<User>(User.class));
		 }
		else {
			 sql += " limit  ?,? ";
			 result = (List<User>) jdbcTemplate.<User>query(sql, new Object[]{(pageNumber-1)*pageSize,pageSize},  new  BeanPropertyRowMapper<User>(User.class));
		}
		
		return result;
		
	}
	
	


}
