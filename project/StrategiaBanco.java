package project;

public interface StrategiaBanco {
    boolean devePescare(double punteggioBanco, double punteggioGiocatore, boolean haSballato);
    double calcolaValoreOttimaleReDiDenari(double punteggioSenzaRe, double punteggioAvversario);
    }
