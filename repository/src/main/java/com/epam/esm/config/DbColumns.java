package com.epam.esm.config;

public final class DbColumns {
    public static final String tagsTable = "tags";
    public static final String certificateTable = "certificates";
    public static final String relationTable = "certificates_tags";

    public static final String tagId = tagsTable + ".id";
    public static final String tagName = tagsTable + ".name";

    public static final String id = "id";
    public static final String name = "name";
    public static final String description = "description";
    public static final String price = "price";
    public static final String creationDate = "creation_date";
    public static final String modificationDate = "modification_date";
    public static final String duration = "duration";

    public static final String relationCertificateId = "certificate_id";
    public static final String relationTagId = "tag_id";

    public static final String certificateId = certificateTable + "." + id;
    public static final String certificateName = certificateTable + "." + name;
    public static final String certificateDescription = certificateTable + "." + description;
    public static final String certificatePrice = certificateTable + "." + price;
    public static final String certificateCreationDate = certificateTable + "." + creationDate;
    public static final String certificateModificationDate = certificateTable + "." + modificationDate;
    public static final String certificateDuration = certificateTable + "." + duration;

    public static final String relationCertificateTableId = relationTable + "." + relationCertificateId;
    public static final String relationTagTableId = relationTable + "." + relationTagId;
}
