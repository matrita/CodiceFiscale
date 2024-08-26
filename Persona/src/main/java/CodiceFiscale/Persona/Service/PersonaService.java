package CodiceFiscale.Persona.Service;

import CodiceFiscale.Persona.Exception.CodiceFiscaleNonValidoException;
import CodiceFiscale.Persona.Model.Persona;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

@Service
public class PersonaService {

    // Mappa per convertire il carattere del mese in un valore numerico
    private static final Map<Character, Integer> monthMap = new HashMap<>();

    static {
        monthMap.put('A', 1);   // Gennaio
        monthMap.put('B', 2);   // Febbraio
        monthMap.put('C', 3);   // Marzo
        monthMap.put('D', 4);   // Aprile
        monthMap.put('E', 5);   // Maggio
        monthMap.put('H', 6);   // Giugno
        monthMap.put('L', 7);   // Luglio
        monthMap.put('M', 8);   // Agosto
        monthMap.put('P', 9);   // Settembre
        monthMap.put('R', 10);  // Ottobre
        monthMap.put('S', 11);  // Novembre
        monthMap.put('T', 12);  // Dicembre
    }

    // Metodo principale per ottenere le informazioni di una Persona dal Codice Fiscale
    public Persona getPersonaInfo(String codiceFiscale) {

        // Validazione del Codice Fiscale
        validateCodiceFiscale(codiceFiscale);

        // Estrazione della data di nascita dal Codice Fiscale
        LocalDate dataNascita = extractDataNascita(codiceFiscale);

        // Calcolo dell'età in base alla data di nascita
        int eta = calculateAge(dataNascita);

        // Creazione dell'oggetto Persona con le informazioni estratte
        Persona persona = new Persona();
        persona.setDataNascita(dataNascita);
        persona.setEta(eta);

        return persona;
    }

    // Metodo per validare il formato del Codice Fiscale
    private void validateCodiceFiscale(String codiceFiscale) {
        if (codiceFiscale == null || codiceFiscale.length() != 16) {
            throw new CodiceFiscaleNonValidoException("Il Codice Fiscale deve essere di 16 caratteri."  + codiceFiscale);
        }
        if (!codiceFiscale.matches("^[A-Z0-9]{16}$")) {
            throw new CodiceFiscaleNonValidoException("Il Codice Fiscale contiene caratteri non validi."  + codiceFiscale);
        }
    }

    // Metodo per estrarre la data di nascita dal Codice Fiscale
    private LocalDate extractDataNascita(String codiceFiscale) {
        try {
            // Estrazione dell'anno, del mese e del giorno dal Codice Fiscale
            String yearString = codiceFiscale.substring(6, 8);
            char monthChar = codiceFiscale.charAt(8);
            String dayString = codiceFiscale.substring(9, 11);

            // Determinazione dell'anno completo (es. 96 -> 1996 o 2096)
            int year = determinareAnnoCompleto(Integer.parseInt(yearString));

            // Conversione del carattere del mese in numero
            int month = monthMap.get(monthChar);

            // Conversione del giorno in numero, con gestione dell'offset per le donne
            int day = Integer.parseInt(dayString);
            if (day > 40) {
                day -= 40; // Le donne hanno un offset di 40
            }

            // Costruzione della data di nascita
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            // Gestione delle eccezioni con un messaggio chiaro in caso di errore
            throw new CodiceFiscaleNonValidoException("Errore nell'estrazione della data di nascita dal Codice Fiscale."  + codiceFiscale);
        }
    }

    // Metodo per calcolare l'età basata sulla data di nascita
    private int calculateAge(LocalDate dataNascita) {
        return Period.between(dataNascita, LocalDate.now()).getYears();
    }

    // Metodo per determinare l'anno completo partendo dalle ultime due cifre
    private int determinareAnnoCompleto(int annoBreve) {
        // Ottieni le ultime due cifre dell'anno corrente (es. 24 per l'anno 2024)
        int annoCorrente = LocalDate.now().getYear() % 100;

        // Determina il secolo:
        // - Se annoBreve è minore o uguale all'anno corrente, appartiene al 2000 altrimenti al 1900.
        int secolo = (annoBreve <= annoCorrente) ? 2000 : 1900;

        // Ritorna l'anno completo sommando il secolo al valore di annoBreve.
        return secolo + annoBreve;
    }
}