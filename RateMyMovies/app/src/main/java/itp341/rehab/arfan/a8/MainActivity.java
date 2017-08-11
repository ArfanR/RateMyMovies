package itp341.rehab.arfan.a8;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getName();
    private String lastChosenType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chooseTipButton, chooseUnitButton, chooseMoneyButton;
        chooseTipButton = (Button) findViewById(R.id.chooseTipButton);
        chooseUnitButton = (Button) findViewById(R.id.chooseUnitButton);
        chooseMoneyButton = (Button) findViewById(R.id.chooseMoneyButton);

        lastChosenType = "";

        FragmentManager fm = getSupportFragmentManager();
        Fragment f = new TipFragment();
        fm.beginTransaction().add(R.id.fragment_container, f).commit();

        View.OnClickListener chooseFragmentListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                String chosenTypeString = button.getText().toString();
                FragmentManager fm = getSupportFragmentManager();
                // remove other fragment
                Fragment f = fm.findFragmentById(R.id.fragment_container);
                Fragment newFragment;

                if (chosenTypeString.equals(getString(R.string.button_tip))) {
                    newFragment = new TipFragment();
                    Log.d(TAG, "New tip fragment");
                } else if (chosenTypeString.equals(getString(R.string.button_units))){
                    newFragment = new UnitFragment();
                    Log.d(TAG, "New units fragment");
                } else {
                    newFragment = new MoneyFragment();
                    Log.d(TAG, "New money fragment");
                }
                if (f == null) {
                    // putin new fragment
                    Log.d(TAG, "Adding fragment");
                    fm.beginTransaction().add(R.id.fragment_container, newFragment).commit();
                }
                else if (!lastChosenType.equals(chosenTypeString)) {
                    // replace fragment if not the same
                    Log.d(TAG, "Replacing fragment");
                    fm.beginTransaction().replace(R.id.fragment_container, newFragment).commit();
                }
                else {
                    Log.d(TAG, "Same type, doing nothing");
                }
                lastChosenType = chosenTypeString;
            }
        };

        chooseTipButton.setOnClickListener(chooseFragmentListener);
        chooseUnitButton.setOnClickListener(chooseFragmentListener);
        chooseMoneyButton.setOnClickListener(chooseFragmentListener);

    }
}
