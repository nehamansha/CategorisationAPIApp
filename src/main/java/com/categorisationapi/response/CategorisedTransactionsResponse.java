package com.categorisationapi.response;

import java.util.List;

public class CategorisedTransactionsResponse {

    private List<CategorisedTransaction> categorisedTransactionList;

    public List<CategorisedTransaction> getCategorisedTransactionList() {
        return categorisedTransactionList;
    }

    public void setCategorisedTransactionList(List<CategorisedTransaction> categorisedTransactionList) {
        this.categorisedTransactionList = categorisedTransactionList;
    }
}

