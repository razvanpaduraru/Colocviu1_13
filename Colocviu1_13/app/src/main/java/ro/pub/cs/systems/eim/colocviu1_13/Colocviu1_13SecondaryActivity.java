package ro.pub.cs.systems.eim.colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Colocviu1_13SecondaryActivity extends AppCompatActivity {
    private TextView text;
    private Button registerButton, cancelButton;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.register_button:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_secondary);

        text = (TextView) findViewById(R.id.pressed);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("numar_apasari")) {
            Integer number_of_presses = intent.getIntExtra("numar_apasari", -1);
            text.setText(number_of_presses.toString());
            Toast.makeText(this, "Succes!", Toast.LENGTH_LONG).show();
        } else if (intent != null && intent.getExtras().containsKey("initializare")) {
            Integer initializare = intent.getIntExtra("initializare", -1);
            text.setText(initializare.toString());
            Toast.makeText(this, "Succes!", Toast.LENGTH_LONG).show();
            setResult(RESULT_FIRST_USER, null);
            finish();
        }

        registerButton = (Button)findViewById(R.id.register_button);
        registerButton.setOnClickListener(buttonClickListener);
        cancelButton = (Button)findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);
    }
}
