package com.metropolitan.kriptokonvertor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;


public class Konverzija extends AppCompatActivity {
    EditText btcEditTekst, ethEditTekst, osnovaEditTekst;
    TextView kod, mKod, mBtc, mDrzava, mEth;
    DecimalFormat decimalniFormat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.konverzija);
        poveziPoglede();
        setEditTextChangedListeners();
        decimalniFormat = new DecimalFormat("#");
        //Max. br. celih brojeva
        decimalniFormat.setMinimumIntegerDigits(1);
        //Max. br. decimalnih brojeva
        decimalniFormat.setMaximumFractionDigits(4);
    }

    //Interfejs koji se koristi za nadgledanje teksta
    TextWatcher osnova = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int j, int k) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int j, int k) {

        }

        @Override
        public void afterTextChanged(Editable tekst) {
            ethEditTekst.removeTextChangedListener(eth);
            btcEditTekst.removeTextChangedListener(btc);
            String s = tekst.toString();
            if (isAValidNumber(s)){
                btcEditTekst.setText(decimalniFormat.format(osnovaUBtc(Double.parseDouble(s))));
                ethEditTekst.setText(decimalniFormat.format(osnovaUEth(Double.parseDouble(s))));
            }
            btcEditTekst.addTextChangedListener(btc);
            ethEditTekst.addTextChangedListener(eth);

        }
    };

    TextWatcher btc = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int j, int k) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int j, int k) {

        }

        @Override
        public void afterTextChanged(Editable tekst) {
            ethEditTekst.removeTextChangedListener(eth);
            osnovaEditTekst.removeTextChangedListener(osnova);
            String s = tekst.toString();
            if (isAValidNumber(s)){
                osnovaEditTekst.setText(decimalniFormat.format(btcUOsnova(Double.parseDouble(s))));
                ethEditTekst.setText(decimalniFormat.format(btcUEth(Double.parseDouble(s))));
            }
            osnovaEditTekst.addTextChangedListener(osnova);
            ethEditTekst.addTextChangedListener(eth);

        }
    };

    TextWatcher eth = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int j, int k) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int j, int k) {

        }

        @Override
        public void afterTextChanged(Editable tekst) {
            btcEditTekst.removeTextChangedListener(btc);
            osnovaEditTekst.removeTextChangedListener(osnova);
            String s = tekst.toString();
            if (isAValidNumber(s)){
                btcEditTekst.setText(decimalniFormat.format(ethUBtc(Double.parseDouble(s))));
                osnovaEditTekst.setText(decimalniFormat.format(ethUOsnova(Double.parseDouble(s))));
            }
            btcEditTekst.addTextChangedListener(btc);
            osnovaEditTekst.addTextChangedListener(osnova);
        }
    };

    void setEditTextChangedListeners() {
        osnovaEditTekst.addTextChangedListener(osnova);
        btcEditTekst.addTextChangedListener(btc);
        ethEditTekst.addTextChangedListener(eth);
    }

    private void poveziPoglede(){
        btcEditTekst = findViewById(R.id.btcTekst);
        ethEditTekst = findViewById(R.id.ethTekst);
        osnovaEditTekst = findViewById(R.id.osnovaEditTekst);
        kod = findViewById(R.id.kodKonverzija);
        mKod =findViewById(R.id.kod);
        mDrzava = findViewById(R.id.drzava);
        mBtc = findViewById(R.id.btcvrednost);
        mEth = findViewById(R.id.ethvrednost);
        mKod.setText(getIntent().getExtras().getString("kod"));
        mDrzava.setText(getIntent().getExtras().getString("drzava"));
        mBtc.setText(String.valueOf(getIntent().getExtras().getDouble("btc")));
        mEth.setText(String.valueOf(getIntent().getExtras().getDouble("eth")));
        kod.setText(getIntent().getExtras().getString("kod"));
    }

    double btcUOsnova(double btcVrednost) {
        return getIntent().getExtras().getDouble("btc") * btcVrednost;
    }

    double ethUOsnova(double ethVrednost) {
        return getIntent().getExtras().getDouble("eth") * ethVrednost;
    }

    double osnovaUBtc(double osnovnaValutaVrednost) {
        return osnovnaValutaVrednost / getIntent().getExtras().getDouble("btc");
    }

    double osnovaUEth(double osnovnaValutaVrednost) {
        return osnovnaValutaVrednost / getIntent().getExtras().getDouble("eth");
    }

    double ethUBtc(double ethVrednost) {
        return ethVrednost * getIntent().getExtras().getDouble("eth") / getIntent().getExtras().getDouble("btc");
    }

    double btcUEth(double btcVrednost) {
        return btcVrednost * getIntent().getExtras().getDouble("btc") / getIntent().getExtras().getDouble("eth");
    }

    public static  boolean isAValidNumber(String number){
        return !TextUtils.isEmpty(number) && !number.equalsIgnoreCase(".");
    }
}