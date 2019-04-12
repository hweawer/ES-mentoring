package com.epam.esm.service.find;

public interface SearchCertificateRequestTranslator<T> {
    T translate(SearchCertificateRequest searchRequest);
}
