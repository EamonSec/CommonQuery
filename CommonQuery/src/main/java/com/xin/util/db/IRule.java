package com.xin.util.db;

import java.util.List;

public interface IRule {
	/**
	 * 将Irule转换成SQL where子句(不含where)及对应的参数,
	 * 注意:要查询的表必须加上别名t 
	 * @param queryString
	 * @return 二元组,分别为where子句及对应的参数,及对应的参数
	 */
	public Tuple<String,List<Object>> toExp();
}
