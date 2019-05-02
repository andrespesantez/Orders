package com.orders.enums;

public enum MonedaEnum
{
    USD("USD"),
    EUR("EUR"),
    CAD("CAD"),
    CHF("CHF"),
    GBP("GBP");

    MonedaEnum(String moneda) {
    }

    public String getDesc() {
        return this.name();
    }
}
