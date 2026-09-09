package project;

public class Carta {
    private String seme;
    private String valore;
    private double punti;
    
    public Carta(String seme, String valore, double punti) {
        this.seme = seme;
        this.valore = valore;
        this.punti = punti;
    }
    
    public String getSeme() {
        return seme;
    }
    
    public String getValore() {
        return valore;
    }
    
    public double getPunti() {
        return punti;
    }
    
    public boolean isReDiDenari() {
        return seme.equals("Denari") && valore.equals("Re");
    }
    
    @Override
    public String toString() {
        return valore + " di " + seme;
    }
}
