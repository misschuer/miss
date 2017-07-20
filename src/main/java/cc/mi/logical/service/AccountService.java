package cc.mi.logical.service;

import cc.mi.logical.mappin.Account;

public class AccountService {
	public static Account createAccount(String account, String password) {
		Account acct = new Account();
		acct.setAccount(account);
		acct.setPassword(password);
		return acct;
	}
}
