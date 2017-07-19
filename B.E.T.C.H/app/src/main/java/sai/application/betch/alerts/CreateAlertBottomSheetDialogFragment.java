package sai.application.betch.alerts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import sai.application.betch.R;

/**
 * Created by sai on 7/19/17.
 */

public class CreateAlertBottomSheetDialogFragment extends BottomSheetDialogFragment {

    public static CreateAlertBottomSheetDialogFragment getInstance() {
        return new CreateAlertBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.new_alert_bottom_sheet, container, false);

        ButterKnife.bind(view);

        return view;
    }


}
