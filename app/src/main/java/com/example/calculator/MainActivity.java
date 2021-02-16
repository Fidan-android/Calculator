package com.example.calculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.icu.lang.UScript;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private boolean dotUsed = false;

    private boolean equalClicked = false;

    private final static int EXCEPTION = -1;
    private final static int IS_NUMBER = 0;
    private final static int IS_OPERAND = 1;
    private final static int IS_DOT = 4;

    Button buttonNumber0;
    Button buttonNumber1;
    Button buttonNumber2;
    Button buttonNumber3;
    Button buttonNumber4;
    Button buttonNumber5;
    Button buttonNumber6;
    Button buttonNumber7;
    Button buttonNumber8;
    Button buttonNumber9;

    Button buttonClear;
    Button buttonDivision;
    Button buttonMultiplication;
    Button buttonSubtraction;
    Button buttonAddition;
    Button buttonEqual;
    Button buttonDot;
    ImageButton buttonBack;

    TextView textViewInputNumbers;
    TextView textViewOutputNumbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initializeViewVariables();
        setOnClickListeners();
        setOnTouchListeners();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.button_zero:
                if (addNumber("0")) equalClicked = false;
                break;
            case R.id.button_one:
                if (addNumber("1")) equalClicked = false;
                break;
            case R.id.button_two:
                if (addNumber("2")) equalClicked = false;
                break;
            case R.id.button_three:
                if (addNumber("3")) equalClicked = false;
                break;
            case R.id.button_four:
                if (addNumber("4")) equalClicked = false;
                break;
            case R.id.button_five:
                if (addNumber("5")) equalClicked = false;
                break;
            case R.id.button_six:
                if (addNumber("6")) equalClicked = false;
                break;
            case R.id.button_seven:
                if (addNumber("7")) equalClicked = false;
                break;
            case R.id.button_eight:
                if (addNumber("8")) equalClicked = false;
                break;
            case R.id.button_nine:
                if (addNumber("9")) equalClicked = false;
                break;
            case R.id.button_addition:
                if (addOperand("+", view)) equalClicked = false;
                break;
            case R.id.button_subtraction:
                if (addOperand("-", view)) equalClicked = false;
                break;
            case R.id.button_multiplication:
                if (addOperand("x", view)) equalClicked = false;
                break;
            case R.id.button_division:
                if (addOperand("\u00F7", view)) equalClicked = false;
                break;
            case R.id.button_dot:
                if (addDot()) equalClicked = false;
                break;
            case R.id.button_back:{
                if (textViewInputNumbers.getText().length() != 0){
                    textViewInputNumbers.setText(textViewInputNumbers.getText().toString().substring(0, textViewInputNumbers.getText().length() - 1));
                }
                break;
            }
            case R.id.button_clear:
                textViewInputNumbers.setText("");
                textViewOutputNumbers.setText("");
                dotUsed = false;
                equalClicked = false;
                break;
            case R.id.button_equal:
                if (textViewInputNumbers.getText().toString() != null && !textViewInputNumbers.getText().toString().equals(""))
                    calculate(textViewInputNumbers.getText().toString(), view);
                break;
        }
    }

    private boolean addNumber(String s) {
        textViewInputNumbers.setText(textViewInputNumbers.getText().toString() + s);
        return true;
    }

    String operand = "";
    int int_number = -1;
    double double_number = -1;
    @SuppressLint("SetTextI18n")
    private boolean addOperand(String s, View view) {
        if (textViewInputNumbers.getText().equals("")) {
            Snackbar.make(view, "Невозможно применить операцию", Snackbar.LENGTH_SHORT).show();
            return false;
        } else {
            dotUsed = false;
            equalClicked = false;
            operand = s;
            try {
                int_number = Integer.parseInt(textViewInputNumbers.getText().toString());
            } catch (Exception ex){
                ex.printStackTrace();
            }
            if (int_number == -1){
                double_number = Double.parseDouble(textViewInputNumbers.getText().toString());
                textViewInputNumbers.setText("");
                textViewOutputNumbers.setText(double_number + s);
            } else {
                textViewInputNumbers.setText("");
                textViewOutputNumbers.setText(int_number + s);
            }
            return true;
        }
    }

    @SuppressLint("SetTextI18n")
    private void calculate(String s, View view) {
        switch (operand){
            case "+":{
                if (int_number == -1) {
                    double summa = double_number + Double.parseDouble(s);
                    textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Double.parseDouble(s) + "=" + summa);
                } else {
                    int summa = int_number + Integer.parseInt(s);
                    textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Integer.parseInt(s) + "=" + summa);
                }
                break;
            }
            case "-":{
                if (int_number == -1) {
                    double razn = double_number - Double.parseDouble(s);
                    textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Double.parseDouble(s) + "=" + razn);
                } else {
                    int razn = int_number - Integer.parseInt(s);
                    textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Integer.parseInt(s) + "=" + razn);
                }
                break;
            }
            case "x":{
                if (int_number == -1) {
                    double proiz = double_number * Double.parseDouble(s);
                    textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Double.parseDouble(s) + "=" + proiz);
                } else {
                    int proiz = int_number * Integer.parseInt(s);
                    textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Integer.parseInt(s) + "=" + proiz);
                }
                break;
            }
            case "\u00F7":{
                double number = Double.parseDouble(s);
                if (number != 0) {
                    if (int_number == -1) {
                        double del = double_number / number + double_number % number / 100;
                        textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Double.parseDouble(s) + "=" + del);
                    } else {
                        try {
                            double del = int_number / number + int_number % number / 100;
                            textViewOutputNumbers.setText(textViewOutputNumbers.getText().toString() + Integer.parseInt(s) + "=" + del);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                } else {
                    Snackbar.make(view, "Деление на ноль запрещено!", Snackbar.LENGTH_SHORT).show();
                }
                break;
            }
        }
        int_number = -1;
        double_number = -1;
        textViewInputNumbers.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                view.getBackground().setColorFilter(getColor(R.color.flower), PorterDuff.Mode.SRC_ATOP);
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                view.getBackground().clearColorFilter();
                view.invalidate();
                break;
            }
        }
        return false;
    }

    //Инициализация всех кнопок и других элементов управления
    private void initializeViewVariables(){
        buttonNumber0 = (Button) findViewById(R.id.button_zero);
        buttonNumber1 = (Button) findViewById(R.id.button_one);
        buttonNumber2 = (Button) findViewById(R.id.button_two);
        buttonNumber3 = (Button) findViewById(R.id.button_three);
        buttonNumber4 = (Button) findViewById(R.id.button_four);
        buttonNumber5 = (Button) findViewById(R.id.button_five);
        buttonNumber6 = (Button) findViewById(R.id.button_six);
        buttonNumber7 = (Button) findViewById(R.id.button_seven);
        buttonNumber8 = (Button) findViewById(R.id.button_eight);
        buttonNumber9 = (Button) findViewById(R.id.button_nine);

        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonDivision = (Button) findViewById(R.id.button_division);
        buttonMultiplication = (Button) findViewById(R.id.button_multiplication);
        buttonSubtraction = (Button) findViewById(R.id.button_subtraction);
        buttonAddition = (Button) findViewById(R.id.button_addition);
        buttonEqual = (Button) findViewById(R.id.button_equal);
        buttonDot = (Button) findViewById(R.id.button_dot);
        buttonBack = (ImageButton) findViewById(R.id.button_back);
        textViewInputNumbers = (TextView) findViewById(R.id.textView_input_numbers);
        textViewOutputNumbers = (TextView) findViewById(R.id.textView_output_numbers);
    }

    //Установка слушателя на клика по кнопкам
    private void setOnClickListeners(){
        buttonNumber0.setOnClickListener(this);
        buttonNumber1.setOnClickListener(this);
        buttonNumber2.setOnClickListener(this);
        buttonNumber3.setOnClickListener(this);
        buttonNumber4.setOnClickListener(this);
        buttonNumber5.setOnClickListener(this);
        buttonNumber6.setOnClickListener(this);
        buttonNumber7.setOnClickListener(this);
        buttonNumber8.setOnClickListener(this);
        buttonNumber9.setOnClickListener(this);

        buttonClear.setOnClickListener(this);
        buttonDivision.setOnClickListener(this);
        buttonMultiplication.setOnClickListener(this);
        buttonSubtraction.setOnClickListener(this);
        buttonAddition.setOnClickListener(this);
        buttonEqual.setOnClickListener(this);
        buttonDot.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    //Установка слушателя касания на кнопках
    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchListeners(){
        buttonNumber0.setOnTouchListener(this);
        buttonNumber1.setOnTouchListener(this);
        buttonNumber2.setOnTouchListener(this);
        buttonNumber3.setOnTouchListener(this);
        buttonNumber4.setOnTouchListener(this);
        buttonNumber5.setOnTouchListener(this);
        buttonNumber6.setOnTouchListener(this);
        buttonNumber7.setOnTouchListener(this);
        buttonNumber8.setOnTouchListener(this);
        buttonNumber9.setOnTouchListener(this);

        buttonClear.setOnTouchListener(this);
        buttonDivision.setOnTouchListener(this);
        buttonMultiplication.setOnTouchListener(this);
        buttonSubtraction.setOnTouchListener(this);
        buttonAddition.setOnTouchListener(this);
        buttonDot.setOnTouchListener(this);
        buttonBack.setOnTouchListener(this);
    }

    private boolean addDot(){
        boolean done = false;

        if (textViewInputNumbers.getText().length() == 0)
        {
            textViewInputNumbers.setText("0.");
            dotUsed = true;
            done = true;
        } else if (dotUsed == true)
        {
        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_OPERAND)
        {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + "0.");
            done = true;
            dotUsed = true;
        } else if (defineLastCharacter(textViewInputNumbers.getText().charAt(textViewInputNumbers.getText().length() - 1) + "") == IS_NUMBER)
        {
            textViewInputNumbers.setText(textViewInputNumbers.getText() + ".");
            done = true;
            dotUsed = true;
        }
        return done;
    }

    private int defineLastCharacter(String lastCharacter) {
        try {
            Integer.parseInt(lastCharacter);
            return IS_NUMBER;
        } catch (NumberFormatException e) {

        }

        if ((lastCharacter.equals("+") || lastCharacter.equals("-") || lastCharacter.equals("x") || lastCharacter.equals("\u00F7")))
            return IS_OPERAND;

        if (lastCharacter.equals("."))
            return IS_DOT;

        return -1;
    }
}