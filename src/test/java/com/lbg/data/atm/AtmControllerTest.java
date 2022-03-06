package com.lbg.data.atm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lbg.data.atm.controller.AtmController;
import com.lbg.data.atm.service.AtmService;
import model.ATM;
import model.ATMResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = AtmController.class)
public class AtmControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private AtmService atmService;

	@Test
	void test_getAtms() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ATMResponse allAtms = objectMapper.readValue(new File("src/test/resources/data/all_atms.json"), ATMResponse.class);
		Mockito.when(atmService.retrieveAllAtms()).thenReturn(allAtms);
		 webTestClient.get()
				.uri("/atms")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(ATMResponse.class).isEqualTo(allAtms);
	}

	@Test
	void test_getAtmDetailByIdentificationValue() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ATMResponse allAtms = objectMapper.readValue(new File("src/test/resources/data/all_atms.json"), ATMResponse.class);
		ATM atm = objectMapper.readValue(new File("src/test/resources/data/atm.json"), ATM.class);
		Mockito.when(atmService.retrieveAllAtms()).thenReturn(allAtms);
		webTestClient.get()
				.uri("/atms/LFFFBC11")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(ATM.class).isEqualTo(atm);
	}

	@Test
	void test_getAtmDetailByIdentificationValue_404() throws IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		ATMResponse atmResponse = objectMapper.readValue(new File("src/test/resources/data/all_atms.json"), ATMResponse.class);
		Mockito.when(atmService.retrieveAllAtms()).thenReturn(atmResponse);
		webTestClient.get()
				.uri("/atms/LFFFBC12")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isNotFound();
	}
}
