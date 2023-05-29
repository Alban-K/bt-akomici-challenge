package com.komici.challenge.rest.api;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class ApiResponseError implements Serializable {


    @ApiModelProperty(value = "Description message when error occurred during the API calls", example = "Error description", required = true)
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public ApiResponseError(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public ApiResponseError() {
    }

}
