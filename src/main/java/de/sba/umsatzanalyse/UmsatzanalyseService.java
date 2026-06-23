package de.sba.umsatzanalyse;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class UmsatzanalyseService {

    private final List<Umsatz> umsaetze = new ArrayList<>();

    @PostConstruct
    void ladeUmsaetze() {
        ClassPathResource resource = new ClassPathResource("umsatzdaten.csv");

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {

            reader.lines()
                    .skip(1)
                    .filter(line -> !line.isBlank())
                    .map(this::parseLine)
                    .sorted(Comparator.comparing(Umsatz::datum))
                    .forEach(umsaetze::add);
        } catch (IOException e) {
            throw new IllegalStateException("CSV-Datei mit Umsatzdaten konnte nicht geladen werden", e);
        }
    }

    public BigDecimal berechneKontostand() {
        return umsaetze.stream()
                .map(Umsatz::betrag)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public MonatsauswertungResponse berechneMonatsauswertung(YearMonth monat) {
        BigDecimal einnahmen = BigDecimal.ZERO;
        BigDecimal ausgaben = BigDecimal.ZERO;

        for (Umsatz umsatz : umsaetze) {
            if (!YearMonth.from(umsatz.datum()).equals(monat)) {
                continue;
            }

            if (umsatz.betrag().signum() >= 0) {
                einnahmen = einnahmen.add(umsatz.betrag());
            } else {
                ausgaben = ausgaben.add(umsatz.betrag().abs());
            }
        }

        return new MonatsauswertungResponse(monat.toString(), einnahmen, ausgaben);
    }

    private Umsatz parseLine(String line) {
        String[] parts = line.split(",", 4);

        if (parts.length != 4) {
            throw new IllegalArgumentException("Ungültige CSV-Zeile: " + line);
        }

        return new Umsatz(
                LocalDate.parse(parts[0].trim()),
                parts[1].trim(),
                new BigDecimal(parts[2].trim()),
                parts[3].trim()
        );
    }
}