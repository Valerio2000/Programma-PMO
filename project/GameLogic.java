package project;

public class GameLogic {
    private final Mazzo mazzo;
    private final Umano giocatore1;
    private final Banco banco;
    private int viteGiocatore;
    private int viteBanco;
    private boolean turnoGiocatoreFinito;
    private boolean partitaFinita;
    private double valoreReDiDenariGiocatore;
    private double valoreReDiDenariBanco;
    
    //private final StrategiaBanco strategiaBanco;
    
    public GameLogic() {
        mazzo = Mazzo.getInstance();
        //strategiaBanco = new StrategiaBancoSemplice();
        giocatore1 = new Umano("Giocatore1");
        banco = new Banco("Banco", new StrategiaBancoSemplice());
        viteGiocatore = 3;
        viteBanco = 3;
        turnoGiocatoreFinito = false;
        partitaFinita = false;
        valoreReDiDenariGiocatore = -1;
        valoreReDiDenariBanco = -1;
    }
    
    public void nuovaPartita() {
        mazzo.reset();
        giocatore1.svuotaMano();
        banco.svuotaMano();
        turnoGiocatoreFinito = false;
        partitaFinita = false;
        valoreReDiDenariGiocatore = -1;
        valoreReDiDenariBanco = -1;
        giocatore1.aggiungiCarta(mazzo.pescaCarta());
    }
    
    public void giocatorePescaCarta() {
        if (!turnoGiocatoreFinito && !partitaFinita) {
        	giocatore1.aggiungiCarta(mazzo.pescaCarta());
            if (giocatore1.haSballato(valoreReDiDenariGiocatore)) {
                turnoGiocatoreFinito = true;
                partitaFinita = true;
                viteGiocatore--;
            }
        }
    }
    
    public void giocatoreStai() {
        if (!turnoGiocatoreFinito && !partitaFinita) {
            turnoGiocatoreFinito = true;
            giocaBanco();
        }
    }
    
    private void giocaBanco() {
        final double punteggioGiocatore = giocatore1.calcolaPunteggio(valoreReDiDenariGiocatore);

        while (true) {
            double punteggioBanco = banco.calcolaPunteggio(valoreReDiDenariBanco);

            if (banco.haReDiDenari() && valoreReDiDenariBanco == -1) {
                valoreReDiDenariBanco = banco.calcolaValoreOttimaleReDiDenari(banco.calcolaPunteggioSenzaReDiDenari(), punteggioGiocatore);
                punteggioBanco = banco.calcolaPunteggio(valoreReDiDenariBanco);
            }

            if (!banco.devePescare(punteggioBanco, punteggioGiocatore, banco.haSballato(valoreReDiDenariBanco))) {
                break;
            }
            banco.aggiungiCarta(mazzo.pescaCarta());
        }

        partitaFinita = true;
        final String risultato = determinaVincitore();

        if (risultato.contains("Banco vince")) {
            viteGiocatore--;
        } else if (risultato.contains("Hai vinto")) {
            viteBanco--;
        }
    }
    
    public String determinaVincitore() {
        if (!partitaFinita) return "Partita in corso";
        
        final double punteggioGiocatore = giocatore1.calcolaPunteggio(valoreReDiDenariGiocatore);
        final double punteggioBanco = banco.calcolaPunteggio(valoreReDiDenariBanco);
        
        if (giocatore1.haSballato(valoreReDiDenariGiocatore))
        	return "Banco vince! Hai sballato.";
        if (banco.haSballato(valoreReDiDenariBanco))
        	return "Hai vinto! Il banco ha sballato.";
        if (punteggioGiocatore > punteggioBanco)
        	return "Hai vinto!";
        if (punteggioBanco > punteggioGiocatore)
        	return "Banco vince!";
        return "Pareggio!";
    }
    
    public Giocatore getGiocatore() {
    	return giocatore1;
    	}
    
    public Giocatore getBanco() {
    	return banco; 
    	}
    
    public int getViteGiocatore(){
    	return viteGiocatore;
    	}
    
    public int getViteBanco() {
    	return viteBanco;
    	}
    
    public boolean isTurnoGiocatoreFinito() {
    	return turnoGiocatoreFinito;
    	}
    
    public boolean isPartitaFinita() {
    	return partitaFinita;
    	}
    
    public boolean isGameOver() {
    	return viteGiocatore <= 0 || viteBanco <= 0;
    	}
    
    public void setValoreReDiDenariGiocatore(double valore) {
    	this.valoreReDiDenariGiocatore = valore;
    	}
    
    public double getValoreReDiDenariGiocatore() {
    	return valoreReDiDenariGiocatore;
    	}
    
    public double getValoreReDiDenariBanco() {
    	return valoreReDiDenariBanco;
    	}
}