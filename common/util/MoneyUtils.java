package com.ktn3.core_banking.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneyUtils {

    public static String formatMoney(Object amount, String currency) {

        if (amount == null) return currency.equals("USD") ? "0.00" : "0";

        BigDecimal value = new BigDecimal(amount.toString());

        if ("USD".equalsIgnoreCase(currency)) {
            return new DecimalFormat("#,##0.00").format(value);
        }

        return new DecimalFormat("#,###").format(value);
    }
}
