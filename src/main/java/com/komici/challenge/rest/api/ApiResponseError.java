package com.komici.challenge.rest.api;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ApiResponseError implements Serializable {


    @ApiModelProperty(value = "Description message when error occurred during the API calls", example = "Error description", required = true)
    private String errorMsg;

    @ApiModelProperty(value = "List of errors for the used model in the crud operations", example = "Error description", required = true)
    private List<String> validationErrors;


    public ApiResponseError(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public ApiResponseError(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public ApiResponseError() {
    }


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }

}
