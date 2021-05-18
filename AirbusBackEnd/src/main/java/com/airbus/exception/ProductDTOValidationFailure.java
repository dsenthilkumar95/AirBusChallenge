package com.airbus.exception;

import java.util.List;
import java.util.Map;

public class ProductDTOValidationFailure extends Exception{
    private Map<String, List<String>> validationErrors;

    public ProductDTOValidationFailure(Map<String, List<String>> validationErrors){
        super("Product is not valid");
        this.validationErrors = validationErrors;
    }

    public Map<String, List<String>> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(Map<String, List<String>> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
