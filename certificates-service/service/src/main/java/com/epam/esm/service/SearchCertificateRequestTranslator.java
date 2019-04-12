package com.epam.esm.service;

public interface SearchCertificateRequestTranslator<T> {
    T translate(SearchCertificateRequest searchRequest);
}
