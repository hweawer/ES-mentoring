package com.epam.esm.repository.config;

public final class CertificateTable {
    private CertificateTable(){}

    public static final String TABLE_NAME = "certificates";

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "price";
    public static final String CREATION_DATE = "creation_date";
    public static final String MODIFICATION_DATE = "modification_date";
    public static final String DURATION = "duration";

    public static final String CERTIFICATE_ID = TABLE_NAME + "." + ID;
    public static final String CERTIFICATE_NAME = TABLE_NAME + "." + NAME;
    public static final String CERTIFICATE_DESCRIPTION = TABLE_NAME + "." + DESCRIPTION;
    public static final String CERTIFICATE_PRICE = TABLE_NAME + "." + PRICE;
    public static final String CERTIFICATE_CREATION_DATE = TABLE_NAME + "." + CREATION_DATE;
    public static final String CERTIFICATE_MODIFICATION_DATE = TABLE_NAME + "." + MODIFICATION_DATE;
    public static final String CERTIFICATE_DURATION = TABLE_NAME + "." + DURATION;
}
