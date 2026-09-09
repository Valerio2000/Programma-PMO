package project;

import java.util.ArrayList;
import java.util.List;

public class Giocatore {
    private List<Carta> mano;
    private String nome;
    private boolean isBanco;
    
    public Giocatore(String nome, boolean isBanco) {
        this.nome = nome;
        this.isBanco = isBanco;
        this.mano = new ArrayList<>();
    }
    
    public void aggiungiCarta(Carta carta) {
        mano.add(carta);
    }
    
    public double calcolaPunteggio(double valoreReDiDenari) {
        if (mano.isEmpty()) {
            return 0;
        }
        
        double punteggio = 0;
        boolean haReDiDenari = false;
        
        for (Carta carta : mano) {
            if (carta.isReDiDenari()) {
                haReDiDenari = true;
            } else {
                punteggio += carta.getPunti();
            }
        }
        
        // Se c'è il Re di Denari, usa il valore scelto
        if (haReDiDenari && valoreReDiDenari >= 0) {
            punteggio += valoreReDiDenari;
        } else if (haReDiDenari && valoreReDiDenari == -1) {
            // Se non è stato ancora scelto, restituisce il punteggio senza il Re
            // (verrà mostrato come "?" nell'interfaccia)
            return punteggio;
        }
        
        return punteggio;
    }
    
    public double calcolaPunteggioSenzaReDiDenari() {
        // Uso degli Stream e delle Lambda expressions
        return mano.stream()
                   .filter(carta -> !carta.isReDiDenari())
                   .mapToDouble(Carta::getPunti)
                   .sum();
    }
    
    public boolean haReDiDenari() {
        // Uso di Stream e method reference
        return mano.stream().anyMatch(Carta::isReDiDenari);
    }
    
    public boolean haSballato(double valoreReDiDenari) {
        return calcolaPunteggio(valoreReDiDenari) > 7.5;
    }
    
    public List<Carta> getMano() {
        return mano;
    }
    
    public void svuotaMano() {
        mano.clear();
    }
    
    public String getNome() {
        return nome;
    }
    
    public boolean isBanco() {
        return isBanco;
    }
}
