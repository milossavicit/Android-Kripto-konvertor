package com.metropolitan.kriptokonvertor;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.viewHolder> {
    private List<Model> nazivi;

    public RVAdapter(List<Model> nazivi) {
        this.nazivi = nazivi;
    }

    @Override
    public RVAdapter.viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.kartica,parent,false);
        return new viewHolder(v);
    }

    //Popunjava podatke u kartici
    @Override
    public void onBindViewHolder(RVAdapter.viewHolder holder, int pozicija) {
        holder.drzava.setText(nazivi.get(pozicija).getDrzava());
        holder.kod.setText(nazivi.get(pozicija).getKod());
        holder.btc.setText(String.valueOf(nazivi.get(pozicija).getBtc()));
        holder.eth.setText(String.valueOf(nazivi.get(pozicija).getEth()));
    }

    @Override
    public int getItemCount() {
        return nazivi.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        private TextView kod, eth, drzava, btc;

        public viewHolder(View v) {
            super(v);
            kod = v.findViewById(R.id.kod);
            drzava = v.findViewById(R.id.drzava);
            btc = v.findViewById(R.id.btcvrednost);
            eth = v.findViewById(R.id.ethvrednost);


            //Klik na karticu ; poziv konverzije
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pozicija = getAdapterPosition();
                    if (pozicija!=RecyclerView.NO_POSITION){
                        Intent intent = new Intent(view.getContext(), Konverzija.class);
                        intent.putExtra("btc", nazivi.get(pozicija).getBtc())
                                .putExtra("drzava", nazivi.get(pozicija).getDrzava())
                                .putExtra("kod", nazivi.get(pozicija).getKod())
                                .putExtra("eth", nazivi.get(pozicija).getEth());
                        view.getContext().startActivity(intent);
                    }
                }
            });
        }
    }
}
