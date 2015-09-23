package svenmeier.coxswain.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

import propoid.ui.Index;
import propoid.ui.list.MatchAdapter;
import svenmeier.coxswain.Gym;
import svenmeier.coxswain.R;
import svenmeier.coxswain.gym.Workout;


public class WorkoutsFragment extends Fragment {

    private Gym gym;

    private ListView workoutsView;

    private WorkoutsAdapter adapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        gym = Gym.instance(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.layout_workouts, container, false);

        workoutsView = (ListView) root.findViewById(R.id.workouts);
        adapter = new WorkoutsAdapter();
        adapter.install(workoutsView);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        adapter.loadAsync(getActivity());
    }

    private class WorkoutsAdapter extends MatchAdapter<Workout> {

        private DateFormat format = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.SHORT);

        public WorkoutsAdapter() {
            super(R.layout.layout_workouts_item, Gym.instance(getActivity()).getWorkouts());
        }

        @Override
        protected void bind(int position, View view, Workout workout) {
            Index index = Index.get(view);

            TextView nameView = index.get(R.id.workout_name);
            nameView.setText(workout.name.get());

            TextView startView = index.get(R.id.workout_start);
            startView.setText(format.format(new Date(workout.start.get())));

            TextView countsView = index.get(R.id.workout_counts);
            String counts = TextUtils.join(", ", new String[]{
                    String.format(getString(R.string.duration_minutes), workout.duration.get() / 60),
                    String.format(getString(R.string.distance_meters), workout.distance.get()),
                    String.format(getString(R.string.strokes_count), workout.strokes.get())
                    });
            countsView.setText(counts);
        }
    }
}