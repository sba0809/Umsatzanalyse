package de.sba.umsatzanalyse;

import java.math.BigDecimal;

public record MonatsauswertungResponse(String monat, BigDecimal einnahmen, BigDecimal ausgaben) {
}