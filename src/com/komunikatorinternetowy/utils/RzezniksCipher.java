package com.komunikatorinternetowy.utils;

/**
 * Ciphering engine of component.
 *
 * It is performed with Rzeźnik's Cipher.
 * <p>
 * Ciphering method Rzeźnik's Cipher:
 * Lz = Lp + nz + int(plk), gdzie
 * <p>
 * Lz - Value of coded character
 * Lp - Beginning value of character
 * nz - index of character in message to cipher
 * plk - obtained Unicode code from simple key, after which index value
 * is shorten until it will be from range of simple key's length.
 *
 * @author Tomasz "Rzeźnik" Trzciński
 */
public class RzezniksCipher {
    /**
     * Default value of key one.
     */
    private final static String DEFAULT_KEY_ONE = "FajnoowyKluczeekEmoooMaartyynki";
    /**
     * Default value of bias.
     */
    private final static int DEFAULT_BIAS = 1;

    /**
     * Simple key used as a base ciphering.
     */
    private String simpleKey = DEFAULT_KEY_ONE;
    /**
     * Number of alphabetical change in char in Unicode.
     */
    private int bias = DEFAULT_BIAS;

    /**
     * Creates new instance of Rzeźnik's Cipherer with given characters.
     *
     * @param simpleKey given simple cipher key
     * @param bias      given bias in Unicode
     */
    RzezniksCipher(String simpleKey, int bias) {
        this.simpleKey = simpleKey;
        this.bias = bias;
    }

    /**
     * Obtains character with given number from simple key.
     *
     * @param nr number of char
     * @return character from simple key
     */
    public char obtainLetterOfSimpleKey(int nr) {
        //Until index of letter is out of range of simple's key length
        while (nr > this.simpleKey.length() - 1) {
            //Decrease index for a length of simple key
            nr -= this.simpleKey.length() - 1;
        }
        //Return letter from simple key
        return this.simpleKey.charAt(nr);
    }

    /**
     * Ciphers given phrase.
     *
     * @param phrase given phrase.
     * @return ciphered phrase
     */
    public String cipher(String phrase) {
        //Array of characters to count their new values in unicode
        char[] chars = new char[phrase.length()];
        //Keeps value, for how much character show be changed
        int countedBias = 0;
        int unicodeValue = 0;
        for (int i = 0; i < phrase.length(); i++) {
            //Count bias value in Unicode
            countedBias = i + this.bias + (int) this.obtainLetterOfSimpleKey(i);
            //Move char's index in Unicode for a counted value
            unicodeValue = (int) phrase.charAt(i) + countedBias;
            //Add counted unicode char to array at the end
            chars[i] = (char) unicodeValue;
        }
        //Convert value of counted characters to String
        return String.copyValueOf(chars);
    }

    /**
     * Deciphers given phrase.
     *
     * @param cipheredPhrase given phrase
     * @return deciphered message
     */
    public String decipher(String cipheredPhrase) {
        //Table of counted letters to return
        char[] effect = new char[cipheredPhrase.length()];
        //Iterate letter by letter given phrase
        for (int i = 0; i < cipheredPhrase.length(); i++) {
            //Decipher value of letter through reading its Unicode value
            //and reducing its index, bias and connected to it letter from simple key
            //after which it is overwritten in table with index i
            effect[i] = (char) (
                    (int) cipheredPhrase.charAt(i) - i - this.bias - (int) this.obtainLetterOfSimpleKey(i)
            );
        }
        //Convert value of counted characters to String
        return String.copyValueOf(effect);
    }

    /**
     * Sets simple key.
     *
     * @param key given key
     */
    public void setSimpleKey(String key) {
        this.simpleKey = key;
    }

    /**
     * Sets bias to given value, if it is withing simple's key length.
     *
     * @param bias given value
     */
    public void setBias(int bias) {
        //If param in withing range of simple key length
        if (bias > -1 && bias < this.DEFAULT_KEY_ONE.length()) {
            this.bias = bias;
        }
    }

    /**
     * Obtains value of kept simple key.
     *
     * @return simple key
     */
    public String getSimpleKey() {
        return this.simpleKey;
    }

    /**
     * Obtains value of bias.
     *
     * @return current bias value
     */
    public int getBias() {
        return this.bias;
    }
}