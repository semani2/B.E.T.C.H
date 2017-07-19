package sai.application.betch.alerts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import io.reactivex.subjects.PublishSubject;
import sai.application.betch.R;

/**
 * Created by sai on 7/18/17.
 */

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertItemViewHolder> {

    private Context mContext;

    private List<AlertsViewModel> mData;

    private final PublishSubject<AlertsViewModel> onCheckedSubject = PublishSubject.create();

    public AlertAdapter(Context context, List<AlertsViewModel> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public AlertItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_list_item, parent, false);
        return new AlertItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AlertItemViewHolder holder, int position) {
        final AlertsViewModel alertViewModel = mData.get(position);

        holder.alertTitleTextView.setText(alertViewModel.getAlertTitle());
        holder.alertMessageTextView.setText(alertViewModel.getAlertCreatedDate());
        holder.alertActiveSwitch.setChecked(alertViewModel.isAlertActive());

        holder.alertActiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                AlertsViewModel alertsViewModel = mData.get(holder.getAdapterPosition());
                alertsViewModel.setIsActive(isChecked);
                onCheckedSubject.onNext(alertsViewModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class AlertItemViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout itemLayout;
        public TextView alertTitleTextView;
        public TextView alertMessageTextView;
        public Switch alertActiveSwitch;

        public AlertItemViewHolder(View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.alertItemLayout);
            alertTitleTextView = itemView.findViewById(R.id.alertTitleTextView);
            alertMessageTextView = itemView.findViewById(R.id.alertDateTextView);
            alertActiveSwitch = itemView.findViewById(R.id.alertActiveSwitch);
        }
    }
}
