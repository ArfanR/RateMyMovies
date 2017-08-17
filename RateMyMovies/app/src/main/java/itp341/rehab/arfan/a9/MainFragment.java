package itp341.rehab.arfan.a9;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import itp341.rehab.arfan.a9.model.Movie;
import itp341.rehab.arfan.a9.model.MovieSingleton;


/**
 * Created by ArfanR on 3/23/17.
 */

public class MainFragment extends Fragment {

    private static final String TAG = MainFragment.class.getSimpleName();

    Button buttonAdd;
    ListView listView;

    public static Drawable[] drawables;
    public static String[] images;
    MovieListAdapter adapter;

    public MainFragment() {
        // Required empty public constructor
    }


    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment f = new MainFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);

        Log.d(TAG, "onCreateView");

        //find views
        buttonAdd = (Button) v.findViewById(R.id.button_add);
        listView = (ListView)v.findViewById(R.id.listView);

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

        // access movie and load it in the list
        ArrayList<Movie> movies = MovieSingleton.get(getActivity()).getMovies();
        adapter = new MovieListAdapter(getActivity(), movies);
        listView.setAdapter(adapter);

        // what happens when user clicks add?
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "buttonAdd: onClick ");
                Intent i = new Intent(getActivity(), CreateActivity.class);
                startActivityForResult(i, 0);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "onListItemClick");
                adapter.setButtonsVisible(true, position);
                adapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    private class MovieListAdapter extends ArrayAdapter<Movie> {

        // store the data source
        ArrayList<Movie> movies;
        private boolean buttonsVisible = false;
        private int changePosition;

        // create the constructor
        public MovieListAdapter(Context context, ArrayList<Movie> m) {
            //call parent constructor
            super(context, 0, m);
            //store the array
            movies = m;
        }

        public void setButtonsVisible(boolean isVisible, int position) {
            buttonsVisible = isVisible;
            changePosition = position;
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_list_movie, null);
            }

            ImageView genreImage = (ImageView) convertView.findViewById(R.id.image_movie);
            TextView textTitle = (TextView) convertView.findViewById(R.id.title_movie);
            TextView textDescription = (TextView) convertView.findViewById(R.id.description_movie);
            Button comment = (Button) convertView.findViewById(R.id.button_comment);

            Movie m = movies.get(position);
            // set genre image
            if (Patterns.WEB_URL.matcher(m.getUrl()).matches()) {
                Glide.with(getContext()).load(m.getUrl()).into(genreImage);
            }
            else {
                genreImage.setImageDrawable(drawables[m.getGenre()]);
            }
            // show image and button only if clicked
            if (position == changePosition && buttonsVisible) {
                genreImage.setVisibility(View.VISIBLE);
                comment.setVisibility(View.VISIBLE);
            }
            else {
                genreImage.setVisibility(View.GONE);
                comment.setVisibility(View.GONE);
            }

            textTitle.setText(m.getTitle());
            textDescription.setText(m.getDescription());

            // set button tag
            comment.setTag(position);

            comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Find out more: onClick");
                    Intent i = new Intent(getActivity(), DetailActivity.class);
                    i.putExtra(DetailActivity.EXTRA_POSITION, (int)v.getTag());
                    startActivity(i);
                }
            });

            return convertView;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            adapter.notifyDataSetChanged();
        }

    }
}
