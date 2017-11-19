package com.komunikatorinternetowy.Content;

/**
 * Keeps array of all known Strings and Message Patterns used in application.
 */
public class Strings {
    public final static String WP_STATE_AWAITING = "Czeka";
    public final static String WP_STATE_SAVING = "Zapis";
    public final static String WP_STATE_TURNED_OFF = "Wył.";
    public final static String WP_HINT_BUTTON_SETTINGS = "Po kliknięciu pokazuje okno ustawień zapisu do logu.";
    public final static String WP_LABEL_SAVE_TO_LOG = "zapisuj do logu";
    public final static String WP_LABEL_LOG_FILE_PATH = "Ścieżka do pliku logu:";
    public final static String WP_LABEL_LOG_FILE_NAME = "Nazwa pliku logu:";
    public final static String WP_TITLE_LOG_SAVING_PARAMS = "Parametry zapisu logu";
    public final static String WP_TITLE_SUCCESS = "Sukces";
    public final static String WP_TITLE_ERROR = "Błąd";
    public final static String WP_MSG_CHANGED_FILE_PATH_TO = "Zmieniono adres pliku logu na ";
    public final static String WP_MSG_CHANGED_ERROR = "Błędnie wprowadzone dane.";

    public final static String SV_CONSOLE_MSG_READ = "Odczytano {0}: ";
    public final static String SV_CONSOLE_MSG_CONNECTED = "połaczono";

    public final static String CPR_HINT_BUTTON_SETTINGS = new StringBuffer("Po kliknięciu zmienia ")
            .append("parametry szyfrowania.\n\r\n")
            .append(" W - wolny, oznacza, że jest gotowy do pracy.\r")
            .append(" Z - zajęty, oznacza, że teraz pracuje.").toString();
    public final static String CPR_LABEL_SIMPLE_KEY = "Klucz prosty (tekstowy):";
    public final static String CPR_LABEL_BIAS = "Przesunięcie znakowe";
    public final static String CPR_TITLE_CIPHER_PARAMS = "Parametry szyfrowania";
    public final static String CPR_TITLE_BIAS_ERROR = "Błąd przesunięcia";
    public final static String CPR_MSG_BIAS_ERROR = new StringBuffer()
            .append("Błędna wartość w wprowadzonym przesunięciu.")
            .append("\n\r\n\rNie wprowadzono zmian.").toString();
    public final static String CPR_TITLE_CHANGES = "Zmiany w parametrach.";
    public final static String CPR_MSG_CHANGES = new StringBuffer()
            .append("Poprawnie wprowadzono zmiany.\n\r\n\r")
            .append("Klucz prosty: {0}\n\rPrzesunięcie: {1}").toString();
    public final static String CPR_CAPTION_WAITING = "W";
    public final static String CPR_CAPTION_SAVING = "Z";

    //TODO Add two languages and translations here.
}
