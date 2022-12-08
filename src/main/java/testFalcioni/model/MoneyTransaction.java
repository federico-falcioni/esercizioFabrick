package testFalcioni.model;

import org.json.JSONObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name = "Transaction")
@Table(name = "transactions")
public class MoneyTransaction {

	@Id
	@Column(name = "transaction_id")
	String transactionId;
	@Column(name = "operation_id")
	String operationId;
	@Column(name = "accounting_date")
	String accountingDate;
	@Column(name = "value_date")
	String valueDate;
	@Column(name = "enumeration")
	String enumeration;
	@Column(name = "value")
	String value;
	@Column(name = "amount")
	double amount;
	@Column(name = "currency")
	String currency;
	@Column(name = "description")
	String description;
	
	public MoneyTransaction() {};
	
	public MoneyTransaction(JSONObject jsonObject) {
		this.transactionId=jsonObject.getString("transactionId");
		this.operationId=jsonObject.getString("transactionId");
		this.accountingDate=jsonObject.getString("accountingDate");
		this.valueDate=jsonObject.getString("valueDate");
		this.enumeration=jsonObject.getJSONObject("type").getString("enumeration");
		this.value=jsonObject.getJSONObject("type").getString("value");
		this.amount=jsonObject.getInt("amount");
		this.currency=jsonObject.getString("currency");
		this.description=jsonObject.getString("description");
	};
	
	public MoneyTransaction(String transactionId, String operationId, String accountingDate, String valueDate,
			String enumeration, String value, double amount, String currency, String description) {
		super();
		this.transactionId = transactionId;
		this.operationId = operationId;
		this.accountingDate = accountingDate;
		this.valueDate = valueDate;
		this.enumeration = enumeration;
		this.value = value;
		this.amount = amount;
		this.currency = currency;
		this.description = description;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getOperationId() {
		return operationId;
	}
	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}
	public String getAccountingDate() {
		return accountingDate;
	}
	public void setAccountingDate(String accountingDate) {
		this.accountingDate = accountingDate;
	}
	public String getValueDate() {
		return valueDate;
	}
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}
	public String getEnumeration() {
		return enumeration;
	}
	public void setEnumeration(String enumeration) {
		this.enumeration = enumeration;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MoneyTransaction [transactionId=" + transactionId + ", operationId=" + operationId + ", accountingDate="
				+ accountingDate + ", valueDate=" + valueDate + ", enumeration=" + enumeration + ", value=" + value
				+ ", amount=" + amount + ", currency=" + currency + ", description=" + description + "]";
	}
	
	
}
