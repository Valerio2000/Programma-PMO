package project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartaTest {

    @Test
    public void testRiconoscimentoReDiDenari() {
        Carta matta = new Carta("Denari", "Re", 0.5);
        Carta cartaNormale = new Carta("Spade", "Cavallo", 0.5);
        
        assertTrue(matta.isReDiDenari(), "La carta dovrebbe essere riconosciuta come Re di Denari");
        assertFalse(cartaNormale.isReDiDenari(), "La carta non è un Re di Denari");
    }
}