package tnkwong.cs190i.cs.ucsb.edu.colormind;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelpDialogFragment extends DialogFragment {


    public HelpDialogFragment() {
        // Required empty public constructor
    }

    static HelpDialogFragment newInstance() {
        HelpDialogFragment f = new HelpDialogFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_help_dialog, container, false);
        Button closeButton = (Button) v.findViewById(R.id.closeButton);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

}
