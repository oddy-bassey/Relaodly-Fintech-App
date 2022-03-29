package com.reloadly.bankacc.cmd.api.dto;

import com.reloadly.bank.core.dto.BaseResponse;

public class OpenAccountResponse extends BaseResponse {
    private String id;

    public OpenAccountResponse(String message) {
        super(message);
    }

    public OpenAccountResponse(String id, String message) {
        super(message);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
