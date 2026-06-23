package de.sba.umsatzanalyse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello", description = "Minimale Demo-Endpunkte der Umsatzanalyse")
public class HelloController {

    @GetMapping("/")
    @Operation(summary = "Startseite", description = "Liefert eine einfache Statusmeldung der Anwendung zurück")
    public String home() {
        return "Umsatzanalyse läuft";
    }
}