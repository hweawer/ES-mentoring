package com.epam.esm.repository.config;

public class CertificateTagTable {
    public static final String tableName = "certificates_tags";

    public static final String relationCertificateId = "certificate_id";
    public static final String relationTagId = "tag_id";

    public static final String relationCertificateTableId = tableName + "." + relationCertificateId;
    public static final String relationTagTableId = tableName + "." + relationTagId;
}
