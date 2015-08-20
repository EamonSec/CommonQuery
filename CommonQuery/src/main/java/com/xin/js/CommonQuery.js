function getUserList(pageNumber,pageSize){
	
	var queryArray = [];
	var uid = $('#uid').val();
	var name = $('#name').val();
	
	if(uid != '' && uid.replace(/[\s\t]+/g,'') != ''){
		var queryObject = {};
		queryObject.field = "uid";
		queryObject.op = 'less';
		queryObject.value= uid;
		queryArray.push(queryObject);
	}
	if(groupid != '' && groupid.replace(/[\s\t]+/g,'') != ''){
		var queryObject = {};
		queryObject.field = "name";
		queryObject.op = 'contains';
		queryObject.value  = name;
		queryArray.push(queryObject);
	}
	 
	
	var query = {};
	query.logicRelation = 'and';
	query.rules = queryArray;
	
	$(dg_user_list).datagrid('load',{
		queryString : JSON.stringify(query)
	});
	
}