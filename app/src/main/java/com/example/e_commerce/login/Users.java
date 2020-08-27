package com.example.e_commerce.login;

public class Users {
        public static final String DATABASE_NAME = "com.example.ichallenge";
        public static final String USER = "user";

        public static class UserTable {
            public static final String TABLE_NAME = "users";
            public static final String ID = "id";
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
            public static final String EMAIL = "email";
            public static final String BIRTHDATE = "birthdate";
            public static final String createTable= "create table " + Users.UserTable.TABLE_NAME
                    + "(" + Users.UserTable.ID + " integer primary key, "
                    + Users.UserTable.USERNAME + " text not null, "
                    + UserTable.EMAIL + " text not null, "
                    + Users.UserTable.PASSWORD + " text,"
                    + UserTable.BIRTHDATE + " text)";
        }

}
