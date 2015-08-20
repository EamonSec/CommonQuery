package com.xin.util.db;

import java.util.ArrayList;
import java.util.List;

public class Rules implements IRule {

	LogicRelation logicRelation;
	List<IRule> rules = new ArrayList<IRule>();
	
	public void addRule(IRule rule){
		this.rules.add(rule);
	}
	
	@Override
	public Tuple<String, List<Object>> toExp() {
		String exp = "";
		List<Object> paras = new ArrayList<Object>();
		if(rules != null && rules.size()>0){
			StringBuilder sb = new StringBuilder();
			sb.append("(");
			for(IRule rule : rules){
				Tuple<String, List<Object>> tuple = rule.toExp();
				
				sb.append(tuple.a);
				sb.append(" ");
				sb.append(logicRelation.toString());
				sb.append(" ");
				
				paras.addAll(tuple.b);
				
			}
			sb.append(")");
			exp = sb.toString().replaceAll("[\\s\\t]*"+logicRelation.toString()+"[\\s\\t]*\\)$", ")");
		}
		return new Tuple<String,List<Object>>(exp,paras);
	}

	public LogicRelation getLogicRelation() {
		return logicRelation;
	}

	public void setLogicRelation(LogicRelation logicRelation) {
		this.logicRelation = logicRelation;
	}

	public List<IRule> getRules() {
		return rules;
	}

	public void setRules(List<IRule> rules) {
		this.rules = rules;
	}
	
	
}
