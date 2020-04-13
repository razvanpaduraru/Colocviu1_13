package ro.pub.cs.systems.eim.colocviu1_13;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Colocviu1_13MainActivity extends AppCompatActivity {

    private String textDeAfisat = "";
    private Integer numarApasari = 0;
    private TextView text;
    private Button north;
    private Button east;
    private Button west;
    private Button south;
    private Button navigate;
    private Button initializare;

    private Integer serviceStatus = 0;

    private CardinalsClickListener cardinalsClickListener = new CardinalsClickListener();
    private NavigateButtonClickListener navigateButtonClickListener = new NavigateButtonClickListener();
    private InitializareButtonClickListener initializareButtonClickListener = new InitializareButtonClickListener();

    private IntentFilter intentFilter = new IntentFilter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colocviu1_13_main);

        text = (TextView)findViewById(R.id.pressed);

        north = (Button)findViewById(R.id.north);
        north.setOnClickListener(cardinalsClickListener);

        east = (Button)findViewById(R.id.east);
        east.setOnClickListener(cardinalsClickListener);

        west = (Button)findViewById(R.id.west);
        west.setOnClickListener(cardinalsClickListener);

        south = (Button)findViewById(R.id.south);
        south.setOnClickListener(cardinalsClickListener);

        navigate = (Button)findViewById(R.id.navigate);
        navigate.setOnClickListener(navigateButtonClickListener);

        initializare = (Button)findViewById(R.id.initializare);
        initializare.setOnClickListener(initializareButtonClickListener);

        intentFilter.addAction("threadText");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("text_puncte", text.getText().toString());
        savedInstanceState.putInt("numar_apasari", numarApasari);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("text_puncte")) {
            TextView tl = (TextView)findViewById(R.id.pressed);
            tl.setText(savedInstanceState.getString("text_puncte"));
        }
        if (savedInstanceState.containsKey("numar_apasari")) {
            numarApasari = savedInstanceState.getInt("numar_apasari");
        }
    }

    private class CardinalsClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            numarApasari += 1;
            if (v.getId() == R.id.north) {
                if (textDeAfisat.length() == 0) {
                    textDeAfisat = textDeAfisat + "North";
                } else {
                    textDeAfisat = textDeAfisat + ", North";
                }

                text.setText(textDeAfisat);
            } else if (v.getId() == R.id.east) {
                if (textDeAfisat.length() == 0) {
                    textDeAfisat = textDeAfisat + "East";
                } else {
                    textDeAfisat = textDeAfisat + ", East";
                }

                text.setText(textDeAfisat);
            } else if (v.getId() == R.id.west) {
                if (textDeAfisat.length() == 0) {
                    textDeAfisat = textDeAfisat + "West";
                } else {
                    textDeAfisat = textDeAfisat + ", West";
                }

                text.setText(textDeAfisat);
            } else {
                if (textDeAfisat.length() == 0) {
                    textDeAfisat = textDeAfisat + "South";
                } else {
                    textDeAfisat = textDeAfisat + ", South";
                }

                text.setText(textDeAfisat);
            }

            if (numarApasari >= 4 && serviceStatus == 0) {
                Intent intentServ = new Intent(getApplicationContext(), Colocviu1_13Service.class);
                String currentTime = Calendar.getInstance().getTime().toString();
                intentServ.putExtra("data_si_ora", currentTime);
                intentServ.putExtra("text_de_afisat", textDeAfisat);
                getApplicationContext().startService(intentServ);
                serviceStatus = 1;
            } else if (numarApasari >= 4 && serviceStatus == 1) {
                Intent intentServO = new Intent(getApplicationContext(), Colocviu1_13Service.class);
                stopService(intentServO);
                Intent intentServ = new Intent(getApplicationContext(), Colocviu1_13Service.class);
                String currentTime = Calendar.getInstance().getTime().toString();
                intentServ.putExtra("data_si_ora", currentTime);
                intentServ.putExtra("text_de_afisat", textDeAfisat);
                getApplicationContext().startService(intentServ);
                serviceStatus = 1;
            }
        }
    }

    private class NavigateButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Colocviu1_13SecondaryActivity.class);
            intent.putExtra("numar_apasari", numarApasari);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK)
                Toast.makeText(getApplication(), "Aplicatie realizata cu succes. S-a apasat butonul Register", Toast.LENGTH_LONG).show();
            else if (resultCode == RESULT_CANCELED)
                Toast.makeText(getApplication(), "Aplicatie realizata cu succes. S-a apasat butonul Cancel", Toast.LENGTH_LONG).show();
            else {
                text.setText("");
                textDeAfisat = "";
                numarApasari = 0;
                Toast.makeText(getApplication(), "Aplicatie realizata cu succes. Initializare", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class InitializareButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), Colocviu1_13SecondaryActivity.class);
            intent.putExtra("initializare", 0);
            startActivityForResult(intent, 1);
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, Colocviu1_13Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
