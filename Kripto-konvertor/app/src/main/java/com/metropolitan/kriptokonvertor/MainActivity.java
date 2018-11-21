package com.metropolitan.kriptokonvertor;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProgressDialog pgDijalog;
    List<Model> kursevi = new ArrayList<>();
    List<Model> iniKurs = new ArrayList<>();
    private RecyclerView rv;
    private RVAdapter rvAdapter;
    private FloatingActionButton fab;
    boolean provereno[] = Valute.proveriValute();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.recycler);
        fab = findViewById(R.id.fab);
        pocetniPrikaz();
    }

    //Pravi listu valuta za dodavanje kartica
    private void dodajKarticu(){
        String[] lista = Valute.kod;
        AlertDialog ad = new AlertDialog.Builder(this)
                .setTitle(R.string.dialogMessage)
                .setMultiChoiceItems(lista, provereno, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        if (b){
                            provereno[i] = true;
                            iniKurs.add(kursevi.get(i));
                            rvAdapter.notifyItemInserted(rvAdapter.getItemCount());
                        }else {
                            provereno[i] = false;
                            int index = iniKurs.indexOf(kursevi.get(i));
                            iniKurs.remove(index);
                            rvAdapter.notifyItemRemoved(index);
                        }
                    }

                }).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        ad.show();
    }

    private void pocetniPrikaz(){
        pgDijalog = new ProgressDialog(this);
        pgDijalog.setMessage("Preuzimanje podataka");
        pgDijalog.setCancelable(false);
        pgDijalog.show();
        rv.setLayoutManager(new LinearLayoutManager(this));;
        rv.smoothScrollToPosition(0);
        //Proverava da li je konekcija dostupna
        if (isKonekcijaDostupna(this)){
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dodajKarticu();
                }
            });
            getResponse();
        }else {
            pgDijalog.dismiss();
            //Notifikacija ukoliko internet nije dostupan
            setSnackBar("Uključite internet konekciju");
        }
    }

    private void getResponse(){
        Klijent kl = new Klijent();
        Servis servis = kl.getKlijent().create(Servis.class);
        Call<JsonObject> call = servis.getKurs();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                fab.setVisibility(View.VISIBLE);
                pgDijalog.dismiss();
                JsonObject jsonObject = response.body();
                //Preuzima kurs na osnovu model klase u kojoj se nalaze kodovi valuta
                for (int i=0;i<22;i++){
                    kursevi.add(new Model(Valute.drzava[i],Valute.kod[i],
                            jsonObject.get("BTC").getAsJsonObject().get(Valute.kod[i]).getAsDouble(),
                            jsonObject.get("ETH").getAsJsonObject().get(Valute.kod[i]).getAsDouble()));
                }
                iniKurs.add(kursevi.get(0));
                rvAdapter = new RVAdapter(iniKurs);
                rv.setAdapter(rvAdapter);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                pgDijalog.dismiss();
                setSnackBar("Internet konekcija je loša");
            }
        });
    }

    private void setSnackBar(String message){
        Snackbar snackbar = Snackbar.make(findViewById(R.id.LinearLayout), message, Snackbar.LENGTH_INDEFINITE)
                .setAction("Osveži", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pocetniPrikaz();
                    }
                }); snackbar.show();

    }

    //Proverava da li je konekcija dostupna
    public static boolean isKonekcijaDostupna(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    //Dodaje ActionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //Poziva veb prikaz
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.stock:
                Intent intent = new Intent(this, WebPrikaz.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
