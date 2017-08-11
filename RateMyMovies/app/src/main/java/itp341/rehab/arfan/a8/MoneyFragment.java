package itp341.rehab.arfan.a8;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ArfanR on 3/7/17.
 */

public class MoneyFragment extends Fragment {

    private String TAG = MainActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.money_fragment, container, false);

        final EditText inputText = (EditText) v.findViewById(R.id.moneyInputEditText);
        final TextView resultValueText = (TextView) v.findViewById(R.id.resultTextValue);

        final Button convertButton = (Button) v.findViewById(R.id.convertButton);
        final Spinner fromSpinner = (Spinner) v.findViewById(R.id.fromSpinner);
        final Spinner toSpinner = (Spinner) v.findViewById(R.id.toSpinner);

        final Map<Pair<Integer, Integer>, Double> conversionsMap = Collections.unmodifiableMap(
                new HashMap<Pair<Integer, Integer>, Double>() {
                    {
                        String[] currencies = getResources().getStringArray(R.array.currencies);
                        Integer usd = -1, yuan = -1, euro = -1;

                        for (int i=0; i < currencies.length; ++i) {
                            if (currencies[i].equals(getString(R.string.usd))) {
                                usd = i;
                            }
                            if (currencies[i].equals(getString(R.string.yuan))) {
                                yuan = i;
                            }
                            if (currencies[i].equals(getString(R.string.euro))) {
                                euro = i;
                            }
                        }
                        this.put(Pair.create(usd, usd), 1.0);
                        this.put(Pair.create(usd, yuan), 6.51);
                        this.put(Pair.create(usd, euro), 0.90);

                        this.put(Pair.create(yuan, usd), 0.15);
                        this.put(Pair.create(yuan, yuan), 1.0);
                        this.put(Pair.create(yuan, euro), 0.14);

                        this.put(Pair.create(euro, usd), 1.12);
                        this.put(Pair.create(euro, yuan), 7.27);
                        this.put(Pair.create(euro, euro), 1.0);
                    }

                }
        );

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fromId = fromSpinner.getSelectedItemPosition();
                int toId = toSpinner.getSelectedItemPosition();

                Double input = -1.0;
                try {
                    input = Double.parseDouble(inputText.getText().toString());
                } catch (NumberFormatException e) {
                    Log.d(TAG, "Invalid input for unit conversion");
                }

                if (fromId < 0 || toId < 0 || input < 0) {
                    Log.d(TAG, "Something not chosen, returning");
                    return;
                }

                double conversionFactor = conversionsMap.get(Pair.create(fromId, toId));
                Double result = conversionFactor * input;

                resultValueText.setText(result.toString());
            }
        });
        return v;
    }

}
