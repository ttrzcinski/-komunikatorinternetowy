package com.komunikatorinternetowy;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Klasa odpowiadaj�ca za dzialanie serwera brodcastowego.
 *
 *
 * @author Maciej
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Serwer {

    ArrayList strumienieWyjsciowe;//wektor strumieni wyjsciowych s�u��cych
    //do przechowywania danych do komunikacji z u�ytkownikami online
    com.komunikatorinternetowy.InterfejsIO log;//odpowiada za obiekt logu

    //Klasa wewn�trzna odpowiadaj��a za obs�ug� klient�w serwera indywidualnie
    public class ObslugaKlientow implements Runnable
    {
        private BufferedReader czytWiad;//strunie s�u��cy do czyatania wiadomo�ci
        private Socket gniazdo;//obiekt gniazda komunikacujnego, czyli adres i port

        //Konstruktor parametrowy interfejsu
        public ObslugaKlientow(Socket clientSocket)
        {
            try
            {//spr�buj
                gniazdo = clientSocket;//ustaw gmniazdo na adres i port klienta
                //odczytaj strumien wejsciowy ze strony zadanego klienta
                InputStreamReader isReader = new InputStreamReader(gniazdo.getInputStream());
                czytWiad = new BufferedReader(isReader);//zbuforuj go
            }
            catch(Exception ex)
            {
                ex.printStackTrace();//wypisz drzewko b��du}
            }
        }

        //Procedura wymuszajca dzialanie w w�tku
        public void run(){
            String wiadomosc;//���cuch wiadomosci
            try{//spr�buj
                //jesli mozna odczytac linie tekstu w wiadomosci
                while ((wiadomosc = czytWiad.readLine()) != null)
                {
                    //wypisz odczytana linie
                    System.out.println("Odczytano: " + wiadomosc);
                    //wyslij odczytana linie
                    wyslij(wiadomosc);
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();//wypisz drzewko b��du
            }
        }
    }
    //koniec klasy ObslugaKlientow

    //gl�wna procedura wymuszajaca dzialanie obiektu serwera
    //na instacji konsturktora.. kt�rego nie ma..
    public static void main(String[] args){
        new Serwer().dzialanie();
    }

    //Procedura odpowiadajaca za prace obiektu serwera
    public void dzialanie(){
        
        strumienieWyjsciowe = new ArrayList();//nowa lista strumienii
        try{//sprobuj
            //ustaw port serwera na 5000
            ServerSocket serverSock = new ServerSocket(5000);

            //dop�ki mo�na
            while(true){
                //zatwierdz port do komunikacji
                Socket gniazdoKlienta = serverSock.accept();
                //ustorz stumien do wypisywania wiadomosci
                PrintWriter pisarz = new PrintWriter(gniazdoKlienta.getOutputStream());
                //dodaj nowy strumien wyjsciowy do listy wstumieni wyjsciowych
                strumienieWyjsciowe.add(pisarz);

                //utworz nowy watek do obslugi klienta
                Thread t = new Thread(new ObslugaKlientow(gniazdoKlienta));
                //startuj prace watku
                t.start();

                System.out.println("polaczono");//wypisz na konsole informacje o polaczaeniu
            }
        }catch(Exception ex)
        {
            ex.printStackTrace();//wypisz drzewko bledu
        }
    }

    //Procedura wysy�aj�ca ���cuch do wszstkich klient�w
    public void wyslij(String message){
        //iterator s�u��cy do ob��ugoi po kolei strumieni wyjsciowych
        Iterator it = strumienieWyjsciowe.iterator();
        //dopoki jeszcze jest jakis na liscie
        while(it.hasNext()){
            try{//sprobuj
                //wybierz kolejny strumien
                PrintWriter piszWiad = (PrintWriter) it.next();
                //wypisz klientowi wiadomosc
                piszWiad.println(message);
                //wyzeruj strumien
                piszWiad.flush();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();//wypisz drzewko bledu
            }
        }
    }
}
