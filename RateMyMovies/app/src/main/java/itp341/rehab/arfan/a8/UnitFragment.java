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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.*;

/**
 * Created by ArfanR on 3/7/17.
 */

public class UnitFragment extends Fragment {

    private String TAG = MainActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.unit_fragment, container, false);

        final EditText inputText = (EditText) v.findViewById(R.id.unitInputEditText);
        final TextView resultValueText = (TextView) v.findViewById(R.id.resultTextValue);

        final Button convertButton = (Button) v.findViewById(R.id.convertButton);
        final RadioGroup fromGroup = (RadioGroup) v.findViewById(R.id.fromRadioGroup);
        final RadioGroup toGroup = (RadioGroup) v.findViewById(R.id.toRadioGroup);

        final RadioButton fromCentimeterRadioButton = (RadioButton) v.findViewById(R.id.fromCentimeterRB);
        final RadioButton fromMeterRadioButton = (RadioButton) v.findViewById(R.id.fromMetersRB);
        final RadioButton fromFeetRadioButton = (RadioButton) v.findViewById(R.id.fromFeetRB);
        final RadioButton fromMileRadioButton = (RadioButton) v.findViewById(R.id.fromMilesRB);
        final RadioButton fromKmRadioButton = (RadioButton) v.findViewById(R.id.fromKilometersRB);

        final RadioButton toCentimeterRadioButton = (RadioButton) v.findViewById(R.id.toCentimeterRB);
        final RadioButton toMeterRadioButton = (RadioButton) v.findViewById(R.id.toMetersRB);
        final RadioButton toFeetRadioButton = (RadioButton) v.findViewById(R.id.toFeetRB);
        final RadioButton toMileRadioButton = (RadioButton) v.findViewById(R.id.toMilesRB);
        final RadioButton toKmRadioButton = (RadioButton) v.findViewById(R.id.toKilometersRB);

        final Map<Pair<Integer, Integer>, Double> conversionsMap = Collections.unmodifiableMap(
                new HashMap<Pair<Integer, Integer>, Double> () {
                    {
                        Integer fromCentimeters = fromCentimeterRadioButton.getId();
                        Integer fromMeters = fromMeterRadioButton.getId();
                        Integer fromFeet = fromFeetRadioButton.getId();
                        Integer fromMiles = fromMileRadioButton.getId();
                        Integer fromKm = fromKmRadioButton.getId();

                        Integer toCentimeters = toCentimeterRadioButton.getId();
                        Integer toMeters = toMeterRadioButton.getId();
                        Integer toFeet = toFeetRadioButton.getId();
                        Integer toMiles = toMileRadioButton.getId();
                        Integer toKm = toKmRadioButton.getId();

                        this.put(Pair.create(fromCentimeters, toCentimeters), 1.0);
                        this.put(Pair.create(fromCentimeters, toMeters), .01);
                        this.put(Pair.create(fromCentimeters, toFeet), 0.0328);
                        this.put(Pair.create(fromCentimeters, toMiles), 0.00000621);
                        this.put(Pair.create(fromCentimeters, toKm), 0.00001);

                        this.put(Pair.create(fromMeters, toMeters), 1.0);
                        this.put(Pair.create(fromMeters, toCentimeters), 100.0);
                        this.put(Pair.create(fromMeters, toFeet), 3.2808);
                        this.put(Pair.create(fromMeters, toMiles), 0.000621);
                        this.put(Pair.create(fromMeters, toKm), .01);

                        this.put(Pair.create(fromFeet, toFeet), 1.0);
                        this.put(Pair.create(fromFeet, toCentimeters), 30.48);
                        this.put(Pair.create(fromFeet, toMeters), 0.3048);
                        this.put(Pair.create(fromFeet, toMiles), 0.000189);
                        this.put(Pair.create(fromFeet, toKm), 0.000304);

                        this.put(Pair.create(fromMiles, toMiles), 1.0);
                        this.put(Pair.create(fromMiles, toCentimeters), 160934.0);
                        this.put(Pair.create(fromMiles, toMeters), 1609.34);
                        this.put(Pair.create(fromMiles, toFeet), 5280.0);
                        this.put(Pair.create(fromMiles, toKm), 1.60934);

                        this.put(Pair.create(fromKm, toKm), 1.0);
                        this.put(Pair.create(fromKm, toCentimeters), 100000.0);
                        this.put(Pair.create(fromKm, toMeters), 1000.0);
                        this.put(Pair.create(fromKm, toFeet), 3280.84);
                        this.put(Pair.create(fromKm, toMiles), 0.62137);
                    }

                }
        );

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer fromId = fromGroup.getCheckedRadioButtonId();
                Integer toId = toGroup.getCheckedRadioButtonId();

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

                Double conversionFactor = conversionsMap.get(Pair.create(fromId, toId));

                Double result = input * conversionFactor;
                resultValueText.setText(result.toString());
            }
        });

        return v;
    }

}
