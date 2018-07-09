package com.kaidin.appframe.service;

import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.transaction.UserTransaction;

@TransactionManagement(TransactionManagementType.BEAN)
public abstract class AbstractBMTService {

	@Resource
    private UserTransaction userTransaction;

	public AbstractBMTService() {
		ServiceFactory.createBMTTransactionManager(userTransaction);
	}
}
