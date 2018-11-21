package com.metropolitan.kriptokonvertor;

public class Valute {
    public static String[] drzava = {"Srbija","Bosna i Hercegovina","Nigerija","Sjedinjene Američke Države","Ujedinjeno Kraljevstvo",
            "Gana","Nemačka","Kongo","Kanada","Avganistan","Albanija","Kolumbija","Egipat","Danska",
            "Južna Koreja","Južna Afrika","Saudijska Arabija","Japan","Argentina","Brazil",
            "Kina","Hong Kong"};

    public static String[] kod = {"RSD","BAM","NGN","USD","GBP","GHS","EUR","XAF","CAD","AFN","ALL",
            "COP","EGP","DKK","KRW","ZAR","SAR","JPY","ARS","BRL","CNY","HKD"};

    //Proverava da li ima valuta
    public static boolean[] proveriValute() {
        boolean[] proveri = new boolean[22];
        proveri[0] = true;
        return proveri;
    }
}
