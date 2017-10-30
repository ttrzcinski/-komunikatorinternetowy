/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logopisarz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Klasa służąca do włąściwego operowania na treści plików i zmiennych.
 *
 * @author ADMIN
 */
public class InterfejsIO {
    //konstruktory
    InterfejsIO()//konstruktore bezparametrowy
    {
        this.zeruj();//wyzeruj zmienne
    }
    InterfejsIO(String NazwaPliku)//konstruktor parametrowy
    {
        this.zeruj();//wyzeruj zmienne
        //ustaw nazwe pliku na zadaną w parametrze
        this.nazwa_pliku = NazwaPliku;
    }

    //zmienne
    private String nazwa_pliku;//nazwa pliku logu
    private String sciezka;//sciezka dostepu do pliku logu

    private String ostatni_blad;//tresc ostatniego bledu,
    //ktory wystapil

    //procedury wewnętrzne
    private void zeruj()//zeruje wartosci zmiennyhc prywatnych
    {
        this.sciezka = "";//to ten sam folder
        this.nazwa_pliku = "Log.txt";//plik logu to Log.txt
        this.ostatni_blad = "Brak błędu";//nie ma błędu
    }//

    //ustawia tresc bledu na zadaną
    private void zaznaczBlad(String tresc){this.ostatni_blad=tresc;}

    //procedura zwraca aktualna date i godzine w okreslonej formatce
    private String zwrocAktualnaDate()
    {
        String data="";//Wyzeruj zmienną
        Date teraz = Calendar.getInstance().getTime();//Pobierz aktualny moment
        //poniżej utwórz formatkę do konwersji daty.
        SimpleDateFormat formatka = new SimpleDateFormat("HH:mm dd-MM-yyyy");
        data = formatka.format(teraz);//Konwersja daty przez fortmatkę

        return data;//zwroc sformatowana date
    }//

    //Procedury wymiany danych z plikami
    //Procedura dodająca date i godzine do tresci wpisu
    private String zwrocLinieDoWpisu(String tresc)
    {
        String zwr="";
        //dodaje godzine i date i naziwsy doń do tresci
        zwr = "[" + this.zwrocAktualnaDate() + "] " + tresc;
        return zwr;//zwroc powsatłą linie tekstu
    }//

    //Procedura tworzy nowy plik logu
    private void utworzNowyPlik()
    {
        try//spróbuj
        {
            //utworz strumien do zapisu odnoszacy sie do pliku
            //o zadanych parametrach w zmiennych prywatnych
            PrintWriter wy = new PrintWriter(new FileWriter(this.sciezka + this.nazwa_pliku));

            //dadaj do pliku naglowek
            wy.println("<<<Log programu utworzony "+this.zwrocAktualnaDate()+" >>>");
            //dopisz powód utworzenia nowego pliku
            wy.println(this.zwrocLinieDoWpisu("Nie można odczytać pliku logu. Utworzono nowy."));

            //zamknij strumien
            wy.close();
        }
        catch(IOException ex1)
        {
            //jeśli coś nie wyszło, pokaz komunikat błędu
            this.zaznaczBlad("Nie mozna utworzyc pliku "+this.sciezka + this.nazwa_pliku);
        }
    }//

    //Pobierz tresć pliku i ją zwróć jako łąńcuch tekstowy
    public String pobierzTrescLogu()
    {
        int i=0;//iterator i
        String linia = "";//linia wczytana z pliku
        String wczytano = "";//łąćżny łąńcuch liniii wczytany z pliku
        String zwr="";//łąńcuch zwrotny

        try
        {
            //czy da sie odczytac, czyli istnieje
            BufferedReader we = new BufferedReader(new FileReader(this.sciezka + this.nazwa_pliku));

            //dopóki nie da sie odczyta linie - w javie nie ma EOF
            while((linia = we.readLine()) != null)
            {
                //dopisz wczytana linie do łąńcucha wczytano i oddziel enterem
                wczytano = wczytano + linia + "\r\n";
            }

            we.close();//zamknij strumien
            zwr = wczytano;//zwroc wczytana wartosc do zmiennej zwrotnej
        }
        catch(IOException ex3)
        {
            //wypisz błąd odczytu
            this.zaznaczBlad("Nie mozna wczytać logu.");
            //wczytano="";
            //this.utworzNowyPlik();
            //wczytano = this.pobierzTrescLogu();
        }//this.utworzNowyPlik(true);}

        return zwr;//zwróc wczytaną tresc
    }//

    //Procedura dopisuje zadaną treść do pliku logu
    public void dopisz(String tresc)
    {
        try//spróbuj
        {
            String wczytano = "";//łąńcuch wczytany

            //pobierz tresc pliku logu pod zmienną wczytano
            wczytano = this.pobierzTrescLogu();

            //utworz strumien do pliku logu
            PrintWriter wy = new PrintWriter(new FileWriter(this.sciezka + this.nazwa_pliku));

            wy.print(wczytano);//Wypisz do pliku linie z ilością notatek
            //wy.println(tresc);//zapisz co bylo
            wy.println(this.zwrocLinieDoWpisu(tresc));//dopisz linie
            wy.close();//zamknij strumien
        }
        catch(IOException exc2)
        {
            //zaznacz tresc błęðu w zmiennej
            this.zaznaczBlad(exc2.toString());
        }
    }//

    //settery
    public void setNazwaPliku(String NowaNazwa){this.nazwa_pliku = NowaNazwa;}
    public void setSciezkaPliku(String Sciezka){this.sciezka = Sciezka;}

    public void setDomyslne(){this.zeruj();}

    //gettery
    public String getNazwaPliku(){return this.nazwa_pliku;}
    public String getSciezkaPliku(){return this.sciezka;}
    public String getOstatniBlad(){return this.ostatni_blad;}
}//
