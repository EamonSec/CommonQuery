package com.xin.util;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

/**
 * Spring JDBC Template中的 RowMapper接口的自定义实现类
 * 主要用于对于数据库中取的空值时,不调用对象的set方法,从而避免异常
 * 此外,对MYSQL数据中的DATETIME和TIMESTAMP类型作了处理,
 * 通过继承重写Date的toString()方法,去除无意义的"毫秒尾巴"
 * @author aooob
 * @param <T>
 */
public class CommonRowMapper<T> extends BeanPropertyRowMapper<T>{
	Class<T> type;
	private Method[] methods;
	private String[] methodsNames;
	
	private static Comparator<Method> comparator = new Comparator<Method>() {
		@Override
		public int compare(Method m1, Method m2) {
			return m1.getName().compareTo(m2.getName());
		}
	};

	public CommonRowMapper (Class<T> type){
		super(type);
		this.type = type;
		methods = type.getMethods();
		methodsNames = new String[methods.length];

		Arrays.sort(methods,comparator);
		for(int i=0;i<methods.length;i++){
			methodsNames[i] = methods[i].getName();
		}
		
		 
	}
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		try {
			T obj = type.newInstance();
			java.sql.ResultSetMetaData meta = rs.getMetaData();
			int colCount = meta.getColumnCount();
			
			for(int i=0;i<colCount;i++){
				String colName = meta.getColumnLabel(i+1);
				String setMethodName = "set"+Character.toUpperCase(colName.charAt(0))+colName.substring(1).toLowerCase();
				int index = Arrays.binarySearch(methodsNames,setMethodName);
				if(index >=0){
					final Object arg = rs.getObject(i+1);
					//字段为空时不处理
					if(arg!=null){
						if(arg instanceof java.util.Date){
							methods[index].invoke(obj,new java.util.Date(){
								private static final long serialVersionUID = 1L;
								@Override
								public String toString() {
									SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     
									String date = sDateFormat.format(arg);
									return date;  
								}
							});
						}
						else{
							methods[index].invoke(obj, arg);
						}
					}
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
