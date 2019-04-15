package com.epam.esm.service.certificate;

import com.epam.esm.service.certificate.impl.SearchCertificateRequest;

public interface SearchCertificateRequestTranslator<T> {
    T translate(SearchCertificateRequest searchRequest);
}
