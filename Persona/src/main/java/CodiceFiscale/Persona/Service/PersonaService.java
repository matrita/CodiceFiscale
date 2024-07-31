package CodiceFiscale.Persona.Service;

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
        // Estrazione delle componenti della data di nascita
        String yearString = codiceFiscale.substring(6, 8);
        char monthChar = codiceFiscale.charAt(8);
        String dayString = codiceFiscale.substring(9, 11);

        // Calcolo dell'anno
        int year = Integer.parseInt(yearString);
        year += (year < 25) ? 2000 : 1900;

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
}
