package tnkwong.cs190i.cs.ucsb.edu.colormind;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Timothy Kwong on 5/11/2017.
 */

public class MainMenuActivity extends AppCompatActivity {
    private final int REQUEST_ENABLE_BT = 1010;
    private Button playButton;
    private Button mmhelpButton;
    private Button settingButton;
    private Button btButton;
    private Button scoresButton;
    private int[] settingCode = {0, 5, 10}; //timed mode, boxes, attempts

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainmenu);

        Bundle b = getIntent().getExtras();
        if (b != null)
            settingCode = b.getIntArray("settings");
        playButton = (Button) findViewById(R.id.playbutton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
            }
        });

        mmhelpButton = (Button) findViewById(R.id.mmhelpButton);
        mmhelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                HelpDialogFragment hdf = HelpDialogFragment.newInstance();
                hdf.show(ft, "Help");
            }
        });

        btButton = (Button) findViewById(R.id.btplayButton);
        btButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btPlay();
            }
        });

        settingButton = (Button) findViewById(R.id.settingbutton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

    }

    private void startGame() {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("settings", settingCode);
        i.putExtra("bluetooth", false);
        startActivity(i);
    }

    private void openSettings() {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("settings", settingCode);
        startActivity(i);
    }

    private void btPlay() {
        Intent i = new Intent(this, BluetoothActivity.class);
        startActivity(i);
    }
}

