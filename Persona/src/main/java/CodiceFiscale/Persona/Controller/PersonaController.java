package CodiceFiscale.Persona.Controller;

import CodiceFiscale.Persona.Model.Persona;
import CodiceFiscale.Persona.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @GetMapping("/api/persona/{codiceFiscale}")
    public Persona getPersonaInfo(@PathVariable String codiceFiscale) {
        return personaService.getPersonaInfo(codiceFiscale);
    }
}
