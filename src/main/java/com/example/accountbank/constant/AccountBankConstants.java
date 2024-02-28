package com.example.accountbank.constant;

public class AccountBankConstants {
    /*
        URLs
        - Member
        - AccountBank
        - Settings
         - Budget
         - BudgetHistory
     */
    private static final String MEMBER_BASE_URL = "/member";
    public static final String MEMBER_LOGIN_URL = MEMBER_BASE_URL + "/login";
    public static final String MEMBER_FORGET_PASSWORD_URL = MEMBER_BASE_URL + "/forget_password";

    private static final String ACCOUNT_BANK_BASE_URL = "/account_bank";
    public static final String ACCOUNT_BANK_REGISTER_URL = ACCOUNT_BANK_BASE_URL + "/register";
    public static final String ACCOUNT_BANK_CALENDAR_URL = ACCOUNT_BANK_BASE_URL + "/calendar";
    public static final String ACCOUNT_BANK_DETAIL_URL = ACCOUNT_BANK_BASE_URL + "/detail";
    public static final String ACCOUNT_BANK_DELETE_URL = ACCOUNT_BANK_BASE_URL + "/delete";

    public static final String SETTINGS_URL = ACCOUNT_BANK_BASE_URL + "/settings";
    public static final String SETTINGS_BUDGET_URL = SETTINGS_URL + "/budget";
    public static final String SETTINGS_BUDGET_REGISTER_URL = SETTINGS_BUDGET_URL + "/register";
    public static final String SETTINGS_BUDGET_DETAIL_URL = SETTINGS_BUDGET_URL + "/detail";
    public static final String SETTINGS_BUDGET_DELETE_URL = SETTINGS_BUDGET_URL + "/delete";
    public static final String SETTINGS_BUDGET_HISTORY_URL = SETTINGS_URL + "/budget_history";
    public static final String SETTINGS_BUDGET_HISTORY_DETAIL_URL = SETTINGS_BUDGET_HISTORY_URL + "/detail";

    /*
        Contents Template
        - Member
        - AccountBook
        - Settings
         - Budget
         - BudgetHistory
     */
    private static final String CONTENTS_BASE_PATH = "contents";
    private static final String CONTENTS_MEMBER_BASE_PATH = CONTENTS_BASE_PATH + "/member";
    public static final String CONTENTS_MEMBER_LOGIN_PATH = CONTENTS_MEMBER_BASE_PATH + "/login";
    public static final String CONTENTS_MEMBER_FORGET_PASSWORD_PATH = CONTENTS_MEMBER_BASE_PATH + "/forget_password";

    private static final String CONTENTS_ACCOUNT_BOOK_BASE_PATH = CONTENTS_BASE_PATH + "/account_book";
    public static final String CONTENTS_ACCOUNT_BOOK_REGISTER_PATH = CONTENTS_ACCOUNT_BOOK_BASE_PATH + "/register";
    public static final String CONTENTS_ACCOUNT_BOOK_CALENDAR_PATH = CONTENTS_ACCOUNT_BOOK_BASE_PATH + "/calendar";
    public static final String CONTENTS_ACCOUNT_BOOK_DETAIL_PATH = CONTENTS_ACCOUNT_BOOK_BASE_PATH + "/detail";
    
    private static final String CONTENTS_SETTINGS_BASE_PATH = CONTENTS_BASE_PATH + "/settings";
    public static final String CONTENTS_SETTINGS_LIST_PATH = CONTENTS_SETTINGS_BASE_PATH + "/list";

    private static final String CONTENTS_BUDGET_BASE_PATH = CONTENTS_SETTINGS_BASE_PATH + "/budget";
    public static final String CONTENTS_BUDGET_LIST_PATH = CONTENTS_BUDGET_BASE_PATH + "/list";
    public static final String CONTENTS_BUDGET_REGISTER_PATH = CONTENTS_BUDGET_BASE_PATH + "/register";
    public static final String CONTENTS_BUDGET_DETAIL_PATH = CONTENTS_BUDGET_BASE_PATH + "/detail";

    private static final String CONTENTS_BUDGET_HISTORY_BASE_PATH = CONTENTS_SETTINGS_BASE_PATH + "/budget_history";
    public static final String CONTENTS_BUDGET_HISTORY_LIST_PATH = CONTENTS_BUDGET_HISTORY_BASE_PATH + "/list";
    public static final String CONTENTS_BUDGET_HISTORY_DETAIL_PATH = CONTENTS_BUDGET_HISTORY_BASE_PATH + "/detail";


    /*
        API
        - Account
     */
    public static final String BASE_API = "/api/v1";
    public static final String ACCOUNT_BOOK_API = BASE_API + "/account_book";

    /*
        Message
     */
    public static final String REGISTER_SUCCESS_MESSAGE = "성공적으로 등록 하였습니다.";
    public static final String NOT_SELECTED_TARGET_ERROR_MESSAGE = "대상을 선택해 주세요.";
    public static final String NOT_SELECTED_CATEGORY_ERROR_MESSAGE = "카테고리를 선택해 주세요.";
    public static final String INVALID_VALUE_ERROR_MESSAGE = "잘못된 값 입니다.";
    public static final String DUPLICATED_BUDGET_ERROR_MESSAGE = "이미 등록된 예산입니다.";
    public static final String NOT_FOUND_EMAIL_ERROR_MESSAGE = "존재 하지 않는 이메일 입니다.";
    public static final String INPUT_DATE_EMPTY_ERROR_MESSAGE = "날짜 정보는 필수 입력 값 입니다.";
    public static final String NOT_FOUND_DATA_ERROR_MESSAGE = "존재 하지 않는 데이터 입니다.";
}
