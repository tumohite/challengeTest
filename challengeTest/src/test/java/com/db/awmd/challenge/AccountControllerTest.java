package com.db.awmd.challenge;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(JMockit.class)
public class AccountControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	
	@Before
	public void setup() {
	}

	@Test
	public void testGetAccount() throws Exception {
//		mockMvc.perform(get("/v1/accounts")).andExpect(status().isOk())
//				.andExpect(content().contentType("application/json;charset=UTF-8"))
//				.andExpect(jsonPath("$.accountId").value("TST1234")).andExpect(jsonPath("$.balance").value(BigDecimal.valueOf(1000)))

	}

}
}
