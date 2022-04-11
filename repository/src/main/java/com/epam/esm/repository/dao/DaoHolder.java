package com.epam.esm.repository.dao;

public class DaoHolder {
    private static DaoHolder instance;

    private DaoHolder() {
    }

    public static DaoHolder getInstance() {
        if (instance == null) {
            instance = new DaoHolder();
        }
        return instance;
    }
}
