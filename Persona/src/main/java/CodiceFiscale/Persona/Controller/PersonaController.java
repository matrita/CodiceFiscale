package CodiceFiscale.Persona.Controller;

import CodiceFiscale.Persona.Model.Persona;
import CodiceFiscale.Persona.Service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PersonaController {

    private final PersonaService personaService;

    @GetMapping("/api/persona/{codiceFiscale}")
    public Persona getPersonaInfo(@PathVariable String codiceFiscale) {
        return personaService.getPersonaInfo(codiceFiscale.toUpperCase());
    }
}
