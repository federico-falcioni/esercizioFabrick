package testFalcioni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import testFalcioni.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
	AccountService accountService;
	
	@GetMapping("/{accountId}/balance")
	public String getBalance(@PathVariable(name="accountId")String accountId) {
		return accountService.getBalance(accountId);
	}
	
	@GetMapping("/{accountId}/transactions")
	public String getTransactions(
			@PathVariable(name="accountId")String accountId,
			@RequestParam("fromAccountingDate") String fromAccountingDate,
			@RequestParam("toAccountingDate") String toAccountingDate) {
		return accountService.getTransactions(accountId, fromAccountingDate, toAccountingDate);
	}
	
	@PostMapping("/{accountId}/payments/money-transfers")
	public String moneyTransfers(@PathVariable(name="accountId")String accountId, @RequestBody() String body) {
		return accountService.moneyTransfers(accountId, body);
	}
	
}
