package CodiceFiscale.Persona.Service;

import CodiceFiscale.Persona.Exception.CodiceFiscaleNonValidoException;
import CodiceFiscale.Persona.Model.Persona;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonaService {
    private static final Map<Character, Integer> monthMap = new HashMap<>();
    static {
        monthMap.put('A', 1);
        monthMap.put('B', 2);
        monthMap.put('C', 3);
        monthMap.put('D', 4);
        monthMap.put('E', 5);
        monthMap.put('H', 6);
        monthMap.put('L', 7);
        monthMap.put('M', 8);
        monthMap.put('P', 9);
        monthMap.put('R', 10);
        monthMap.put('S', 11);
        monthMap.put('T', 12);
    }

    public Persona getPersonaInfo(String codiceFiscale) {

        if (!isValidCodiceFiscale(codiceFiscale)) {
            throw new CodiceFiscaleNonValidoException("Codice Fiscale non valido: " + codiceFiscale);
        }

        // Estrazione delle componenti della data di nascita
        String yearString = codiceFiscale.substring(6, 8);
        char monthChar = codiceFiscale.charAt(8);
        String dayString = codiceFiscale.substring(9, 11);

        // Calcolo dell'anno usando il metodo
        int year = determinareAnnoCompleto(Integer.parseInt(yearString));

        // Calcolo del mese
        int month = monthMap.get(monthChar);

        // Calcolo del giorno
        int day = Integer.parseInt(dayString);
        if (day > 40) {
            day -= 40; // Le donne hanno un offset di 40
        }

        // Costruzione della data di nascita
        LocalDate dataNascita = LocalDate.of(year, month, day);

        // Calcolo dell'età
        int eta = Period.between(dataNascita, LocalDate.now()).getYears();

        Persona persona = new Persona();
        persona.setDataNascita(dataNascita);
        persona.setEta(eta);

        return persona;
    }

    // Metodo per determinare il secolo corretto
    private int determinareAnnoCompleto(int annoBreve) {
        // Ottieni gli ultimi due cifre dell'anno corrente (es. 24 per l'anno 2024)
        int annoCorrente = LocalDate.now().getYear() % 100;

        // Determina il secolo:
        // - Se annoBreve è minore o uguale all'anno corrente, appartene al 2000 altrimenti appartene al 1900.
        int secolo = (annoBreve <= annoCorrente) ? 2000 : 1900;

        // Ritorna l'anno completo sommando il secolo al valore di annoBreve.
        return secolo + annoBreve;
    }

    private boolean isValidCodiceFiscale(String codiceFiscale) {
        // Implementa qui la logica di validazione del codice fiscale
        // Ad esempio, puoi verificare la lunghezza, il formato o altri criteri
        return codiceFiscale.matches("^[A-Z0-9]{16}$");
    }
}
