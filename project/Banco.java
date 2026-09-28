package project;

public class Banco extends Giocatore {

    private final StrategiaBanco strategia;

    public Banco(String nome, StrategiaBanco strategia) {
        super(nome);
        this.strategia = strategia;
    }
    
    //può decidere se deve pescare in base alla strategia
    public boolean devePescare(double punteggioBanco, double punteggioGiocatore, boolean sballato) {
        return strategia.devePescare(punteggioBanco, punteggioGiocatore, sballato);
    }

    public double calcolaValoreOttimaleReDiDenari(double punteggioSenzaRe, double punteggioGiocatore) {
        return strategia.calcolaValoreOttimaleReDiDenari(punteggioSenzaRe, punteggioGiocatore);
    }
}
