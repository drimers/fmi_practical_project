package com.stefanpetkov.medical.util;

import org.springframework.http.HttpMethod;

public final class Constants {

    //== http methods ==
    public static final String HTTP_METHOD_GET = HttpMethod.GET.name();
    public static final String HTTP_METHOD_PUT = HttpMethod.PUT.name();
    public static final String HTTP_METHOD_POST = HttpMethod.POST.name();
    public static final String HTTP_METHOD_PATCH = HttpMethod.PATCH.name();
    public static final String HTTP_METHOD_DELETE = HttpMethod.DELETE.name();
    //== http methods ==

    //---------------------------------------------------------------------------------------

    //== request mappings ==
    public static final String REQUEST_MAPPING_DOCTOR = "/doctor";
    public static final String REQUEST_MAPPING_PATIENT = "/patient";
    public static final String REQUEST_MAPPING_HOME = "/home";
    public static final String REQUEST_MAPPING_LOGIN = "/login";
    public static final String REQUEST_MAPPING_LOGOUT = "/logout";
    public static final String REQUEST_MAPPING_ADMIN = "/admin";
    //== end of request mappings ==

    //---------------------------------------------------------------------------------------

    //== view names ==
    public static final String VIEW_NAME_DOCTORS = "/doctor/doctor";
    //== end of view names ==

    //---------------------------------------------------------------------------------------

    //== model attributes ==
    public static final String PATIENTS = "patients";
    //== end of model attributes ==

    //---------------------------------------------------------------------------------------

    //== Date Time Format Patterns ==
    public static final String DAY_MONTH_YEAR_HOUR_MINUTE_FORMAT = "dd-MM-yyyy HH:mm";
    //== end of Date Time Format Patterns ==


    //---------------------------------------------------------------------------------------

    //== security related ==
    public static final String LOGIN_FORM_ACTION = "/logon";
    public static final String LOGIN_FORM_USERNAME_PARAMETER = "email";
    public static final String LOGIN_FORM_PASSWORD_PARAMETER = "password";

    public static final String LOGOUT_ACTION = "/logout";

    public static final String[] ALLOWED_REQUEST_MATCHERS = new String[]{
            //== static resources ==
            "/static/**",
            "/assets/**",
            "/css/**",
            "/fonts/**",
            "/img/**",
            "/js/**",
            "/vendor/**",
            //== request mappings ==
            "/",
            "/home",
            "/login",
            "/save",
            "/saveContactForm",
            "/register",
            "/contact"
    };

    public static final String[] SESSION_COOKIES_TO_DELETE = new String[]{
            "JSESSIONID"
    };

    public static final String ROLE_PREFIX = "ROLE_";
    //== end of security related ==

    //---------------------------------------------------------------------------------------

    //== constructors ==
    private Constants() {
    }

}// end of class ApplicationConstants