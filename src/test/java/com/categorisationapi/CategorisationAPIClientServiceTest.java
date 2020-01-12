package com.categorisationapi;


import com.categorisationapi.response.AddNewCategoryResponse;
import com.categorisationapi.response.CategorisedTransaction;
import com.categorisationapi.response.CategorisedTransactionsResponse;
import com.categorisationapi.response.TransactionTypeEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

@RunWith(SpringRunner.class)
@RestClientTest({RestTemplate.class, CategorisationAPIClientService.class})
public class CategorisationAPIClientServiceTest {

    public static final String ADD_CATEGORY = "/category";
    public static final String GET_CATEGORIZED_TRANSACTIONS = "/category/transactions?";
    public static final String UPDATE_CATEGORIES = "/categories/transactions";
    private static final String SANDBOX_ASK_FRACTAL_URL = "https://sandbox.askfractal.com";
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private CategorisationAPIClientService client;
    @Autowired
    private MockRestServiceServer server;
    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        server = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void isTrue_whenCallingAddNewCategoryCoffee_thenAssertCreated()
            throws Exception {

        AddNewCategoryResponse addNewCategoryResponse = new AddNewCategoryResponse();
        addNewCategoryResponse.setCategory("Coffee");
        addNewCategoryResponse.setCategoryId("C1");

        String resultStr =
                objectMapper.writeValueAsString(addNewCategoryResponse);

        URL url = new URL(SANDBOX_ASK_FRACTAL_URL + ADD_CATEGORY);
        this.server.expect(requestTo(SANDBOX_ASK_FRACTAL_URL + ADD_CATEGORY))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withCreatedEntity(url.toURI())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resultStr));
        ResponseEntity<AddNewCategoryResponse> responseEntity = this.client.addNewCategory("Coffee");
        this.server.verify();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getCategory()).isEqualTo(addNewCategoryResponse.getCategory());
        assertThat(responseEntity.getBody().getCategoryId()).isEqualTo(addNewCategoryResponse.getCategoryId());
    }

    @Test
    public void isTrue_whenCallingAddNewCategoryCake_thenAssertCreated()
            throws Exception {

        AddNewCategoryResponse addNewCategoryResponse = new AddNewCategoryResponse();
        addNewCategoryResponse.setCategory("Cake");
        addNewCategoryResponse.setCategoryId("C2");
        String resultStr =
                objectMapper.writeValueAsString(addNewCategoryResponse);

        URL url = new URL(SANDBOX_ASK_FRACTAL_URL + ADD_CATEGORY);
        this.server.expect(requestTo(SANDBOX_ASK_FRACTAL_URL + ADD_CATEGORY))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withCreatedEntity(url.toURI())
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resultStr));
        ResponseEntity<AddNewCategoryResponse> responseEntity = this.client.addNewCategory("Cake");
        this.server.verify();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody().getCategory()).isEqualTo(addNewCategoryResponse.getCategory());
        assertThat(responseEntity.getBody().getCategoryId()).isEqualTo(addNewCategoryResponse.getCategoryId());
    }

    @Test
    public void isFalse_whenCallingAddNewCategoryNull_thenIsBadRequest()
            throws Exception {

        this.server.expect(requestTo(SANDBOX_ASK_FRACTAL_URL + ADD_CATEGORY))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withBadRequest()
                        .contentType(MediaType.APPLICATION_JSON));
        assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() -> {
            this.client.addNewCategory("");
        })
                .withMessageMatching("400 BAD_REQUEST \"Invalid Request, category cannot be null\"");

    }

    @Test
    public void isTrue_whenCallingUpdateCategory_thenAssert_No_Content()
            throws Exception {

        AddNewCategoryResponse mockObj = new AddNewCategoryResponse();
        mockObj.setCategory("NewCoffee");
        mockObj.setCategoryId("NC1");

        String resultStr =
                objectMapper.writeValueAsString(mockObj);
        this.server.expect(requestTo(SANDBOX_ASK_FRACTAL_URL + UPDATE_CATEGORIES))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withNoContent().body(resultStr));

        ResponseEntity<String> result = this.client.updateCategory("Coffee company", "NC1", "T1");
        this.server.verify();
        assertThat(result.getBody()).isNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    public void isFalse_whenCallingUpdateCategory_thenIsBadRequest()
            throws Exception {
        this.server.expect(requestTo(SANDBOX_ASK_FRACTAL_URL + UPDATE_CATEGORIES))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withNoContent());

        assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(() -> {
            this.client.updateCategory("", "NC1", "T1");
        })
                .withMessageMatching("400 BAD_REQUEST \"Invalid Request, companyId cannot be null\"");

    }

    @Test
    public void givenCorrectData_whenCallingGetCategorisedTransactions_thenClientMakesCorrectCall()
            throws Exception {

        List<CategorisedTransaction> categorisedTransactions = new ArrayList<>();

        CategorisedTransaction cakeTransactionsResponse = new CategorisedTransaction();
        cakeTransactionsResponse.setCategory("Cake");
        cakeTransactionsResponse.setCompanyId(new Integer(100));
        cakeTransactionsResponse.setAccountId("CakeAccount");
        cakeTransactionsResponse.setTransactionType(TransactionTypeEnum.DEBIT);
        categorisedTransactions.add(cakeTransactionsResponse);

        CategorisedTransactionsResponse categorisedTransactionsResponse = new CategorisedTransactionsResponse();
        categorisedTransactionsResponse.setCategorisedTransactionList(categorisedTransactions);

        String resultStr =
                objectMapper.writeValueAsString(categorisedTransactionsResponse);
        String URL = SANDBOX_ASK_FRACTAL_URL + GET_CATEGORIZED_TRANSACTIONS;

        this.server.expect(requestToUriTemplate(URL, "100", "Cake"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(resultStr));

        ResponseEntity<CategorisedTransactionsResponse> result = this.client.getCategorisedTransactions("Cake", "100", "", "", "");
        this.server.verify();

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody().getCategorisedTransactionList().get(0).getCompanyId()).isEqualTo(100);
        assertThat(result.getBody().getCategorisedTransactionList().get(0).getAccountId()).isEqualTo("CakeAccount");
        assertThat(result.getBody().getCategorisedTransactionList().get(0).getCategory()).isEqualTo("Cake");
        assertThat(result.getBody().getCategorisedTransactionList().get(0).getTransactionType().getValue()).isEqualTo("DEBIT");
    }

    @Test
    public void isFalse_whenCallingGetCategorisedTransactionsCategoryNull_thenIsBadRequest()
            throws Exception {
        String completeURL = SANDBOX_ASK_FRACTAL_URL + GET_CATEGORIZED_TRANSACTIONS;
        this.server.expect(requestToUriTemplate(completeURL, "", "cake"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON));

        assertThatExceptionOfType(ResponseStatusException.class).isThrownBy(()
                -> {
            this.client.getCategorisedTransactions("", "cake", "", "", "");
        })
                .withMessageMatching("400 BAD_REQUEST \"Invalid Request, category cannot be null\"");
    }
}
