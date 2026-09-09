package project;


public class GameLogic {
    private Mazzo mazzo;
    private Giocatore giocatore;
    private Giocatore banco;
    private int viteGiocatore;
    private int viteBanco;
    private boolean turnoGiocatoreFinito;
    private boolean partitaFinita;
    private double valoreReDiDenariGiocatore;
    private double valoreReDiDenariBanco;
    
    private StrategiaBanco strategiaBanco;
    
    public GameLogic() {
        mazzo = Mazzo.getInstance();
        giocatore = new Giocatore("Giocatore", false);
        banco = new Giocatore("Banco", true);
        strategiaBanco = new StrategiaBancoSemplice();
        viteGiocatore = 3;
        viteBanco = 3;
        turnoGiocatoreFinito = false;
        partitaFinita = false;
        valoreReDiDenariGiocatore = -1;
        valoreReDiDenariBanco = -1;
    }
    
    public void nuovaPartita() {
        mazzo.reset();
        giocatore.svuotaMano();
        banco.svuotaMano();
        turnoGiocatoreFinito = false;
        partitaFinita = false;
        valoreReDiDenariGiocatore = -1;
        valoreReDiDenariBanco = -1;
        giocatore.aggiungiCarta(mazzo.pescaCarta());
    }
    
    public void giocatorePescaCarta() {
        if (!turnoGiocatoreFinito && !partitaFinita) {
            giocatore.aggiungiCarta(mazzo.pescaCarta());
            if (giocatore.haSballato(valoreReDiDenariGiocatore)) {
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
        double punteggioGiocatore = giocatore.calcolaPunteggio(valoreReDiDenariGiocatore);
        
        while (true) {
            double punteggioBanco = banco.calcolaPunteggio(valoreReDiDenariBanco);
            
            if (banco.haReDiDenari() && valoreReDiDenariBanco == -1) {
                valoreReDiDenariBanco = strategiaBanco.calcolaValoreOttimaleReDiDenari(banco.calcolaPunteggioSenzaReDiDenari(), punteggioGiocatore);
                punteggioBanco = banco.calcolaPunteggio(valoreReDiDenariBanco);
            }
            
            if (!strategiaBanco.devePescare(punteggioBanco, punteggioGiocatore, banco.haSballato(valoreReDiDenariBanco))) {
                break;
            }
            
            banco.aggiungiCarta(mazzo.pescaCarta());
        }
        
        partitaFinita = true;
        String risultato = determinaVincitore();
        if (risultato.contains("Banco vince")) {
            viteGiocatore--;
        } else if (risultato.contains("Hai vinto")) {
            viteBanco--;
        }
    }
    
    public String determinaVincitore() {
        if (!partitaFinita) return "Partita in corso";
        
        double punteggioGiocatore = giocatore.calcolaPunteggio(valoreReDiDenariGiocatore);
        double punteggioBanco = banco.calcolaPunteggio(valoreReDiDenariBanco);
        
        if (giocatore.haSballato(valoreReDiDenariGiocatore))
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
    	return giocatore;
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