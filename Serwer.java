/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Klasa odpowiadaj¹ca za dzialanie serwera brodcastowego.
 *
 *
 * @author Maciej
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class Serwer {

    ArrayList strumienieWyjsciowe;//wektor strumieni wyjsciowych s³u¿¹cych
    //do przechowywania danych do komunikacji z u¿ytkownikami online
    InterfejsIO log;//odpowiada za obiekt logu

    //Klasa wewnêtrzna odpowiadaj¹æa za obs³ugê klientów serwera indywidualnie
    public class ObslugaKlientow implements Runnable
    {
        private BufferedReader czytWiad;//strunie s³u¿¹cy do czyatania wiadomoœci
        private Socket gniazdo;//obiekt gniazda komunikacujnego, czyli adres i port

        //Konstruktor parametrowy interfejsu
        public ObslugaKlientow(Socket clientSocket)
        {
            try
            {//spróbuj
                gniazdo = clientSocket;//ustaw gmniazdo na adres i port klienta
                //odczytaj strumien wejsciowy ze strony zadanego klienta
                InputStreamReader isReader = new InputStreamReader(gniazdo.getInputStream());
                czytWiad = new BufferedReader(isReader);//zbuforuj go
            }
            catch(Exception ex)
            {
                ex.printStackTrace();//wypisz drzewko b³êdu}
            }
        }

        //Procedura wymuszajca dzialanie w w¹tku
        public void run(){
            String wiadomosc;//³¹ñcuch wiadomosci
            try{//spróbuj
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
                ex.printStackTrace();//wypisz drzewko b³êdu
            }
        }
    }
    //koniec klasy ObslugaKlientow

    //glówna procedura wymuszajaca dzialanie obiektu serwera
    //na instacji konsturktora.. którego nie ma..
    public static void main(String[] args){
        new Serwer().dzialanie();
    }

    //Procedura odpowiadajaca za prace obiektu serwera
    public void dzialanie(){
        
        strumienieWyjsciowe = new ArrayList();//nowa lista strumienii
        try{//sprobuj
            //ustaw port serwera na 5000
            ServerSocket serverSock = new ServerSocket(5000);

            //dopóki mo¿na
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

    //Procedura wysy³aj¹ca ³¹ñcuch do wszstkich klientów
    public void wyslij(String message){
        //iterator s³u¿¹cy do obœ³ugoi po kolei strumieni wyjsciowych
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
