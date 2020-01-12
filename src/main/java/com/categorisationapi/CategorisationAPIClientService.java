package com.categorisationapi;

import com.categorisationapi.request.UpdateCategorisedTransactionRequest;
import com.categorisationapi.response.AddNewCategoryResponse;
import com.categorisationapi.response.CategorisedTransactionsResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Service
public class CategorisationAPIClientService {
    private static final String SANDBOX_ASK_FRACTAL_URL = "https://sandbox.askfractal.com";
    public static final String ADD_NEW_CATEGORY = "/category";
    public static final String GET_CATEGORIZED_TRANSACTIONS = "/category/transactions?";
    public static final String UPDATE_CATEGORIES = "/categories/transactions";


    @Autowired
    private RestTemplate restTemplate;

    /**
     * Add a new category
     *
     * @param category
     * @return HttpStatus created
     * @throws ResponseStatusException
     */
    public ResponseEntity<AddNewCategoryResponse> addNewCategory(String category) throws ResponseStatusException {

        if (StringUtils.isBlank(category)) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid Request, category cannot be null");
        }
        return restTemplate.postForEntity(SANDBOX_ASK_FRACTAL_URL + ADD_NEW_CATEGORY, category, AddNewCategoryResponse.class, CREATED);
    }

    /**
     * Get categorised transactions for a given category
     *
     * @param category
     * @param companyId
     * @param pg
     * @param from
     * @param to
     * @return transactions
     * @throws ResponseStatusException
     */
    public ResponseEntity<CategorisedTransactionsResponse> getCategorisedTransactions(String category, String companyId, String pg, String from, String to)
            throws ResponseStatusException {

        if (StringUtils.isBlank(companyId)) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid Request, companyId cannot be null");
        }
        if (StringUtils.isBlank(category)) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid Request, category cannot be null");
        }

        final Map<String, String> pathVariable = new HashMap<>();
        pathVariable.put("category", category);
        pathVariable.put("companyId", companyId);
        pathVariable.put("pg", pg);
        pathVariable.put("from", from);
        pathVariable.put("to", to);
        return restTemplate.getForEntity(SANDBOX_ASK_FRACTAL_URL + GET_CATEGORIZED_TRANSACTIONS, CategorisedTransactionsResponse.class,pathVariable);
    }

    /**
     * Update a category for a given transaction
     *
     * @param companyId
     * @param categoryId
     * @param transactionId
     * @return HttpStatus No response
     * @throws ResponseStatusException
     * @throws IOException
     */
    public ResponseEntity<String> updateCategory(String companyId, String categoryId, String transactionId)
            throws ResponseStatusException, IOException {
        if (StringUtils.isBlank(companyId)) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid Request, companyId cannot be null");
        }
        if (StringUtils.isBlank(categoryId)) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid Request, categoryId cannot be null");
        }
        if (StringUtils.isBlank(transactionId)) {
            throw new ResponseStatusException(BAD_REQUEST, "Invalid Request, transactionId cannot be null");
        }

        UpdateCategorisedTransactionRequest categoryObject = new UpdateCategorisedTransactionRequest();
        categoryObject.setCompanyId(companyId);
        categoryObject.setCategoryId(categoryId);
        categoryObject.setTransactionId(transactionId);

        HttpEntity<UpdateCategorisedTransactionRequest> entity = new HttpEntity<>(categoryObject);
        ResponseEntity<String> response = restTemplate.exchange(SANDBOX_ASK_FRACTAL_URL + UPDATE_CATEGORIES, HttpMethod.PUT, entity, String.class, NO_CONTENT);
        return response;
    }


}
