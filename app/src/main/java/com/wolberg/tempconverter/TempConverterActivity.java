package com.wolberg.tempconverter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.text.NumberFormat;

public class TempConverterActivity extends AppCompatActivity implements OnEditorActionListener {

    //define instance variables
    private TextView celsiusTextView;
    private EditText fahrenheitEditText;
    private String celsiusString;
    private String fahrenheitString;
    //defining shared preferences
    private SharedPreferences savedValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        //get reference to the widgets
        fahrenheitEditText = (EditText) findViewById(R.id.fahrenheitEditText);
        celsiusTextView = (TextView) findViewById(R.id.celsiusTextView);

        //setting listener
        fahrenheitEditText.setOnEditorActionListener(this);

        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if(actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            convertFahrenheitToCelsius();
        }
        return false;
    }

    public void convertFahrenheitToCelsius() {
        fahrenheitString = fahrenheitEditText.getText().toString();
        float fahrenheit;
        if (fahrenheitString.equals("")) {
            fahrenheit = 0;
        } else {
            fahrenheit = Float.parseFloat(fahrenheitString);
        }

        //calculate conversion
        float celsius = (fahrenheit - 32) * 5 / 9;

        //display data on the layout
        NumberFormat doub = NumberFormat.getInstance();
        celsiusTextView.setText(doub.format(celsius));
    }


    @Override
    protected void onPause(){
        //save the instance variables
        SharedPreferences.Editor editor = savedValues.edit();
        editor.putString("fahrenheitString",fahrenheitString);
        editor.putString("celsiusString",celsiusString);
        editor.commit();
        super.onPause();
    }
    @Override
    protected void onResume(){
        //save the instance variables
        super.onResume();
        //get instance variables
        fahrenheitString = savedValues.getString("fahrenheitString","");
        celsiusString = savedValues.getString("celsiusString","");
        //calculate and display
        convertFahrenheitToCelsius();
    }


}
