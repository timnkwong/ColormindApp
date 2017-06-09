package tnkwong.cs190i.cs.ucsb.edu.colormind;


import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ButtonsFragment extends Fragment {
    private ImageButton LLButton;
    private ImageButton LRButton;
    private ImageButton CButton;
    private ImageButton RLButton;
    private ImageButton RRButton;
    private boolean toggle = false;

    private ConstraintLayout scoreLayout;

    private int LLcol = 0;
    private int LRcol = 0;
    private int Ccol = 0;
    private int RLcol = 0;
    private int RRcol = 0;

    private ImageView check1;
    private ImageView check2;
    private ImageView check3;
    private ImageView check4;
    private ImageView check5;

    private ConstraintLayout containerLayout;

    private Button coverButton;

    public ButtonsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_buttons, container, false);
        //scoreLayout = (ConstraintLayout) v.findViewById(R.id.scoreLayout);
        containerLayout = (ConstraintLayout) v.findViewById(R.id.containerLayout);
        LLButton = (ImageButton) v.findViewById(R.id.LLbutton);
        LRButton = (ImageButton) v.findViewById(R.id.LRbutton);
        CButton = (ImageButton) v.findViewById(R.id.Cbutton);
        RLButton = (ImageButton) v.findViewById(R.id.RLbutton);
        RRButton = (ImageButton) v.findViewById(R.id.RRbutton);

        coverButton = (Button) v.findViewById(R.id.coverButton);

        LLButton.setOnClickListener(null);
        LRButton.setOnClickListener(null);
        CButton.setOnClickListener(null);
        RLButton.setOnClickListener(null);
        RRButton.setOnClickListener(null);

        check1 = (ImageView) v.findViewById(R.id.check1);
        check1.setVisibility(View.INVISIBLE);
        check1.setColorFilter(Color.BLUE);

        check2 = (ImageView) v.findViewById(R.id.check2);
        check2.setVisibility(View.INVISIBLE);
        check2.setColorFilter(Color.BLUE);

        check3 = (ImageView) v.findViewById(R.id.check3);
        check3.setVisibility(View.INVISIBLE);
        check3.setColorFilter(Color.BLUE);

        check4 = (ImageView) v.findViewById(R.id.check4);
        check4.setVisibility(View.INVISIBLE);
        check4.setColorFilter(Color.BLUE);

        check5 = (ImageView) v.findViewById(R.id.check5);
        check5.setVisibility(View.INVISIBLE);
        check5.setColorFilter(Color.BLUE);


        LLButton.setColorFilter(Color.BLACK);
        LRButton.setColorFilter(Color.BLACK);
        CButton.setColorFilter(Color.BLACK);
        RLButton.setColorFilter(Color.BLACK);
        RRButton.setColorFilter(Color.BLACK);
        scoreLayout = (ConstraintLayout) v.findViewById(R.id.scoreLayout);
        containerLayout.setVisibility(View.INVISIBLE);

        return v;
    }


    public void enable() {
        Log.i("DEBUG", "ENABLING TOGGLES");
        containerLayout.setVisibility(View.VISIBLE);
        containerLayout.setBackgroundColor(Color.YELLOW);
        coverButton.setVisibility(View.GONE);
        LLButton.setColorFilter(Color.RED);
        LRButton.setColorFilter(Color.RED);
        CButton.setColorFilter(Color.RED);
        RLButton.setColorFilter(Color.RED);
        RRButton.setColorFilter(Color.RED);
        LLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {
                    switch (LLcol) {
                        case 0:
                            Log.i("DEBUG", "CHANGING COLOR TO ORANGE");
                            LLButton.setColorFilter(Color.rgb(255, 153, 0));
                            LLcol = 1;
                            break;
                        case 1:
                            Log.i("DEBUG", "CHANGING COLOR TO YELLOW");
                            LLButton.setColorFilter(Color.YELLOW);
                            LLcol = 2;
                            break;
                        case 2:
                            Log.i("DEBUG", "CHANGING COLOR TO GREEN");
                            LLButton.setColorFilter(Color.GREEN);
                            LLcol = 3;
                            break;
                        case 3:
                            Log.i("DEBUG", "CHANGING COLOR TO BLUE");
                            LLButton.setColorFilter(Color.BLUE);
                            LLcol = 4;
                            break;
                        case 4:
                            Log.i("DEBUG", "CHANGING COLOR TO MAGENTA");
                            LLButton.setColorFilter(Color.MAGENTA);
                            LLcol = 5;
                            break;
                        case 5:
                            Log.i("DEBUG", "CHANGING COLOR TO RED");
                            LLButton.setColorFilter(Color.RED);
                            LLcol = 0;
                            break;
                    }
                }
            }
        });
        LRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {
                    switch (LRcol) {
                        case 0:
                            Log.i("DEBUG", "CHANGING COLOR TO ORANGE");
                            LRButton.setColorFilter(Color.rgb(255, 153, 0));
                            LRcol = 1;
                            break;
                        case 1:
                            Log.i("DEBUG", "CHANGING COLOR TO YELLOW");
                            LRButton.setColorFilter(Color.YELLOW);
                            LRcol = 2;
                            break;
                        case 2:
                            Log.i("DEBUG", "CHANGING COLOR TO GREEN");
                            LRButton.setColorFilter(Color.GREEN);
                            LRcol = 3;
                            break;
                        case 3:
                            Log.i("DEBUG", "CHANGING COLOR TO BLUE");
                            LRButton.setColorFilter(Color.BLUE);
                            LRcol = 4;
                            break;
                        case 4:
                            Log.i("DEBUG", "CHANGING COLOR TO MAGENTA");
                            LRButton.setColorFilter(Color.MAGENTA);
                            LRcol = 5;
                            break;
                        case 5:
                            Log.i("DEBUG", "CHANGING COLOR TO RED");
                            LRButton.setColorFilter(Color.RED);
                            LRcol = 0;
                            break;
                    }
                }
            }
        });
        CButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {
                    switch (Ccol) {
                        case 0:
                            Log.i("DEBUG", "CHANGING COLOR TO ORANGE");
                            CButton.setColorFilter(Color.rgb(255, 153, 0));
                            Ccol = 1;
                            break;
                        case 1:
                            Log.i("DEBUG", "CHANGING COLOR TO YELLOW");
                            CButton.setColorFilter(Color.YELLOW);
                            Ccol = 2;
                            break;
                        case 2:
                            Log.i("DEBUG", "CHANGING COLOR TO GREEN");
                            CButton.setColorFilter(Color.GREEN);
                            Ccol = 3;
                            break;
                        case 3:
                            Log.i("DEBUG", "CHANGING COLOR TO BLUE");
                            CButton.setColorFilter(Color.BLUE);
                            Ccol = 4;
                            break;
                        case 4:
                            Log.i("DEBUG", "CHANGING COLOR TO MAGENTA");
                            CButton.setColorFilter(Color.MAGENTA);
                            Ccol = 5;
                            break;
                        case 5:
                            Log.i("DEBUG", "CHANGING COLOR TO RED");
                            CButton.setColorFilter(Color.RED);
                            Ccol = 0;
                            break;
                    }
                }
            }
        });
        RLButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {
                    switch (RLcol) {
                        case 0:
                            Log.i("DEBUG", "CHANGING COLOR TO ORANGE");
                            RLButton.setColorFilter(Color.rgb(255, 153, 0));
                            RLcol = 1;
                            break;
                        case 1:
                            Log.i("DEBUG", "CHANGING COLOR TO YELLOW");
                            RLButton.setColorFilter(Color.YELLOW);
                            RLcol = 2;
                            break;
                        case 2:
                            Log.i("DEBUG", "CHANGING COLOR TO GREEN");
                            RLButton.setColorFilter(Color.GREEN);
                            RLcol = 3;
                            break;
                        case 3:
                            Log.i("DEBUG", "CHANGING COLOR TO BLUE");
                            RLButton.setColorFilter(Color.BLUE);
                            RLcol = 4;
                            break;
                        case 4:
                            Log.i("DEBUG", "CHANGING COLOR TO MAGENTA");
                            RLButton.setColorFilter(Color.MAGENTA);
                            RLcol = 5;
                            break;
                        case 5:
                            Log.i("DEBUG", "CHANGING COLOR TO RED");
                            RLButton.setColorFilter(Color.RED);
                            RLcol = 0;
                            break;
                    }
                }
            }
        });
        RRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggle) {
                    Log.i("DEBUG", "CHANGING COLORS");
                    switch (RRcol) {
                        case 0:
                            Log.i("DEBUG", "CHANGING COLOR TO ORANGE");
                            RRButton.setColorFilter(Color.rgb(255, 153, 0));
                            RRcol = 1;
                            break;
                        case 1:
                            Log.i("DEBUG", "CHANGING COLOR TO YELLOW");
                            RRButton.setColorFilter(Color.YELLOW);
                            RRcol = 2;
                            break;
                        case 2:
                            Log.i("DEBUG", "CHANGING COLOR TO GREEN");
                            RRButton.setColorFilter(Color.GREEN);
                            RRcol = 3;
                            break;
                        case 3:
                            Log.i("DEBUG", "CHANGING COLOR TO BLUE");
                            RRButton.setColorFilter(Color.BLUE);
                            RRcol = 4;
                            break;
                        case 4:
                            Log.i("DEBUG", "CHANGING COLOR TO MAGENTA");
                            RRButton.setColorFilter(Color.MAGENTA);
                            RRcol = 5;
                            break;
                        case 5:
                            Log.i("DEBUG", "CHANGING COLOR TO RED");
                            RRButton.setColorFilter(Color.RED);
                            RRcol = 0;
                            break;
                    }
                }
            }
        });
        toggle = !toggle;
    }

    public void disable() {
        containerLayout.setBackgroundColor(Color.RED);
        toggle = !toggle;
    }

    public ArrayList<Integer> getColorVals(int mode) {
        ArrayList<Integer> vals = new ArrayList<>(mode);
        vals.add(0, LLcol);
        vals.add(1, LRcol);
        vals.add(2, Ccol);
        vals.add(3, RLcol);
        if(mode == 5)
            vals.add(4, RRcol);
        return vals;
    }

    public boolean checkResults(int contains, int matches, int mode) {
        int incre = 1;
        for (int i = 0; i < matches; i++) {
            setCheck(incre, 1, mode);
            incre++;
        }
        for (int j = 0; j < contains; j++) {
            setCheck(incre, 0, mode);
            incre++;
        }
        if (matches == 5 && mode == 5) {
            containerLayout.setBackgroundColor(Color.GREEN);
            return true;
        }

        if (matches == 4 && mode == 4) {
            containerLayout.setBackgroundColor(Color.GREEN);
            return true;
        }
        return false;
    }

    public void setCheck(int checkNum, int state, int mode) {
        check1.setVisibility(View.VISIBLE);
        check2.setVisibility(View.VISIBLE);
        check3.setVisibility(View.VISIBLE);
        check4.setVisibility(View.VISIBLE);
        if(mode == 5)
            check5.setVisibility(View.VISIBLE);
        switch (checkNum) {
            case 1:
                if (state == 1)
                    check1.setColorFilter(Color.BLACK);
                else
                    check1.setColorFilter(Color.RED);
                break;
            case 2:
                if (state == 1)
                    check2.setColorFilter(Color.BLACK);
                else
                    check2.setColorFilter(Color.RED);
                break;
            case 3:
                if (state == 1)
                    check3.setColorFilter(Color.BLACK);
                else
                    check3.setColorFilter(Color.RED);
                break;
            case 4:
                if (state == 1)
                    check4.setColorFilter(Color.BLACK);
                else
                    check4.setColorFilter(Color.RED);
                break;
            case 5:
                if (state == 1)
                    check5.setColorFilter(Color.BLACK);
                else
                    check5.setColorFilter(Color.RED);
                break;
        }
    }

    public void reveal(ArrayList<Integer> solution, int mode) {
        setColor(LLButton, solution.get(0));
        setColor(LRButton, solution.get(1));
        setColor(CButton, solution.get(2));
        setColor(RLButton, solution.get(3));
        if(mode == 5)
            setColor(RRButton, solution.get(4));
        coverButton.setVisibility(View.GONE);
        containerLayout.setVisibility(View.VISIBLE);
        /*LLButton.setVisibility(View.VISIBLE);
        LRButton.setVisibility(View.VISIBLE);
        CButton.setVisibility(View.VISIBLE);
        RLButton.setVisibility(View.VISIBLE);
        RRButton.setVisibility(View.VISIBLE);*/
    }

    public void setColor(ImageButton b, int col) {
        switch (col) {
            case 1:
                Log.i("DEBUG", "CHANGING COLOR TO ORANGE");
                b.setColorFilter(Color.rgb(255, 153, 0));
                break;
            case 2:
                Log.i("DEBUG", "CHANGING COLOR TO YELLOW");
                b.setColorFilter(Color.YELLOW);
                break;
            case 3:
                Log.i("DEBUG", "CHANGING COLOR TO GREEN");
                b.setColorFilter(Color.GREEN);
                break;
            case 4:
                Log.i("DEBUG", "CHANGING COLOR TO BLUE");
                b.setColorFilter(Color.BLUE);
                break;
            case 5:
                Log.i("DEBUG", "CHANGING COLOR TO MAGENTA");
                b.setColorFilter(Color.MAGENTA);
                break;
            case 0:
                Log.i("DEBUG", "CHANGING COLOR TO RED");
                b.setColorFilter(Color.RED);
                break;
        }
    }

    public void hideLayout() {
        scoreLayout.setVisibility(View.GONE);
        coverButton.setVisibility(View.GONE);
        containerLayout.setVisibility(View.GONE);
    }

    public void btLayout(){
        containerLayout.setVisibility(View.VISIBLE);
        scoreLayout.setVisibility(View.GONE);
        coverButton.setVisibility(View.GONE);
    }

    public void hideFifth(){
        RRButton.setVisibility(View.INVISIBLE);
        check5.setVisibility(View.INVISIBLE);
    }

    public void setCover(String s){
        coverButton.setText(s);
    }

    public void hideRow(){
        containerLayout.setVisibility(View.GONE);
        coverButton.setVisibility(View.GONE);
    }

}
