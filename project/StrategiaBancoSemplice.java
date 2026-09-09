package project;

public class StrategiaBancoSemplice implements StrategiaBanco {
    
    @Override
    public boolean devePescare(double punteggioBanco, double punteggioGiocatore, boolean haSballato) {
        // Il banco pesca finché non supera il giocatore o sballa
        return punteggioBanco < punteggioGiocatore && !haSballato;
    }

    @Override
    public double calcolaValoreOttimaleReDiDenari(double punteggioSenzaRe, double punteggioAvversario) {
        double[] valoriPossibili = {0.5, 1, 2, 3, 4, 5, 6, 7};
        double migliorValore = 0.5;
        double migliorDifferenza = Double.MAX_VALUE;
        
        for (double valore : valoriPossibili) {
            double punteggioTotale = punteggioSenzaRe + valore;
            if (punteggioTotale <= 7.5) {
                if (punteggioTotale > punteggioAvversario) {
                    return valore;
                }
                double differenza = punteggioAvversario - punteggioTotale;
                if (differenza < migliorDifferenza) {
                    migliorDifferenza = differenza;
                    migliorValore = valore;
                }
            }
        }
        return migliorValore;
    }
}