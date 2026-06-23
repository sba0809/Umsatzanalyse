package de.sba.umsatzanalyse;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Umsatz(LocalDate datum, String beschreibung, BigDecimal betrag, String kategorie) {
}