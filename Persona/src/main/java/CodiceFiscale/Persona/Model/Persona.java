package CodiceFiscale.Persona.Model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Persona {
    private LocalDate dataNascita;
    private int eta;
}
