package com.gooseeker.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Dao {
	
	private SqlSessionTemplate metacorpora;
	
	public SqlSessionTemplate getMetacorpora() {
		return metacorpora;
	}
	
	@Autowired
	public void setMetacorpora(SqlSessionTemplate metacorpora) {
		this.metacorpora = metacorpora;
	}

}
