package ru.ivanmurzin.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    ImageButton button;
    SpeechRecognizer speechRecognizer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
        checkPermission();
        if (SpeechRecognizer.isRecognitionAvailable(this)) { // важно, чтобы на телефоне стояло приложение Google
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            Log.d("RRR", SpeechRecognizer.isRecognitionAvailable(this) + "");
            Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            speechRecognizer.setRecognitionListener(new RecognitionListener());
            button.setOnTouchListener((view, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // положил палец на кнопку
                        button.setImageDrawable(getDrawable(R.drawable.micro));
                        speechRecognizer.startListening(speechRecognizerIntent);
                        return true;

                    case MotionEvent.ACTION_UP: // убрал палец с кнопки
                        button.setImageDrawable(getDrawable(R.drawable.not_micro));
                        speechRecognizer.stopListening();
                        return true;
                }
                return false;
            });
        }


    }

    private void setupUI() {
        editText = findViewById(R.id.text);
        button = findViewById(R.id.button);
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
    }


    class RecognitionListener implements android.speech.RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {
        }


        @Override
        public void onResults(Bundle bundle) {
            ArrayList<String> data = bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
            editText.setText(data.get(0));
        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    }
}