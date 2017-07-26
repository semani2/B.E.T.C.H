package sai.application.betch.alerts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import sai.application.betch.R;

/**
 * Created by sai on 7/18/17.
 */

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.AlertItemViewHolder> {

    private Context mContext;

    private List<AlertsViewModel> mData;

    private final PublishSubject<AlertsViewModel> onCheckedSubject = PublishSubject.create();

    private final PublishSubject<AlertsViewModel> onLongPressSubject = PublishSubject.create();

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

        List<String> titleData = alertViewModel.getAlertTitle();
        holder.alertTitleTextView.setText(alertViewModel.isTimeTrigger() ? mContext.getString(R.string.str_time_alert_title, titleData.get(0), titleData.get(1)) :
                mContext.getString(R.string.str_price_alert_title, titleData.get(0), titleData.get(1), titleData.get(2)));
        holder.alertMessageTextView.setText(mContext.getString(R.string.str_created_on, alertViewModel.getAlertCreatedDate()));
        holder.alertActiveSwitch.setChecked(alertViewModel.isAlertActive());
        if(alertViewModel.isTimeTrigger()) {
            holder.alertImageView.setImageDrawable(mContext.getDrawable(R.drawable.ic_access_alarm));
        }
        else {
            holder.alertImageView.setImageDrawable(alertViewModel.isLessThan() ? mContext.getDrawable(R.drawable.ic_trending_down) :
                    mContext.getDrawable(R.drawable.ic_trending_up));
        }

        holder.alertActiveSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                AlertsViewModel alertsViewModel = mData.get(holder.getAdapterPosition());
                alertsViewModel.setIsActive(isChecked);
                onCheckedSubject.onNext(alertsViewModel);
            }
        });

        holder.itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertsViewModel alertsViewModel = mData.get(holder.getAdapterPosition());
                onLongPressSubject.onNext(alertsViewModel);
                return true;
            }
        });
    }

    public Observable<AlertsViewModel> getAlertSwitchToggle() {
        return onCheckedSubject;
    }

    public Observable<AlertsViewModel> getLongPress() {
        return onLongPressSubject;
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
        public ImageView alertImageView;

        public AlertItemViewHolder(View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.alertItemLayout);
            alertTitleTextView = itemView.findViewById(R.id.alertTitleTextView);
            alertMessageTextView = itemView.findViewById(R.id.alertDateTextView);
            alertActiveSwitch = itemView.findViewById(R.id.alertActiveSwitch);
            alertImageView = itemView.findViewById(R.id.alert_image_view);
        }
    }
}
