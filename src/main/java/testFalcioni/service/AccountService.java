package testFalcioni.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import testFalcioni.model.MoneyTransaction;
import testFalcioni.repository.MoneyTransactionRepository;

@Service
public class AccountService {
	
	Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired
	MoneyTransactionRepository moneyTransactionRepository;
	
	String BaseUrl = "https://sandbox.platfr.io";
	String AuthSchema = "S2S";
	String ApiKey = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
	
	HttpClient client = HttpClient.newHttpClient();
	HttpResponse<String> response=null;
	
	//Get request
	private HttpRequest createHttpGetRequest(String _uri){
		var uri = URI.create(BaseUrl
				+ _uri);
		
		var request = HttpRequest.newBuilder()
				.GET()
				.header("Accept", "application/json")
				.uri(uri)
				.header("Auth-Schema", AuthSchema)
				.header("apikey", ApiKey)
				.build();
		
		return request;
	}
	
	
	//Post request
	private HttpRequest createHttpPostRequest(String _uri, String body){
		var uri = URI.create(BaseUrl
				+ _uri);
		
		var request = HttpRequest.newBuilder()
				.POST(BodyPublishers.ofString(body))
				.uri(uri)
				.header("Accept", "application/json")
				.header("Auth-Schema", AuthSchema)
				.header("apikey", ApiKey)
				.header("Content-Type", "application/json")
				.build();
		
		return request;
	}
	
	public String getBalance(String accountId) {
		logger.info("Richiesto saldo per il conto "+accountId);
		HttpRequest request = createHttpGetRequest("/api/gbs/banking/v4.0/accounts/"+accountId+"/balance");
		try {
			response = client.send(
					request,
					HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
					);
			logger.info("Lettura saldo completata");
		} catch (IOException | InterruptedException e) {
			logger.error("Lettura saldo non completata: "+e);
		}
		return response.body();
	}


	public String getTransactions(String accountId, String fromAccountingDate, String toAccountingDate) {
		logger.info("Richiesta lista transazioni per l'account "+accountId+" nelle date comprese tra: "
				+fromAccountingDate + " e "+ toAccountingDate);
		
		HttpRequest request = createHttpGetRequest("/api/gbs/banking/v4.0/accounts/"
				+ accountId + "/transactions?fromAccountingDate="
				+ fromAccountingDate+ "&toAccountingDate=" + toAccountingDate);
		try {
			response = client.send(
					request,
					HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8)
					);
			logger.info("Request inviata correttamente");
		} catch (IOException | InterruptedException e) {
			logger.error("Errore durante l'invio della request: "+e);
		}
		
		try {
		String jsonString = response.body();
		JSONObject obj = new JSONObject(jsonString);
		JSONObject payload = obj.getJSONObject("payload");
		JSONArray transactionsJSON = payload.getJSONArray("list");
		ArrayList<MoneyTransaction> transactionsArray = new ArrayList<MoneyTransaction>();
		for(int i=0; i<transactionsJSON.length(); i++) {
			MoneyTransaction transaction = new MoneyTransaction(transactionsJSON.getJSONObject(i));
			transactionsArray.add(transaction);
		}
		moneyTransactionRepository.saveAll(transactionsArray);
		logger.info("Transazioni salvate correttamente sul db");
		}catch(Exception e) {
			logger.error("Errore nel salvataggio delle transazioni sul DB: "+e);
		}
		return response.body();
		
	}


	public String moneyTransfers(String accountId, String body) {
		logger.info("Richiesto bonifico da parte del conto "+accountId);
		HttpRequest request = createHttpPostRequest("/api/gbs/banking/v4.0/accounts/"+accountId+"/payments/money-transfers",
				body);
		try {
			response = client.send(
					request,
					HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
			logger.info("Esito bonifico positivo");
		} catch (IOException | InterruptedException e) {
			logger.error("Errore durante l'operazione di bonifico: "+e);
		}
		
		return response.body();
		
	}
	
	

}
