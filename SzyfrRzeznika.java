/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package szyfrrzeznika;

/**
 * Klasa odpowiedzialna za silnik szyfrujący komponentu.
 * Szyfrowanie i deszyfrowanie odbywa się za pomocą metody SzyfrRzeznika.
 *
 * Szyfrowanie metodą SzyfrRzeznika:
 * Lz = Lp + nz + int(plk), gdzie
 *
 * Lz - wartość zakodowanej litery,
 * Lp - wartość litery początkowa,
 * nz - numer znaku w ciągu szyfrowanym,
 * plk - pobrana wartośc liczbowa znaku z klucza prostego, czym wartość indexu
 *       jest skracana tak długo, aż będzie z zakresu 0 do długości ciągu.
 *
 * @author Tomasz "Rzeźnik" Trzciński
 */
public class SzyfrRzeznika {
    //konsteruktory
    SzyfrRzeznika(){}//bezparametrowy
    SzyfrRzeznika(String klucz_prosty, int przesuniecie_znakowe)//konstruktor parametrowy
    {
        this.klucz1 = klucz_prosty;
        //ustaw klucz prosty na podany parametr
        this.przesuniecie = przesuniecie_znakowe;
        //ustaw przesuniecie na zadane parametrem
    }//
    //koniec konstruktorów

    //zmienne
    private String klucz1 = "FajnoowyKluczeekEmoooMaartyynki";
    //klucz prosty tekstowy - służy jako podstawa szyfrowania
    private int przesuniecie = 1;
    //przesunięcie znakowe - odpowiada za przesunięcie znaku w Unicode
    //koniec zmienne
    
    //procki
    /*
     * Procedura zwracająca znak o podanym numerze z klucza prostego
     */
    public char zwrocLitereKluczaProstego(int nr)
    {
        char zwr = 'a';//utworz zmienna zwrotną

        //dopóki index (nr) litery jest z poza długości klucz prostego
        while (nr>this.klucz1.length()-1)
        {
            nr-=this.klucz1.length()-1;
            //zmiejsz index o długość klucza porstego
            //zmiejszać bedzie tak długo, az litera będzie z zakresu
            //0 do długośc klucza
        }

        zwr = this.klucz1.charAt(nr);//przypisz litere na zmienną zwrotną

        return zwr;//zwróc litere
    }//

    /*
     * Procedura odpowiadająca za zaszyforowanie zadanego łańcucha tekstowego
     * zgodnie z podanymi parametrami oraz zwrócenie go.
     */
    public String szyfruj(String tekst)
    {
        String zas = "";//zmienna zaszyfrowana - wyzeruj
        char[] eska = new char[tekst.length()];
        //tablica liter slużąca do przechowywania tekstu
        //jako wektora charów - aby wykonywać obliczenia
        //na ich wartościach w Unicode

        int i=0;//iterator i
        int oIle=0;//o ile zmienić wybraną literę
        int wart=0;//wartość unicode
        for (i=0; i<tekst.length(); i++)
        {
            //wylicz przesuniecie wartości znaku unicode
            oIle = i + this.przesuniecie + (int)this.zwrocLitereKluczaProstego(i);
            //przesun znak unicode o wyliczoną wartość
            wart = (int)tekst.charAt(i) + oIle;
            //przypisdz wartość Unicode do wektora pod kolejnym indeksem
            eska[i] = (char)wart;
        }//koniec fora

        //przetwórz wektor liter zakodowany na łąńcuch znakowy
        zas = String.copyValueOf(eska);
        return zas;//zwróc zaszyfrowany łąńcuch
    }//

    /*
     * Procedura odpowiadająca za deszyforowanie zadanego łańcucha tekstowego
     * zgodnie z podanymi parametrami oraz zwrócenie go.
     */
    public String deszyfruj(String zaszyfr)
    {
        String tekst="";//zmienna tekstu po odszyfrowaniu
        char[] efekt = new char[zaszyfr.length()];
        //tablica liter do przechowywania wyliczonych wartości
        //liter Unicode do zwrocenia

        int i=0;//iterator i
        //od początku do końca zaszyforwanego tekstu
        for (i=0; i<zaszyfr.length(); i++)
        {
            //Odszyfruj wartośc litery przez popranie jeje wartości Uniceode
            //i odjęcie jej indeksu, przesunięcia i odpowiadającej jej
            //litery z klucza prostego, po czym ją nadpiszesz
            //w tablicy pod indexem i
            efekt[i] = (char)((int)zaszyfr.charAt(i) - i - this.przesuniecie - (int)this.zwrocLitereKluczaProstego(i));
        }

        //konwertuj wartość wyliczonego wektora liter na łąńcuch znakowy
        tekst = String.copyValueOf(efekt);
        return tekst;//zwróc odszyfrowany tekst
    }//

    //setter
    /*
     * Nadpisuje zawartość klucza podstawowego zadanym łańcuchem.
     */
    public void setKluczProsty(String nowy){this.klucz1 = nowy;}//

    /*
     * Nadpisuje wartość przesunięcia zadaną wartością, pod warunkiem, że
     * mieści się ona w zadanym zakresie, czyli <0, 50>.
     */
    public void setPrzesuniecieZnakowe(int wart)
    {
        //jeśli parametr jest z zakresu od 0 do 50..
        if (wart>-1 && wart<51)
        {
            //zmien watrosc przesuniecia znakowego
            this.przesuniecie = wart;
        }
    }//

    //koniec setterów

    //gettery
    /*
     * Zwraca klucz postawowy.
     */
    public String getKluczProsty(){return this.klucz1;}
    /*
     * Zwraca wartość przesunięcia znakowego.
     */
    public int getPrzesuniecieZnakowe(){return this.przesuniecie;}

    //koniec getterów
}//koniec SzyfrRzeznika