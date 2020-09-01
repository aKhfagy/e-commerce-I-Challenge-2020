package com.example.e_commerce.login;

public class Constants {
    public static final String DATABASE_NAME = "com.example.ichallenge";
    public static final String PREFERENCE_NAME = "com.example.ichallenge";
    public static final String REMEMBER_ME = "remember_me";
    public static class UserTable {
        public static final String USERS_TABLE_NAME = "users";
        public static final String ID = "id";
        public static final String USERNAME = "username";
        public static final String PASSWORD = "password";
        public static final String EMAIL = "email";
        public static final String BIRTHDATE = "birthdate";
        public static final String REVIEWS = "reviews";
        public static final String CREATE_USERS_TABLE = "create table " + Constants.UserTable.USERS_TABLE_NAME
                + "(" + Constants.UserTable.ID + " integer primary key, "
                + Constants.UserTable.USERNAME + " text not null, "
                + UserTable.EMAIL + " text not null, "
                + Constants.UserTable.PASSWORD + " text,"
                + UserTable.BIRTHDATE + " text,"
                + UserTable.REVIEWS + " text)";

    }
}
