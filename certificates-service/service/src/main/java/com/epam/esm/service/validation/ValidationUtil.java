package com.epam.esm.service.validation;

import com.epam.esm.service.exception.IncorrectPaginationValues;

public final class ValidationUtil {
    public static void checkPagination(Integer page, Integer pageCount){
        if (page > pageCount && pageCount != 0) {
            throw new IncorrectPaginationValues("incorrect.pagination");
        }
    }
}
