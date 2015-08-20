package com.xin.util.db;

import java.util.ArrayList;
import java.util.List;


public class Rule implements IRule {

	
	
	String field;
	OP op;
	String value;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	 
	public OP getOp() {
		return op;
	}
	public void setOp(OP op) {
		this.op = op;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

	public Tuple<String, List<Object>> toExp() {
		
		String exp = "t."+this.field;
		List<Object> para = new ArrayList<Object>();
		switch(this.op){
		case contains:	  exp = exp + " like ?"; para.add("%"+value+"%"); break;
		case notcontains: exp = exp + " not like ?";para.add("%"+value+"%"); break;
		case beginwith :  exp = exp + " like ?"; para.add(value+"%"); break;
		case endwith: 	  exp = exp + " like ?"; para.add("%"+value); break;
		case less:		  exp = exp + " < ?"; 	para.add(value); break;
		case lessorequal: exp = exp + " <= ?"; para.add(value); break;
		case greater:	  exp = exp + " > ?";  para.add(value); break;
		case greaterorequal : exp = exp + " >= ?"; para.add(value); break;
		case  equal:  	  exp = exp + " = ?"; para.add(value); break;
		case  notequal:   exp = exp + " <> ?"; para.add(value); break;
		}
		return new Tuple<String, List<Object>>(exp,para);
	}

}
