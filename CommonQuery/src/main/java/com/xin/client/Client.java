package com.xin.client;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.xin.pojo.User;
import com.xin.util.db.CommonQueryDao;
import com.xin.util.db.LogicRelation;
import com.xin.util.db.OP;
import com.xin.util.db.Rule;
import com.xin.util.db.Rules;

public class Client {
	public static void main(String[] args) {
		
		 Rule rule1 = new Rule();
		 rule1.setField("uid");
		 rule1.setOp(OP.less);
		 rule1.setValue("10");
		 
		 Rule rule2 = new Rule();
		 rule2.setField("uid");
		 rule2.setOp(OP.greater);
		 rule2.setValue("1");
		 
		 
		 Rules rules1 = new Rules();
		 rules1.setLogicRelation(LogicRelation.and);
		 rules1.addRule(rule1);
		 rules1.addRule(rule2);

		 
		 Rule rule3 = new Rule();
		 rule3.setField("name");
		 rule3.setOp(OP.contains);
		 rule3.setValue("user07");
		 
		 
		 Rule rule4 = new Rule();
		 rule4.setField("name");
		 rule4.setOp(OP.contains);
		 rule4.setValue("user08");
		 
		 Rules rules2 = new Rules();
		 rules2.setLogicRelation(LogicRelation.or);
		 rules2.addRule(rule3);
		 rules2.addRule(rule4);
		 
		 
		 
		 Rules rules3 = new Rules();
		 rules3.setLogicRelation(LogicRelation.and);
		 rules3.addRule(rules1);
		 rules3.addRule(rules2);
		 
		 Rule rule5 = new Rule();
		 rule5.setField("uid");
		 rule5.setOp(OP.equal);
		 rule5.setValue("11");
		 
		 Rules rules4 = new Rules();
		 rules4.setLogicRelation(LogicRelation.or);
		 rules4.addRule(rules3);
		 rules4.addRule(rule5);
		  System.out.println(rules4.toExp().a );
		 
		 ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"});
		 CommonQueryDao dao = (CommonQueryDao)ctx.getBean("commonQueryDao");
		 Gson gson = new Gson();
		 List<User> list = dao.query(gson.toJson(rules4));
		 for(User user : list){
			 System.out.println(user.getUid()+","+user.getName()+","+user.getPsw());
		 }
	}
}
