package com.metropolitan.kriptokonvertor;

public class Model {
    private String drzava;
    private String kod;
    private double btc;
    private double eth;

    public Model(String drzava, String kod, double btc, double eth) {
        this.drzava = drzava;
        this.kod = kod;
        this.btc = btc;
        this.eth = eth;
    }

    public String getDrzava() {
        return drzava;
    }

    public String getKod() {
        return kod;
    }

    public double getBtc() {
        return btc;
    }

    public double getEth() {
        return eth;
    }
}
