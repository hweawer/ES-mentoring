package com.epam.esm.service.certificate.update.property.event;

import org.springframework.context.ApplicationEvent;

import java.math.BigDecimal;

public class PriceChangeEvent extends ApplicationEvent {
    private String message;
    private Long certificateId;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public PriceChangeEvent(Object source, Long id, BigDecimal oldPrice, BigDecimal newPrice) {
        super(source);
        this.certificateId = id;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public String getMessage() {
        return "Price was changed : certificate id = " + certificateId
                + ", old price = " + oldPrice + ", new price = " + newPrice;
    }
}
