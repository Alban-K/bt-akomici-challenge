package com.komici.challenge.rest.api;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

public class ApiResponseError implements Serializable {


    @ApiModelProperty(value = "Description message when error occurred during the API calls", example = "Error description", required = true)
    private String errorMsg;

    @ApiModelProperty(value = "Warning messages", example = "Warning messages")
    List<String> warningList;
}
