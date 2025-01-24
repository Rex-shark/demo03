package com.example.demoservice.constant;

import lombok.Getter;

@Getter
public enum ApiMessageEnum {

    SUCCESS(true,"1", "成功"),
    FAIL(false,"2", "失敗"),
    ERROR(false,"3", "伺服器發生錯誤"),

    AUTH_SUCCESS(true,"100", "授權成功"),
    AUTH_FAIL(false,"200", "授權失敗"),//Forbidden 403

    QUY_SUCCESS(true,"101", "查詢成功"),
    ADD_SUCCESS(true,"102", "新增成功"),
    UPD_SUCCESS(true,"103", "修改成功"),
    DEL_SUCCESS(true,"104", "刪除成功"),

    QUY_FAIL(false,"201", "查詢失敗"),
    ADD_FAIL(false,"202", "新增失敗"),
    UPD_FAIL(false,"203", "修改失敗"),
    DEL_FAIL(false,"204", "刪除失敗"),

    TEST(true,"999", "TEST");

    private final Boolean isOk;
    private final String code; // 狀態碼
    private final String message; // 訊息

    ApiMessageEnum(Boolean isOk, String code, String message) {
        this.isOk = isOk;
        this.code = code;
        this.message = message;
    }

}
