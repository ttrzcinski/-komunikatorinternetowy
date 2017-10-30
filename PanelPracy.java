/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PanelPracy.java
 *
 * Created on 2010-04-15, 09:13:18
 */

package logopisarz;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.TextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Klasa panelu komponentu odpowiadająca za jego wygląð
 * i komunikację z obiektem logu
 *
 * @author ADMIN
 */
public class PanelPracy extends JPanel {

    //zmienne
    private InterfejsIO log = new InterfejsIO();
    //obiekt włąściwy klasy do obslugi logu
    private boolean zapisywac = true;//flaga okreslajaca, czy prowadzic zapis

    /** Creates new form PanelPracy */
    public PanelPracy()//konstruktor bezparametrowy
    {
        initComponents();//wyrysuj komponenty dodawane automatycznie
        this.ustawStan(0);//ustaw stan komponentu na czeka
    }

    //Procedura ustawiająca stan komponentu na zadany
    //zmienia jego kolor, tekst i stan flagi zapisywac
    private void ustawStan(int nr_stanu)
    {
        switch(nr_stanu)
        {
            case 0: //czeka
                this.PrzKomaUstawien.setText("Czeka");
                this.PrzKomaUstawien.setBackground(Color.YELLOW);
                this.zapisywac=true;
                break;
            case 1: //zapisuje
                this.PrzKomaUstawien.setText("Zapis");
                this.PrzKomaUstawien.setBackground(Color.RED);
                this.zapisywac=true;
                break;
            case 2: //wylaczony
                this.PrzKomaUstawien.setText("Wył.");
                this.PrzKomaUstawien.setBackground(Color.GRAY);
                this.zapisywac=false;
                break;
        }
    }

    //Procedura interfejsujaca zapis trsci do pliku logu
    public void dopisz(String co_dopisac)
    {
        //jesli flaga zapisu jest podniesiona
        if (this.zapisywac == true)
        {
            this.ustawStan(1);//ustaw stan na zapisuje
            this.log.dopisz(co_dopisac);
            //dopisz tresc do pliku przez obiekt log
            this.ustawStan(0);//ustaw tresc na czeka
        }
    }

    //settery
    public void setSciezkeDoPliku(String sciezka){this.log.setSciezkaPliku(sciezka.trim());}
    public void setNazwaPliku(String nazwa){this.log.setNazwaPliku(nazwa);}
    public void setZapisywac(boolean zapis){this.zapisywac = zapis;}

    //gettery
    public String getSciezkaDoPliku(){return this.log.getSciezkaPliku();}
    public String getNazwaPliku(){return this.log.getNazwaPliku();}
    public boolean isZapisywac(){return this.zapisywac;}

    //Procedura pokazujaca komunikat ustawien
    private void pokazDialogUstawien()
    {
        //utworz pola tekstowe zze sciezka i nazwa pliku
        TextField TexSciezkaPliku = new TextField(this.log.getSciezkaPliku(),30);
        TextField TexNazwaPliku = new TextField(this.log.getNazwaPliku(), 30);
        //oraz pole zaznaczenia z flaga zapisu
        Checkbox ChkZapisywac = new Checkbox("zapisuj do logu", this.zapisywac);

        //utworz tablice komponenetow do wyswietlenia na dialogu
        Object[] doDialogu = {"Scieżka do pliku logu:", TexSciezkaPliku, 
        "Nazwa pliku logu:", TexNazwaPliku, ChkZapisywac};

        //zapisz wartosc wcisnietego przycisku na instancji komunikatu
        //ustawien jako komunikatu potwierdzajacego z tablica komponentow
        //jako trescią komunikatu
        int coWybrano = JOptionPane.showConfirmDialog(this,
                doDialogu,
                "Parametry zapisu logu",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
                );

        //jesli nacisnieto ok na komunikacie
        if (coWybrano == JOptionPane.OK_OPTION)
        {
            //jesli zostala wprowadzona jakas tresc w nazwie pliku
            if (TexNazwaPliku.getText().isEmpty() == false)
            {
                //ustaw sciezke pliku w obiekcie na zadaną
                this.log.setSciezkaPliku(TexSciezkaPliku.getText().trim());
                //ustaw nazwe pliku w obiekcie na zadaną
                this.log.setNazwaPliku(TexNazwaPliku.getText().trim());
                //ustaw stan flagu zapisu na zadany
                this.zapisywac = ChkZapisywac.getState();

                if (this.zapisywac == false)//jesli flaga jest opuszczona
                {
                    this.ustawStan(2);//ustaw stan komponentu na wyłąćzony
                }
                else//w przeciwnym wypadku
                {
                    //ustaw stan komponentu na czeka
                    this.ustawStan(0);
                }

                //pokaz komunikat o zmianie wartosci
                JOptionPane.showMessageDialog(this, 
                        "Zmieniono adres pliku logu na "+this.log.getSciezkaPliku()+this.log.getNazwaPliku()+".",
                        "Sukces", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                //pokaz komunikat bledu wprowadzonych danych
                JOptionPane.showMessageDialog(this,
                        "Błędnie wprowadzone dane.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PrzKomaUstawien = new javax.swing.JButton();

        PrzKomaUstawien.setBackground(new java.awt.Color(0, 204, 0));
        PrzKomaUstawien.setText("Czeka");
        PrzKomaUstawien.setToolTipText("Po kliknięciu pokazuje okno ustawień zapisu do logu.");
        PrzKomaUstawien.setBorder(null);
        PrzKomaUstawien.setBorderPainted(false);
        PrzKomaUstawien.setMargin(new java.awt.Insets(0, 0, 0, 0));
        PrzKomaUstawien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PrzKomaUstawienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrzKomaUstawien, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PrzKomaUstawien, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void PrzKomaUstawienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrzKomaUstawienActionPerformed
        this.pokazDialogUstawien();
    }//GEN-LAST:event_PrzKomaUstawienActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton PrzKomaUstawien;
    // End of variables declaration//GEN-END:variables

}
