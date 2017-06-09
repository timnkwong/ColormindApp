package tnkwong.cs190i.cs.ucsb.edu.colormind;


import android.content.Intent;
import android.content.res.Configuration;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TimerTask;

import static tnkwong.cs190i.cs.ucsb.edu.colormind.R.id.enter_button;

public class GameActivity extends AppCompatActivity {
    private ButtonsFragment f1;
    private ButtonsFragment f2;
    private ButtonsFragment f3;
    private ButtonsFragment f4;
    private ButtonsFragment f5;
    private ButtonsFragment f6;
    private ButtonsFragment f7;
    private ButtonsFragment f8;
    private ButtonsFragment f9;
    private ButtonsFragment f10;
    private ButtonsFragment fsol;

    private Button quitButton;
    private Button restartButton;
    private Button helpButton;

    private Button menu_button;
    private Button guess_button;
    private boolean gameEnded = false;

    private ConstraintLayout header;
    private LinearLayout menuLayout;

    private FragmentManager fm;
    private ArrayList<Integer> solution;
    private int currentGameState = 1;
    private int[] settingsCode;

    private Bundle b;

    private ArrayList<Integer> btSolution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        b = getIntent().getExtras();
        if (b != null)
            settingsCode = b.getIntArray("settings");
        initFrags();
        generateSolution();
        guess_button = (Button) findViewById(enter_button);
        guess_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcResults();
            }
        });

  /*      if(settingsCode[0] > 0)
            setTimerOnSubmit();*/


        header = (ConstraintLayout) findViewById(R.id.headerLayout);
        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);

        menu_button = (Button) findViewById(R.id.menuButton);
        menu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOptions();
            }
        });

        quitButton = (Button) findViewById(R.id.quitbutton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeOptions();
                endgame(false);
            }
        });
        restartButton = (Button) findViewById(R.id.restartbutton);
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeOptions();
                recreate();
            }
        });
        helpButton = (Button) findViewById(R.id.homebutton); //NOW A HOME BUTTON
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenu();
            }
        });
    }

    public void mainMenu(){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("settings", settingsCode);
        startActivity(i);
    }

    public void initFrags() {
        fm = getSupportFragmentManager();
        f1 = (ButtonsFragment) fm.findFragmentById(R.id.fragment1);
        f2 = (ButtonsFragment) fm.findFragmentById(R.id.fragment2);
        f3 = (ButtonsFragment) fm.findFragmentById(R.id.fragment3);
        f4 = (ButtonsFragment) fm.findFragmentById(R.id.fragment4);
        f5 = (ButtonsFragment) fm.findFragmentById(R.id.fragment5);
        f6 = (ButtonsFragment) fm.findFragmentById(R.id.fragment6);
        f7 = (ButtonsFragment) fm.findFragmentById(R.id.fragment7);
        f8 = (ButtonsFragment) fm.findFragmentById(R.id.fragment8);
        f9 = (ButtonsFragment) fm.findFragmentById(R.id.fragment9);
        f10 = (ButtonsFragment) fm.findFragmentById(R.id.fragment10);
        fsol = (ButtonsFragment) fm.findFragmentById(R.id.solution);
        if(settingsCode[1] == 4){
            f1.hideFifth();
            f2.hideFifth();
            f3.hideFifth();
            f4.hideFifth();
            f5.hideFifth();
            f6.hideFifth();
            f7.hideFifth();
            f8.hideFifth();
            f9.hideFifth();
            f10.hideFifth();
            fsol.hideFifth();

        }
        f2.setCover("2");
        f3.setCover("3");
        f4.setCover("4");
        f5.setCover("5");
        f6.setCover("6");
        if(settingsCode[2] == 10) {
            f7.setCover("7");
            f8.setCover("8");
            f9.setCover("9");
            f10.setCover("10");
        }
        else {
            f7.hideRow();
            f8.hideRow();
            f9.hideRow();
            f10.hideRow();
        }
        f1.enable();
        fsol.hideLayout();
    }

    public void generateSolution() {
        if (b.getBoolean("bluetooth") == true){
            solution = b.getIntegerArrayList("bt");
        }
        else if(settingsCode[1] == 5) {
            solution = new ArrayList<>(5);
            for (int i = 0; i < 5; i++) {
                solution.add(i, (int) (Math.random() * 6));
                //solution.add(i, i%1);
            }
        }
        else {
            solution = new ArrayList<>(4);
            for (int i = 0; i < 4; i++) {
                solution.add(i, (int) (Math.random() * 6));
                //solution.add(i, i%1);
            }
        }
    }

    public void calcResults() {
        ArrayList<Integer> guessArray = new ArrayList<>(5);
        int[] results;
        boolean win = false;
        switch (currentGameState) {
            case 1:
                guessArray = f1.getColorVals(settingsCode[1]);
                results = checkEquivalence(guessArray);
                win = f1.checkResults(results[0], results[1], settingsCode[1]);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f1.disable();
                    f2.enable();
                }
                break;
            case 2:
                guessArray = f2.getColorVals(settingsCode[1]);
                results = checkEquivalence(guessArray);
                win = f2.checkResults(results[0], results[1], settingsCode[1]);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f2.disable();
                    f3.enable();
                }
                break;
            case 3:
                guessArray = f3.getColorVals(settingsCode[1]);
                results = checkEquivalence(guessArray);
                win = f3.checkResults(results[0], results[1], settingsCode[1]);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f3.disable();
                    f4.enable();
                }
                break;
            case 4:
                guessArray = f4.getColorVals(settingsCode[1]);
                results = checkEquivalence(guessArray);
                win = f4.checkResults(results[0], results[1], settingsCode[1]);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f4.disable();
                    f5.enable();
                }
                break;
            case 5:
                guessArray = f5.getColorVals(settingsCode[1]);
                results = checkEquivalence(guessArray);
                win = f5.checkResults(results[0], results[1], settingsCode[1]);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f5.disable();
                    f6.enable();
                }
                break;
            case 6:
                guessArray = f6.getColorVals(settingsCode[1]);
                results = checkEquivalence(guessArray);
                win = f6.checkResults(results[0], results[1], settingsCode[1]);
                if (win)
                    endgame(win);
                else {
                    if(settingsCode[2] != 6) {
                        currentGameState++;
                        f6.disable();
                        f7.enable();
                    }
                    else
                        endgame(false);
                }
                break;
            case 7:
                guessArray = f7.getColorVals(5);
                results = checkEquivalence(guessArray);
                win = f7.checkResults(results[0], results[1], 5);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f7.disable();
                    f8.enable();
                }
                break;
            case 8:
                guessArray = f8.getColorVals(5);
                results = checkEquivalence(guessArray);
                win = f8.checkResults(results[0], results[1], 5);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f8.disable();
                    f9.enable();
                }
                break;
            case 9:
                guessArray = f9.getColorVals(5);
                results = checkEquivalence(guessArray);
                win = f9.checkResults(results[0], results[1], 5);
                if (win)
                    endgame(win);
                else {
                    currentGameState++;
                    f9.disable();
                    f10.enable();
                }
                break;
            case 10:
                guessArray = f10.getColorVals(5);
                results = checkEquivalence(guessArray);
                win = f10.checkResults(results[0], results[1], 5);
                if (win)
                    endgame(win);
                else
                    endgame(false);
                break;
        }
    }

    public int[] checkEquivalence(ArrayList<Integer> guess) {
        int[] occurrences = new int[6], occurrencesGuess = new int[6];
        int contains = 0;
        int matches = 0;
        int[] results = new int[2];
        for (Integer i : guess) {
            occurrences[i] = Collections.frequency(solution, i);
            occurrencesGuess[i] = Collections.frequency(guess, i);
            if (occurrences[i] > occurrencesGuess[i])
                occurrences[i] = occurrencesGuess[i];
        }
        if(settingsCode[1] == 5) {
            for (int i = 0; i < 5; i++) {
                if (solution.get(i).equals(guess.get(i))) {
                    occurrences[i]--;
                    matches++;
                }
            }
        }
        else {
            for (int i = 0; i < 4; i++) {
                if (solution.get(i).equals(guess.get(i))) {
                    occurrences[i]--;
                    matches++;
                }
            }
        }
        for (int j : occurrences)
            contains += j;
        results[0] = contains;
        results[1] = matches;
        return results;
    }

    public void endgame(boolean win) {
        if (gameEnded) return;
        gameEnded = true;
        if (win)
            Toast.makeText(this, "Game won!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Game lost :(", Toast.LENGTH_SHORT).show();
        View b = findViewById(enter_button);
        b.setVisibility(View.GONE);
        fsol.reveal(solution, settingsCode[1]);
    }

    public void openOptions(){
        header.setVisibility(View.INVISIBLE);
        menuLayout.setVisibility(View.VISIBLE);
    }

    public void closeOptions(){
        header.setVisibility(View.VISIBLE);
        menuLayout.setVisibility(View.GONE);
    }

    public void openHelp(){
        FragmentTransaction ft = fm.beginTransaction();
        HelpDialogFragment hdf = HelpDialogFragment.newInstance();
        hdf.show(ft, "Help");
    }

    public void setTimerOnSubmit(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int seconds = settingsCode[0] * 60;
            @Override
            public void run() {
                guess_button.setText("Submit guess! Time left: " + Integer.toString(seconds) + " sec");
                seconds--;
            }
        }, 0, 1000);

    }

}


