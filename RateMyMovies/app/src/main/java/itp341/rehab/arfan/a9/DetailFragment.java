package itp341.rehab.arfan.a9;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import itp341.rehab.arfan.a9.model.Movie;
import itp341.rehab.arfan.a9.model.MovieSingleton;

/**
 * Created by ArfanR on 3/23/17.
 */

public class DetailFragment extends Fragment {

    private static final String TAG = DetailFragment.class.getSimpleName();

    ImageView movieImage;
    TextView textTitle, textDescription;
    ListView listComments;
    EditText editComment, editUser;
    Button buttonComment;

    //Bundle key
    public static final String ARGS_POSITION = "args_position";

    public static Drawable[] drawables;
    public static String[] images;
    int position;
    //ArrayAdapter<String> commentAdapter;
    CommentListAdapter commentAdapter;

    public DetailFragment() {
        // Required empty public constructor
    }

    // store newInstance input into fragment argument
    public static DetailFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(DetailFragment.ARGS_POSITION, position);

        DetailFragment f = new DetailFragment();
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        position = args.getInt(ARGS_POSITION,-1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detail_fragment, container, false);

        movieImage = (ImageView) v.findViewById(R.id.image_movie);
        textTitle = (TextView) v.findViewById(R.id.title_movie);
        textDescription = (TextView) v.findViewById(R.id.description_movie);
        listComments = (ListView) v.findViewById(R.id.comments_movie);
        editUser = (EditText) v.findViewById(R.id.edit_user);
        editComment = (EditText) v.findViewById(R.id.edit_comment);
        buttonComment = (Button) v.findViewById(R.id.button_comment);

        Movie m = MovieSingleton.get(getActivity()).getMovie(position);

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

        if (Patterns.WEB_URL.matcher(m.getUrl()).matches()) {
            Glide.with(getContext()).load(m.getUrl()).into(movieImage);
        }
        else {
            movieImage.setImageDrawable(drawables[m.getGenre()]);
        }

        textTitle.setText(m.getTitle());
        textDescription.setText(m.getDescription());

        //commentAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, m.getComments());
        commentAdapter = new CommentListAdapter(getActivity(), m.getComments());
        listComments.setAdapter(commentAdapter);

        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieSingleton.get(getActivity()).addMovieComment(position, editUser.getText().toString(), editComment.getText().toString());
                editUser.setText("");
                editComment.setText("");
                commentAdapter.updateComments(MovieSingleton.get(getActivity()).getMovie(position).getComments());
            }
        });

        return v;
    }

    private class CommentListAdapter extends BaseAdapter {
        ArrayList mData;
        Context context;

        public CommentListAdapter(Context context, HashMap<String, ArrayList<String>> map) {
            super();
            this.context = context;
            mData = new ArrayList();
            mData.addAll(map.entrySet());
        }

        public void updateComments(HashMap<String, ArrayList<String>> map) {
            mData.clear();
            mData.addAll(map.entrySet());
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public HashMap.Entry<String, ArrayList<String>> getItem(int position) {
            return (HashMap.Entry) mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.layout_list_comment, null);
            }

            HashMap.Entry<String, ArrayList<String>> item = getItem(position);

            TextView user = (TextView) convertView.findViewById(R.id.text1);
            TextView comment = (TextView) convertView.findViewById(R.id.text2);
            TextView time = (TextView) convertView.findViewById(R.id.time);

            user.setText("User: " + item.getKey());
            String comments = "";
            for (int i = 0; i < item.getValue().size(); i++) {
                comments += item.getValue().get(i) + "\n";
            }
            comment.setText(comments);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
            time.setText(sdf.format(new Date()));
            return convertView;
        }
    }

}
