package com.jhermiz.tipsy;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import static com.jhermiz.tipsy.R.*;


public class MainActivity extends ActionBarActivity {

    private static final String TOTAL_BILL = "TOTAL_BILL";
    private static final String CURRENT_TIP = "CURRENT_TIP";
    private static final String BILL_WITHOUT_TIP = "BILL_WITHOUT_TIP";

    private double billBeforeTip;
    private double tipAmount;
    private double finalBill;

    EditText billBeforeTipET;
    EditText tipAmountET;
    TextView finalBillTV;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        if(savedInstanceState == null){
            //just starting the app
            billBeforeTip=0.00
            tipAmount=0;
            finalBill=0.00;
        }
        else{
            billBeforeTip = savedInstanceState.getDouble(BILL_WITHOUT_TIP);
            tipAmount = savedInstanceState.getDouble(CURRENT_TIP);
            finalBill = savedInstanceState.getDouble(TOTAL_BILL);
        }

        billBeforeTipET = (EditText) findViewById(id.billEditText);
        tipAmountET = (EditText) findViewById(id.tipEditText);
        finalBillTV = (TextView) findViewById(id.finalTextView);

        billBeforeTipET.addTextChangedListener(billBeforeTipListener);
        tipAmountET.addTextChangedListener(tipAmountListener);
        addListenerOnRatingBar();
    }

    public void addListenerOnRatingBar(){
        ratingBar = (RatingBar) findViewById(id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tipAmountET.setText((String.valueOf(rating * 5)));
                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tipAmountET.getWindowToken(), 0);
                updateTipAndFinalBill();
            }
        });
    }

    private TextWatcher tipAmountListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try{
                tipAmount = Double.parseDouble(charSequence.toString());

            }
            catch(NumberFormatException e){
                tipAmount = 0;
                tipAmountET.setText("0");
            }
            updateTipAndFinalBill();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private TextWatcher billBeforeTipListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            try{
                billBeforeTip = Double.parseDouble(charSequence.toString());

            }
            catch(NumberFormatException e){
                billBeforeTip = 0.00;
                billBeforeTipET.setText("0.00");
            }
            updateTipAndFinalBill();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void updateTipAndFinalBill() {
            double tipAmount = Double.parseDouble(tipAmountET.getText().toString());
            double finalBill = billBeforeTip + (billBeforeTip * (tipAmount/100));
            finalBillTV.setText(String.format("%.02f", finalBill));
    }

    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putDouble(TOTAL_BILL, finalBill);
        outState.putDouble(CURRENT_TIP, tipAmount);
        outState.putDouble(BILL_WITHOUT_TIP, billBeforeTip);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
