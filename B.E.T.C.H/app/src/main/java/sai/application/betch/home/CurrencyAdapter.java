package sai.application.betch.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import sai.application.betch.R;

/**
 * Created by sai on 7/17/17.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyItemViewHolder> {

    @Inject
    Context context;

    private List<CurrencyViewModel> data;

    public CurrencyAdapter(List<CurrencyViewModel> currencyViewModels) {
        this.data = currencyViewModels;
    }

    @Override
    public CurrencyItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_list_item, parent, false);
        return new CurrencyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CurrencyItemViewHolder holder, int position) {
        holder.fiatCurrencyTextView.setText(data.get(position).getFiatCurrency());
        holder.costPerUnitTextView.setText(String.valueOf(data.get(position).getCostPerUnit()));
        holder.statusImageView.setImageDrawable(data.get(position).isGoingUp() ? context.getDrawable(R.drawable.arrow_up_bold) :
                context.getDrawable(R.drawable.arrow_down_bold));
        holder.currencyNameTextView.setText(data.get(position).getCurrencyName());
        holder.currencySymbolTextView.setText(data.get(position).getCurrencySymbol());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class CurrencyItemViewHolder extends RecyclerView.ViewHolder {

        public TextView fiatCurrencyTextView;
        public TextView costPerUnitTextView;
        public TextView currencyNameTextView;
        public TextView currencySymbolTextView;
        public ImageView statusImageView;

        public CurrencyItemViewHolder(View itemView) {
            super(itemView);

            fiatCurrencyTextView = itemView.findViewById(R.id.fiatCurrencyTextView);
            costPerUnitTextView = itemView.findViewById(R.id.costPerUnitTextView);
            currencyNameTextView = itemView.findViewById(R.id.currencyNameTextView);
            currencySymbolTextView = itemView.findViewById(R.id.currencySymbolTextView);
            statusImageView = itemView.findViewById(R.id.statusImageView);
        }
    }
}
