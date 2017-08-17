package itp341.rehab.arfan.a9;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ArfanR on 3/23/17.
 */

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    // how will we pass data from MainListFragment?
    public static final String EXTRA_POSITION = "extra_position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        // get intent data
        int position = getIntent().getIntExtra(EXTRA_POSITION, -1);
        //if position == -1, then we are ADDING a movie
        //if postions != -1, then we are editing an EXISTING record

        //Create fragment
        FragmentManager fm = getSupportFragmentManager();
        Fragment f = fm.findFragmentById(R.id.fragment_container);

        //T modify fragment creation
        if (f == null ) {
            f = DetailFragment.newInstance(position);
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, f);
        fragmentTransaction.commit();
    }
}
