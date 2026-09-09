package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GiocatoreTest {

    @Test
    public void testCalcoloPunteggioSballato() {
        Giocatore g = new Giocatore("Test", false);
        g.aggiungiCarta(new Carta("Coppe", "7", 7));
        g.aggiungiCarta(new Carta("Spade", "2", 2));
        
        // Punteggio totale 9. Deve risultare sballato.
        assertTrue(g.haSballato(-1), "Il giocatore con 9 dovrebbe aver sballato");
    }
}