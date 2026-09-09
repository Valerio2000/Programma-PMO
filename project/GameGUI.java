package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameGUI extends JFrame {
    private GameLogic game;
    
    // Componenti GUI
    private JPanel panelGiocatore;
    private JPanel panelBanco;
    private JLabel labelPunteggioGiocatore;
    private JLabel labelPunteggioBanco;
    private JLabel labelViteGiocatore;
    private JLabel labelViteBanco;
    private JLabel labelMessaggio;
    private JButton btnCarta;
    private JButton btnStai;
    private JButton btnNuovaPartita;
    
    public GameGUI() {
        game = new GameLogic();
        
        setTitle("Sette e Mezzo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        
        inizializzaComponenti();
        game.nuovaPartita();
        aggiornaInterfaccia();
        
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private void inizializzaComponenti() {
        // Panel superiore con informazioni
        JPanel panelInfo = new JPanel(new GridLayout(1, 3, 10, 0));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        labelViteGiocatore = new JLabel("Tue Vite: 3", SwingConstants.CENTER);
        labelViteGiocatore.setFont(new Font("Arial", Font.BOLD, 18));
        labelViteGiocatore.setForeground(new Color(0, 128, 0));
        
        labelMessaggio = new JLabel("Buona fortuna!", SwingConstants.CENTER);
        labelMessaggio.setFont(new Font("Arial", Font.BOLD, 16));
        
        labelViteBanco = new JLabel("Vite Banco: 3", SwingConstants.CENTER);
        labelViteBanco.setFont(new Font("Arial", Font.BOLD, 18));
        labelViteBanco.setForeground(Color.RED);
        
        panelInfo.add(labelViteGiocatore);
        panelInfo.add(labelMessaggio);
        panelInfo.add(labelViteBanco);
        
        // Panel centrale con le carte
        JPanel panelCentrale = new JPanel(new GridLayout(2, 1, 10, 10));
        panelCentrale.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Sezione giocatore
        JPanel sezioneggiocatore = new JPanel(new BorderLayout());
        sezioneggiocatore.setBorder(BorderFactory.createTitledBorder("Le tue carte"));
        
        labelPunteggioGiocatore = new JLabel("Punteggio: 0", SwingConstants.CENTER);
        labelPunteggioGiocatore.setFont(new Font("Arial", Font.BOLD, 16));
        
        panelGiocatore = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelGiocatore.setBackground(new Color(34, 139, 34));
        
        sezioneggiocatore.add(labelPunteggioGiocatore, BorderLayout.NORTH);
        sezioneggiocatore.add(panelGiocatore, BorderLayout.CENTER);
        
        // Sezione banco
        JPanel sezioneBanco = new JPanel(new BorderLayout());
        sezioneBanco.setBorder(BorderFactory.createTitledBorder("Carte del Banco"));
        
        labelPunteggioBanco = new JLabel("Punteggio: 0", SwingConstants.CENTER);
        labelPunteggioBanco.setFont(new Font("Arial", Font.BOLD, 16));
        
        panelBanco = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelBanco.setBackground(new Color(139, 0, 0));
        
        sezioneBanco.add(labelPunteggioBanco, BorderLayout.NORTH);
        sezioneBanco.add(panelBanco, BorderLayout.CENTER);
        
        panelCentrale.add(sezioneggiocatore);
        panelCentrale.add(sezioneBanco);
        
        // Panel inferiore con pulsanti
        JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelPulsanti.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnCarta = new JButton("Carta");
        btnCarta.setFont(new Font("Arial", Font.BOLD, 16));
        btnCarta.setPreferredSize(new Dimension(120, 40));
        // LAMBDA EXPRESSION
        btnCarta.addActionListener(e -> pescaCarta());
        
        btnStai = new JButton("Stai");
        btnStai.setFont(new Font("Arial", Font.BOLD, 16));
        btnStai.setPreferredSize(new Dimension(120, 40));
        // LAMBDA EXPRESSION
        btnStai.addActionListener(e -> stai());
        
        btnNuovaPartita = new JButton("Nuova Partita");
        btnNuovaPartita.setFont(new Font("Arial", Font.BOLD, 16));
        btnNuovaPartita.setPreferredSize(new Dimension(150, 40));
        // LAMBDA EXPRESSION
        btnNuovaPartita.addActionListener(e -> nuovaPartita());
        
        panelPulsanti.add(btnCarta);
        panelPulsanti.add(btnStai);
        panelPulsanti.add(btnNuovaPartita);
        
        // Aggiungi tutto al frame
        add(panelInfo, BorderLayout.NORTH);
        add(panelCentrale, BorderLayout.CENTER);
        add(panelPulsanti, BorderLayout.SOUTH);
    }
    
    private void pescaCarta() {
        if (!game.isPartitaFinita() && !game.isGameOver()) {
            game.giocatorePescaCarta();
            
            // Controlla se il giocatore ha pescato il Re di Denari
            if (game.getGiocatore().haReDiDenari() && game.getValoreReDiDenariGiocatore() == -1) {
                mostraDialogoReDiDenari();
            }
            
            aggiornaInterfaccia();
            
            if (game.isPartitaFinita()) {
                mostraRisultato();
            }
        }
    }
    
    private void mostraDialogoReDiDenari() {
        double punteggioSenzaRe = game.getGiocatore().calcolaPunteggioSenzaReDiDenari();
        
        // Crea array: 0.5 oppure valori interi (1, 2, 3, 4, 5, 6, 7)
        String[] opzioni = {"0.5", "1", "2", "3", "4", "5", "6", "7"};
        
        String messaggio = "Hai pescato il Re di Denari!\n" +
                          "Punteggio attuale (senza Re): " + formatPunteggio(punteggioSenzaRe) + "\n" +
                          "Scegli il valore del Re di Denari:\n" +
                          "(Puoi scegliere 0.5 oppure un valore intero da 1 a 7)";
        
        String scelta = (String) JOptionPane.showInputDialog(
            this,
            messaggio,
            "Re di Denari",
            JOptionPane.QUESTION_MESSAGE,
            null,
            opzioni,
            opzioni[0]
        );
        
        if (scelta != null) {
            double valore = Double.parseDouble(scelta);
            game.setValoreReDiDenariGiocatore(valore);
        } else {
            // Se l'utente chiude il dialogo, imposta 0.5 come default
            game.setValoreReDiDenariGiocatore(0.5);
        }
    }
    
    private void stai() {
        if (!game.isPartitaFinita() && !game.isGameOver()) {
            game.giocatoreStai();
            aggiornaInterfaccia();
            mostraRisultato();
        }
    }
    
    private void nuovaPartita() {
        if (game.isGameOver()) {
            String messaggio;
            if (game.getViteGiocatore() <= 0) {
                messaggio = "Hai finito le vite! Il banco ha vinto. Vuoi ricominciare da capo?";
            } else {
                messaggio = "Il banco ha finito le vite! Hai vinto! Vuoi ricominciare da capo?";
            }
            
            int risposta = JOptionPane.showConfirmDialog(this, 
                messaggio, 
                "Game Over", 
                JOptionPane.YES_NO_OPTION);
            
            if (risposta == JOptionPane.YES_OPTION) {
                game = new GameLogic();
            } else {
                return;
            }
        }
        
        game.nuovaPartita();
        aggiornaInterfaccia();
        labelMessaggio.setText("Buona fortuna!");
        btnCarta.setEnabled(true);
        btnStai.setEnabled(true);
    }
    
    private void aggiornaInterfaccia() {
        // Aggiorna vite
        labelViteGiocatore.setText("Tue Vite: " + game.getViteGiocatore());
        labelViteBanco.setText("Vite Banco: " + game.getViteBanco());
        
        // Aggiorna carte giocatore
        panelGiocatore.removeAll();
        for (Carta carta : game.getGiocatore().getMano()) {
            panelGiocatore.add(creaPanelCarta(carta, true));
        }
        
        // Mostra punteggio giocatore
        double punteggioGiocatore = game.getGiocatore().calcolaPunteggio(game.getValoreReDiDenariGiocatore());
        if (game.getGiocatore().haReDiDenari() && game.getValoreReDiDenariGiocatore() == -1) {
            double senzaRe = game.getGiocatore().calcolaPunteggioSenzaReDiDenari();
            labelPunteggioGiocatore.setText("Punteggio: " + formatPunteggio(senzaRe) + " + Re di Denari (?)");
        } else {
            labelPunteggioGiocatore.setText("Punteggio: " + formatPunteggio(punteggioGiocatore));
        }
        
        // Aggiorna carte banco (solo se il turno del giocatore è finito)
        panelBanco.removeAll();
        if (game.isTurnoGiocatoreFinito()) {
            for (Carta carta : game.getBanco().getMano()) {
                panelBanco.add(creaPanelCarta(carta, false));
            }
            double punteggioBanco = game.getBanco().calcolaPunteggio(game.getValoreReDiDenariBanco());
            labelPunteggioBanco.setText("Punteggio: " + formatPunteggio(punteggioBanco));
        } else {
            labelPunteggioBanco.setText("Punteggio: ?");
        }
        
        // Disabilita pulsanti se necessario
        if (game.isPartitaFinita() || game.isGameOver()) {
            btnCarta.setEnabled(false);
            btnStai.setEnabled(false);
        }
        
        panelGiocatore.revalidate();
        panelGiocatore.repaint();
        panelBanco.revalidate();
        panelBanco.repaint();
    }
    
    private JPanel creaPanelCarta(Carta carta, boolean isGiocatore) {
        JPanel panelCarta = new JPanel(new BorderLayout());
        panelCarta.setPreferredSize(new Dimension(100, 140));
        panelCarta.setBackground(Color.WHITE);
        panelCarta.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        JLabel labelValore = new JLabel(carta.getValore(), SwingConstants.CENTER);
        labelValore.setFont(new Font("Arial", Font.BOLD, 20));
        
        JLabel labelSeme = new JLabel(carta.getSeme(), SwingConstants.CENTER);
        labelSeme.setFont(new Font("Arial", Font.PLAIN, 14));
        
        String puntiText;
        if (carta.isReDiDenari()) {
            if (isGiocatore && game.getValoreReDiDenariGiocatore() > 0) {
                puntiText = formatPunteggio(game.getValoreReDiDenariGiocatore());
            } else if (!isGiocatore && game.getValoreReDiDenariBanco() > 0) {
                puntiText = formatPunteggio(game.getValoreReDiDenariBanco());
            } else {
                puntiText = "?";
            }
        } else {
            puntiText = formatPunteggio(carta.getPunti());
        }
        
        JLabel labelPunti = new JLabel(puntiText, SwingConstants.CENTER);
        labelPunti.setFont(new Font("Arial", Font.ITALIC, 12));
        labelPunti.setForeground(Color.BLUE);
        
        panelCarta.add(labelValore, BorderLayout.NORTH);
        panelCarta.add(labelSeme, BorderLayout.CENTER);
        panelCarta.add(labelPunti, BorderLayout.SOUTH);
        
        return panelCarta;
    }
    
    private String formatPunteggio(double punteggio) {
        if (punteggio == (int) punteggio) {
            return String.valueOf((int) punteggio);
        } else {
            return String.valueOf(punteggio);
        }
    }
    
    private void mostraRisultato() {
        String risultato = game.determinaVincitore();
        labelMessaggio.setText(risultato);
        
        if (game.isGameOver()) {
            if (game.getViteGiocatore() <= 0) {
                labelMessaggio.setText("GAME OVER! Hai finito le vite!");
            } else {
                labelMessaggio.setText("VITTORIA! Il banco ha finito le vite!");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameGUI();
            }
        });
    }
}
