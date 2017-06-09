package tnkwong.cs190i.cs.ucsb.edu.colormind;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private CheckedTextView tminctv;
    private CheckedTextView fminctv;
    private CheckedTextView eminctv;
    private CheckedTextView uminctv;

    private CheckBox fopegcb;
    private CheckBox fipegcb;

    private CheckBox tattcb;
    private CheckBox eattcb;
    private CheckBox sattcb;

    private Button applyButton;

    private int[] settingsCode = {0, 5, 10};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Bundle b = getIntent().getExtras();
        if (b != null)
            settingsCode = b.getIntArray("settings");
        fopegcb = (CheckBox) findViewById(R.id.fourpegsctv);
        fopegcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    settingsCode[1] = 4;
                    Toast.makeText(SettingsActivity.this, "Reducing peg number to 4!", Toast.LENGTH_SHORT).show();
                } else {
                    settingsCode[1] = 5;
                    Toast.makeText(SettingsActivity.this, "Increasing peg to number to 5!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sattcb = (CheckBox) findViewById(R.id.sixattemptctv);
        sattcb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();
                if (checked) {
                    settingsCode[2] = 6;
                    Toast.makeText(SettingsActivity.this, "Reducing attempts to solve to 6!", Toast.LENGTH_SHORT).show();
                } else {
                    settingsCode[2] = 10;
                    Toast.makeText(SettingsActivity.this, "Increasing attempts to solve to 10!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(settingsCode[1] == 4)
            fopegcb.setChecked(true);

        if(settingsCode[2] == 6)
            sattcb.setChecked(true);

        applyButton = (Button) findViewById(R.id.applyButton);
        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });
    }

    public void backToMain(){
        Intent i = new Intent(this, MainMenuActivity.class);
        i.putExtra("settings", settingsCode);
        startActivity(i);
    }
}
