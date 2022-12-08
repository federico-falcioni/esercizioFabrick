package testFalcioni;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import testFalcioni.controller.AccountController;
import testFalcioni.repository.MoneyTransactionRepository;
import testFalcioni.service.AccountService;

@WebMvcTest
@ContextConfiguration(classes = {AccountController.class, AccountService.class})
class TestFalcioniApplicationTests {
	
	private static int accountId = 14537780;
	private static Calendar fromTransactionDate = new GregorianCalendar(2022,11,01);
	private static Date fromAccountingDate = fromTransactionDate.getTime();
	private static Date toAccountingDate =new Date();
	private static Calendar _executionDate = new GregorianCalendar(2022,11,9);
	private static Date executionDate = _executionDate.getTime();
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
    private WebApplicationContext ctx;
	private MockMvc mockMvc;

    @MockBean
    private MoneyTransactionRepository employeeRepository;
	
    @BeforeEach
    public void init() throws IOException {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }
    
    @Test
    public void getBalance() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/"+accountId+"/balance"))
				.andExpect(status().isOk()).andDo(print());
    }
    
    @Test
    public void getTransactions() throws Exception {
    	String fromDate = simpleDateFormat.format(fromAccountingDate);
    	String toDate = simpleDateFormat.format(toAccountingDate);
		mockMvc.perform(MockMvcRequestBuilders.get("/accounts/"+accountId+"/transactions?fromAccountingDate="
				+fromDate.toString()+"&toAccountingDate="+toDate.toString()))
				.andExpect(status().isOk()).andDo(print());
    }
    
    @Test
    public void moneyTransfers()throws Exception {
    	String date = simpleDateFormat.format(executionDate);
		String body = 
				"{"
						+ "\"creditor\": {"
							+ "\"name\": \"Giannoni Robertino\","
								+ "\"account\": {"
								+ "\"accountCode\": \"IT60X0542811101000000123456\","
								+ "\"bicCode\": \"SELBIT2BXXX\""
								+ "}"
							+ "},"
						+ "\"executionDate\": \""+date+"\","
						+ "\"description\": \"Payment invoice 75/2017\","
						+ "\"amount\": 1,"
						+ "\"currency\": \"EUR\","
						+ "\"isUrgent\": false,"
						+ "\"isInstant\": false,"
						+ "\"feeType\": \"SHA\","
						+ "\"taxRelief\": {"
							+ "\"taxReliefId\": \"L449\","
							+ "\"isCondoUpgrade\": false,"
							+ "\"creditorFiscalCode\": \"CRSCCM01S58F704S\","
							+ "\"beneficiaryType\": \"NATURAL_PERSON\","
							+ "\"naturalPersonBeneficiary\": {"
								+ "\"fiscalCode1\": \"GNNRRT00P58B322F\""
							+ "}"
						+ "}"
					+ "}";
		
		mockMvc.perform(MockMvcRequestBuilders.post("/accounts/"+accountId+"/payments/money-transfers")
				.content(body)
				.header("Content-Type", "application/json"))
				.andExpect(status().isOk()).andDo(print());
    }

}
