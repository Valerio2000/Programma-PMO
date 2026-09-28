package project;

import java.util.ArrayList;
import java.util.List;

public abstract class Giocatore {

    private final List<Carta> mano;
    private final String nome;

    protected Giocatore(String nome) {
        this.nome = nome;
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

        if (haReDiDenari && valoreReDiDenari >= 0) {
            punteggio += valoreReDiDenari;
        } else if (haReDiDenari && valoreReDiDenari == -1) {
            return punteggio;
        }

        return punteggio;
    }

    public double calcolaPunteggioSenzaReDiDenari() {
        return mano.stream()
                   .filter(carta -> !carta.isReDiDenari())
                   .mapToDouble(Carta::getPunti)
                   .sum();
    }

    public boolean haReDiDenari() {
        return mano.stream()
                   .anyMatch(Carta::isReDiDenari);
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
}