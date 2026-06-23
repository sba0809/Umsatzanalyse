package de.sba.umsatzanalyse;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
public class UmsatzanalyseMcpTools {

    private final UmsatzanalyseService umsatzanalyseService;

    public UmsatzanalyseMcpTools(UmsatzanalyseService umsatzanalyseService) {
        this.umsatzanalyseService = umsatzanalyseService;
    }

    @Tool(description = "Liefert den aktuellen Kontostand auf Basis aller verfügbaren Kontoumsätze")
    public KontostandResponse aktuellerKontostand() {
        return new KontostandResponse(umsatzanalyseService.berechneKontostand());
    }

    @Tool(description = "Liefert für einen Monat im Format JJJJ-MM die Summe der Einnahmen und Ausgaben")
    public MonatsauswertungResponse monatsauswertung(
            @ToolParam(description = "Monat im Format JJJJ-MM, zum Beispiel 2026-03") String monat) {
        return umsatzanalyseService.berechneMonatsauswertung(YearMonth.parse(monat));
    }
}