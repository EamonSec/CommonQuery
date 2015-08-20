package com.xin.util.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *  <pre>
 *  自定义查询转换成SQL条件子句的通过查询工具
 *  使用gson工具包解析,解析时也可以FlexJSON解析
 *  
 *  前端自定义查询时:
 *  前端传值方式: JSON.stringify(obj) 
 *  单个条件时:
 *  obj的结构为   {field:'',op:'',value:''} 分别代表字段,逻辑关系和值,如 id>5
 *  多个条件时:
 *  obj的结构为  {logicRelation:'or',rules:[]} rules中的对象为单个条件的数组
 *  
 *  必要时,也可以在后端自定义查询
 *  
 *  logicRelation为多个条件时的逻辑关系,可为'or'或'and'
 *  field对应实体类的属性名称及数据库中表的列名 
 *  op可为下列值:contains,notcontains,beginwith,endwith,less,lessorequal,greater,greaterorequal,equal,notequal 
 *  value为用户自定义
 *  
 * 	
 *  可以使用基于Gson的JsonToMap反序列化成MAP
 *  <dependency>
 *	<groupId>com.google.code.gson</groupId>
 *  <artifactId>gson</artifactId>
 *  <version>2.3.1</version>
 *	</dependency>
 *  
 *  也可以用FlexJson反序列化成MAP
 *  <dependency>
 *	<groupId>net.sf.flexjson</groupId>
 *  <artifactId>flexjson</artifactId>
 * 	<version>3.3</version>
 *  </dependency>
 *  
 *  </pre>
 *  
 *  @author aooob
 *
 */

public class JSONQueryParser {

		public static IRule parseJSON(String json){
			 if(json == null || json.trim().length() <= 0)
				 return null;
			/* JSONDeserializer<IRule> der = new JSONDeserializer<IRule>();
			 Map<String,Object> map = (Map<String, Object>) der.deserialize(json);*/
			 Map<String,Object> map = JsonToMap.toMap(json);
			 return parse(map);
		}
		private static IRule parse(Map<String,Object> map){
			if(map.containsKey("logicRelation")){

				Rules rules = new Rules();
				Object o = map.get("logicRelation");
				if(o != null){
					rules.setLogicRelation(LogicRelation.valueOf((String)o));
				}
				List<HashMap> list = (List<HashMap>) map.get("rules");
				if(list != null){
					for(Map m : list){
						rules.addRule(parse(m));
					}
				}
				return rules;
			}else{
				Rule rule = new Rule();
				rule.setField((String)map.get("field"));
				rule.setOp(OP.valueOf((String)map.get("op")));
				rule.setValue((String)map.get("value"));
				return rule;
			}
			
		}
		
}
