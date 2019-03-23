package com.epam.esm.repository.config;

public class CertificateTable {
    public static final String tableName = "certificates";

    public static final String id = "id";
    public static final String name = "name";
    public static final String description = "description";
    public static final String price = "price";
    public static final String creationDate = "creation_date";
    public static final String modificationDate = "modification_date";
    public static final String duration = "duration";

    public static final String certificateId = tableName + "." + id;
    public static final String certificateName = tableName + "." + name;
    public static final String certificateDescription = tableName + "." + description;
    public static final String certificatePrice = tableName + "." + price;
    public static final String certificateCreationDate = tableName + "." + creationDate;
    public static final String certificateModificationDate = tableName + "." + modificationDate;
    public static final String certificateDuration = tableName + "." + duration;
}
