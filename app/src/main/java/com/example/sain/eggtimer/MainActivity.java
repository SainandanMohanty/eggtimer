package com.example.sain.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SeekBar seekBar = findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress * 5);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void updateTimer(int time) {
        TextView textView = findViewById(R.id.textView);
        String timer = String.format(Locale.getDefault(), "%02d:%02d", time / 60, time % 60);
        textView.setText(timer);
    }

    public void onClick(View view) {
        Button button = (Button) view;
        String text = button.getText().toString();

        SeekBar seekBar = findViewById(R.id.seekBar);

        if (text.equals("Start")) {
            if (seekBar.getProgress() == 0) {
                Toast.makeText(this, "Set a nonzero timer", Toast.LENGTH_SHORT).show();
            } else {
                countDownTimer = new CountDownTimer((seekBar.getProgress() * 5 * 1000) + 100, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        updateTimer((int) (millisUntilFinished / 1000));
                    }

                    @Override
                    public void onFinish() {
                        onClick(findViewById(R.id.button));
                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                        mediaPlayer.start();
                    }

                }.start();

                button.setText("Stop");
                seekBar.setEnabled(false);
            }
        } else if (text.equals("Stop")) {
            countDownTimer.cancel();
            button.setText("Start");
            seekBar.setEnabled(true);
            seekBar.setProgress(0);
        }
    }
}