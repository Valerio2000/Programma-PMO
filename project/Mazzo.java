package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Mazzo {
    private static Mazzo istanza; // PATTERN SINGLETON: L'unica istanza del mazzo
    private List<Carta> carte;
    
    // Costruttore privato. Nessuno fuori da questa classe può fare "new Mazzo()"
    private Mazzo() {
        carte = new ArrayList<>();
        inizializzaMazzo();
        mescola();
    }
    
    // Metodo statico per ottenere l'unica istanza del Mazzo
    public static Mazzo getInstance() {
        if (istanza == null) {
            istanza = new Mazzo();
        }
        return istanza;
    }
    
    private void inizializzaMazzo() {
        String[] semi = {"Denari", "Coppe", "Spade", "Bastoni"};
        for (String seme : semi) {
            for (int i = 1; i <= 7; i++) {
                carte.add(new Carta(seme, String.valueOf(i), i));
            }
            carte.add(new Carta(seme, "Fante", 0.5));
            carte.add(new Carta(seme, "Cavallo", 0.5));
            carte.add(new Carta(seme, "Re", 0.5));
        }
    }
    
    public void mescola() {
        Collections.shuffle(carte);
    }
    
    public Carta pescaCarta() {
        if (carte.isEmpty()) {
            inizializzaMazzo();
            mescola();
        }
        return carte.remove(0);
    }
    
    public void reset() {
        carte.clear();
        inizializzaMazzo();
        mescola();
    }
}