package com.epam.esm.repository.config;

public final class TagTable {
    private TagTable(){}

    public static final String TABLE_NAME = "tags";

    public static final String ID = "id";
    public static final String NAME = "name";

    public static final String TAG_ID = TABLE_NAME + "." + ID;
    public static final String TAG_NAME = TABLE_NAME + "." + NAME;
}
