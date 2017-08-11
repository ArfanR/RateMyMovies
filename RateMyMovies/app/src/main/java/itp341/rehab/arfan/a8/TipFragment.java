package itp341.rehab.arfan.a8;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
/**
 * Created by ArfanR on 3/7/17.
 */

public class TipFragment extends Fragment {
    private String TAG = MainActivity.class.getName();

    private double billAmount, tipPercent, calculatedTip, calculatedTotal, splitWays, perPerson;

    // UI
    private EditText mBillAmountEditText;
    private SeekBar mTipPercentSeekBar;
    private TextView mTipPercentText;
    private TextView mCalculatedTipText;
    private TextView mCalculatedTotalText;
    private Spinner mSplitSpinner;
    private TextView mPerPerson;
    private LinearLayout mPerPersonLayout;

    // Keys
    private final String BILLKEY = "BILL";
    private final String PERCENTKEY = "PERCENT";
    private final String TIPKEY = "TIP";
    private final String TOTALKEY = "TOTAL";
    private final String SPLITWAYSKEY = "SPLITWAYS";
    private final String PERPERSONKEY = "PERPERSON";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            billAmount = savedInstanceState.getDouble(BILLKEY);
            tipPercent = savedInstanceState.getDouble(PERCENTKEY);
            calculatedTip = savedInstanceState.getDouble(TIPKEY);
            calculatedTotal = savedInstanceState.getDouble(TOTALKEY);
            splitWays = savedInstanceState.getDouble(SPLITWAYSKEY);
            perPerson = savedInstanceState.getDouble(PERPERSONKEY);
        } else {
            billAmount = 0;
            calculatedTotal = 0;
            calculatedTip = 0;
            splitWays = 0;
            perPerson = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tip_fragment, container, false);

        mBillAmountEditText = (EditText) v.findViewById(R.id.billAmountInput);
        mTipPercentSeekBar = (SeekBar) v.findViewById(R.id.tipPercentSeekBar);
        mTipPercentText = (TextView) v.findViewById(R.id.tipPercentText);
        mCalculatedTipText = (TextView) v.findViewById(R.id.calculatedTipText);
        mCalculatedTotalText = (TextView) v.findViewById(R.id.calculatedTotalText);
        mSplitSpinner = (Spinner) v.findViewById(R.id.splitSpinner);
        mPerPerson = (TextView) v.findViewById(R.id.perPersonText);
        mPerPersonLayout = (LinearLayout) v.findViewById(R.id.perPersonLayout);

        String tempBillAmt = NumberFormat.getCurrencyInstance().format(billAmount/100);
        mBillAmountEditText.setText(tempBillAmt);
        mBillAmountEditText.setSelection(tempBillAmt.length());
        tipPercent = mTipPercentSeekBar.getProgress();
        mTipPercentText.setText(tipPercent + "%");
        mCalculatedTipText.setText(NumberFormat.getCurrencyInstance().format(calculatedTip / 100));
        mCalculatedTotalText.setText(NumberFormat.getCurrencyInstance().format(calculatedTotal / 100));
        mPerPerson.setText(NumberFormat.getCurrencyInstance().format(perPerson/100));

        // Set spinner choices
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.split_array, R.layout.support_simple_spinner_dropdown_item);
        mSplitSpinner.setAdapter(adapter);
        splitWays = getResources().getIntArray(R.array.split_values_array)[mSplitSpinner.getSelectedItemPosition()];

        // Set Bill Amount Listener
        mBillAmountEditText.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* do nothing */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    mBillAmountEditText.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$,.]", "");
                    try {
                        double parsed = Double.parseDouble(cleanString);
                        billAmount = parsed;
                        String formatted = NumberFormat.getCurrencyInstance().format(parsed/100);
                        current = formatted;
                    }
                    catch (NumberFormatException n) {
                        Log.d(TAG, "Invalid characters entered into bill amount.");
                    }
                    mBillAmountEditText.setText(current);
                    mBillAmountEditText.setSelection(current.length());
                    mBillAmountEditText.addTextChangedListener(this);

                    // do tip calculation
                    calculateTipAndSetValues();
                }
            }

            @Override
            public void afterTextChanged(Editable s) { /* do nothing */ }
        });

        // Set Seek Bar listener
        mTipPercentSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tipPercent = progress;
                String text = progress + "%";
                mTipPercentText.setText(text);

                // do tip calculation
                calculateTipAndSetValues();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { /* do nothing */ }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { /* do nothing */ }
        });

        // Spinner listener
        mSplitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                splitWays = getResources().getIntArray(R.array.split_values_array)[position];
                Log.d(TAG, "Split ways: " + splitWays);
                if (splitWays > 1) {
                    mPerPersonLayout.setVisibility(View.VISIBLE);
                }
                else {
                    mPerPersonLayout.setVisibility(View.GONE);
                }
                calculateTipAndSetValues();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { /* do nothing */ }
        });

        return v;
    }
        @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putDouble(BILLKEY, billAmount);
        savedInstanceState.putDouble(PERCENTKEY, tipPercent);
        savedInstanceState.putDouble(TIPKEY, calculatedTip);
        savedInstanceState.putDouble(TOTALKEY, calculatedTotal);
        savedInstanceState.putDouble(SPLITWAYSKEY, splitWays);
        savedInstanceState.putDouble(PERPERSONKEY, perPerson);
        super.onSaveInstanceState(savedInstanceState);
    }

    private void calculateTipAndSetValues() {
        calculatedTip = billAmount * (tipPercent/100);
        calculatedTotal = calculatedTip + billAmount;
        perPerson = calculatedTotal/splitWays;
        String formattedTipValue = NumberFormat.getCurrencyInstance().format(calculatedTip / 100);
        String formattedTotalValue = NumberFormat.getCurrencyInstance().format(calculatedTotal / 100);
        String formattedPerPersonValue = NumberFormat.getCurrencyInstance().format(perPerson / 100);
        mCalculatedTipText.setText(formattedTipValue);
        mCalculatedTotalText.setText(formattedTotalValue);
        mPerPerson.setText(formattedPerPersonValue);
    }

}
