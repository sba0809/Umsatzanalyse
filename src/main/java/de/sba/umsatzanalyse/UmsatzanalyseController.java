package de.sba.umsatzanalyse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/api/umsatzanalyse")
@Tag(name = "Umsatzanalyse", description = "APIs für Kontostand und Monatsauswertung")
public class UmsatzanalyseController {

    private final UmsatzanalyseService umsatzanalyseService;

    public UmsatzanalyseController(UmsatzanalyseService umsatzanalyseService) {
        this.umsatzanalyseService = umsatzanalyseService;
    }

    @GetMapping("/kontostand")
    @Operation(summary = "Aktuellen Kontostand abrufen", description = "Liefert den Kontostand auf Basis aller CSV-Umsätze")
    public KontostandResponse kontostand() {
        return new KontostandResponse(umsatzanalyseService.berechneKontostand());
    }

    @GetMapping("/monatsauswertung")
    @Operation(summary = "Monatliche Ein- und Ausgaben abrufen", description = "Liefert für einen Monat die Summe der Einnahmen und Ausgaben")
    public MonatsauswertungResponse monatsauswertung(
            @Parameter(description = "Monat im Format JJJJ-MM, z. B. 2026-03")
            @RequestParam
            @DateTimeFormat(pattern = "yyyy-MM") YearMonth monat) {
        return umsatzanalyseService.berechneMonatsauswertung(monat);
    }
}