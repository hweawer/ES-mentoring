package com.epam.esm.repository.config;

public final class CertificateTagTable {
    private CertificateTagTable(){}

    public static final String TABLE_NAME = "certificates_tags";

    public static final String RELATION_CERTIFICATE_ID = "certificate_id";
    public static final String RELATION_TAG_ID = "tag_id";

    public static final String RELATION_CERTIFICATE_TABLE_ID = TABLE_NAME + "." + RELATION_CERTIFICATE_ID;
    public static final String RELATION_TAG_TABLE_ID = TABLE_NAME + "." + RELATION_TAG_ID;
}
