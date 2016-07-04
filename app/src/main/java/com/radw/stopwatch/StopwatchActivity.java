package com.radw.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {
    // número de segundos mostrados en el display
    private int seconds = 0;
    // ¿está el contador funcionando?
    private boolean running;
    private boolean wasRunning;

    private int anterior = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        if (savedInstanceState != null) {
            // Recoge el estado anterior del cronómetro si la actividad
            // ha sido destruida y recreada
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");

            anterior = savedInstanceState.getInt("anterior");
        }
        runTimer();

        mostrarAnterior();



    }

    // Se guarda el estado de la instancia que se desee
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);

        savedInstanceState.putInt("anterior", anterior);

    }

    //si la actividad se para, el cronómetro entra en pausa
    @Override
    public void onPause(){
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //si la actividad vuelve, el cronómetro continua contando
    @Override
    public void onResume(){
        super.onResume();
        if (wasRunning){
            running = true;
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (wasRunning){
            running = true;
        }
    }

    //Start the stopwatch running when the start button is clicked
    public void onClickStart(View view){
        running = true;
    }

    //Stop the stopwatch running when the stop button is clicked
    public void onClickStop(View view){
        running = false;
    }

    //Reset the stopwatch running when the reset button is clicked
    public void onClickReset(View view){
        running = false;
        anterior = seconds;
        mostrarAnterior();
        seconds = 0;
    }

    //Sets de number of seconds on the timer
    private void runTimer(){
        final TextView timeView = (TextView)findViewById(R.id.time_view);

        final Handler handler = new Handler();
        handler.post(new Runnable() {

            @Override
            public void run() {

                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    //muestra el resultado anterior
    private void mostrarAnterior(){
        final TextView anteriorView = (TextView)findViewById(R.id.anterior);

        int hours = anterior / 3600;
        int minutes = (anterior % 3600) / 60;
        int secs = anterior % 60;
        String anteriorTime = String.format("%d:%02d:%02d", hours, minutes, secs);
        anteriorView.setText(anteriorTime);


    }

}
