package itp341.rehab.arfan.a9;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import itp341.rehab.arfan.a9.model.Movie;
import itp341.rehab.arfan.a9.model.MovieSingleton;

/**
 * Created by ArfanR on 3/23/17.
 */

public class CreateFragment extends Fragment {

    private static final String TAG = CreateFragment.class.getSimpleName();

    EditText editTitle, editDescription, editUrl;
    Spinner spinnerGenre;
    ImageView imagePreview;
    Button buttonSave;

    public static String[] genres, images;
    public static Drawable[] drawables;
    ArrayAdapter<CharSequence> genreAdapter;

    public CreateFragment() {
        // Required empty public constructor
    }

    // store newInstance input into fragment argument
    public static CreateFragment newInstance() {
        //Bundle args = new Bundle();

        CreateFragment c = new CreateFragment();
        //c.setArguments(args);

        return c;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.create_fragment, container, false);

        editTitle = (EditText) v.findViewById(R.id.edit_title);
        editDescription = (EditText) v.findViewById(R.id.edit_description);
        editUrl = (EditText) v.findViewById(R.id.edit_url);
        spinnerGenre = (Spinner) v.findViewById(R.id.spinner_genre);
        imagePreview = (ImageView) v.findViewById(R.id.image_preview);
        buttonSave = (Button) v.findViewById(R.id.button_save);

        genres = getResources().getStringArray(R.array.genres);
        images = getResources().getStringArray(R.array.images);
        drawables = new Drawable[] {
                ContextCompat.getDrawable(getActivity(), R.drawable.action),
                ContextCompat.getDrawable(getActivity(), R.drawable.animation),
                ContextCompat.getDrawable(getActivity(), R.drawable.comedy),
                ContextCompat.getDrawable(getActivity(), R.drawable.drama),
                ContextCompat.getDrawable(getActivity(), R.drawable.fantasy),
                ContextCompat.getDrawable(getActivity(), R.drawable.horror),
                ContextCompat.getDrawable(getActivity(), R.drawable.romance),
                ContextCompat.getDrawable(getActivity(), R.drawable.scifi)
        };
        //load spinner values for States in the coffee shop address
        genreAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.genres, android.R.layout.simple_spinner_item);
        spinnerGenre.setAdapter(genreAdapter);

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imagePreview.setImageDrawable(drawables[position]);
                //Glide.with(getContext()).load(images[position]).into(imagePreview);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndClose();
            }
        });

        return v;
    }

    private void saveAndClose() {
        Log.d(TAG, "saveAndClose");
        Movie m = new Movie();

        if (buttonSave != null) {
            m.setTitle(editTitle.getText().toString());
            m.setDescription(editDescription.getText().toString());
            m.setUrl(editUrl.getText().toString());

            TextView tv = (TextView) spinnerGenre.getSelectedView();
            if (tv != null) {
                m.setGenre(Arrays.asList(genres).indexOf(tv.getText().toString()));
            }

            //ArrayList<String> listComments = new ArrayList<>();
            HashMap<String, ArrayList<String>> listComments = new HashMap<>();
            //ArrayList<String> list = new ArrayList<>();
            //list.add("comment1");
            //list.add("comment2");
            //listComments.put("user1", list);
            m.setComments(listComments);

            MovieSingleton.get(getActivity()).addMovie(m);
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        }

    }
}
