package com.categorisationapi;

import com.categorisationapi.response.AddNewCategoryResponse;
import com.categorisationapi.response.CategorisedTransactionsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/askFractalCalls")
@Api(value = "askFractalCalls")
public class CategorisationAPIController {
    @Autowired
    CategorisationAPIClientService categorisationAPIClientService;

    @ApiOperation(value = "Create a new category", response = AddNewCategoryResponse.class)
    @RequestMapping(value = "/addCategory/{category}", method = RequestMethod.POST, produces = "application/json")
    public AddNewCategoryResponse addNewCategory(@PathVariable String category) {
        ResponseEntity<AddNewCategoryResponse> responseEntity = categorisationAPIClientService.addNewCategory(category);
        return responseEntity.getBody();
    }

    @ApiOperation(value = "Get categorised transactions for a given category", response = CategorisedTransactionsResponse.class)
    @RequestMapping(value = "/getCategorisedTransactions/{categoryId}/{companyId}/{pg}/{from}/{to}", method = RequestMethod.GET, produces = "application/json")
    public CategorisedTransactionsResponse getCategorisedTransactions(@PathVariable String category, @PathVariable String companyId, @PathVariable String pg, @PathVariable String from, @PathVariable String to) {
        ResponseEntity<CategorisedTransactionsResponse> responseEntity = categorisationAPIClientService.getCategorisedTransactions(category, companyId, pg, from, to);
        return responseEntity.getBody();
    }

    @ApiOperation(value = "Update categories of transactions", response = String.class)
    @RequestMapping(value = "/updateCategory/{companyId}/{categoryId}/{transactionId}", method = RequestMethod.PUT, produces = "application/json")
    public String updateCategory(@PathVariable String companyId, @PathVariable String categoryId, @PathVariable String transactionId) throws IOException {
        ResponseEntity<String> responseEntity = categorisationAPIClientService.updateCategory(companyId, categoryId, transactionId);
        return responseEntity.getBody();
    }
}
