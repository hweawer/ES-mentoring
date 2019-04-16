package com.epam.esm.service.certificate;

import com.epam.esm.service.certificate.impl.SearchCertificateRequest;

public interface CertificateRequestTranslator<T> {
    T translate(SearchCertificateRequest searchRequest);
}
