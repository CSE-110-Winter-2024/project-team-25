package edu.ucsd.cse110.successorator.ui.goallist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalListAdapter extends ArrayAdapter<Goal> {
    Consumer<Integer> onDeleteClick;
    Consumer<Integer> onCompleteClick;
    public GoalListAdapter(Context context, List<Goal> goals) {
        super(context, 0, new ArrayList<>(goals));
    }
    public GoalListAdapter(Context context, List<Goal> goals, Consumer<Integer> onCompleteClick) {
        super(context, 0, new ArrayList<>(goals));
//        this.onDeleteClick = onDeleteClick;
        this.onCompleteClick = onCompleteClick;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the goal for this position.
        var goal = getItem(position);
        assert goal != null;

        // Check if a view is being reused...
        ListItemGoalBinding binding;
        if (convertView != null) {
            // if so, bind to it
            binding = ListItemGoalBinding.bind(convertView);
        } else {
            // otherwise inflate a new view from our layout XML.
            var layoutInflater = LayoutInflater.from(getContext());
            binding = ListItemGoalBinding.inflate(layoutInflater, parent, false);
        }
        binding.goalContentText.setOnClickListener(v -> {
            var id = goal.id();
            assert id != null;
            onCompleteClick.accept(id);
        });
        // Populate the view with the flashcard's data.
        binding.goalContentText.setText(goal.getContent());
        binding.goalContentText.setPaintFlags(goal.isComplete() ? binding.goalContentText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : 0);

        return binding.getRoot();
    }

    // The below methods aren't strictly necessary, usually.
    // But get in the habit of defining them because they never hurt
    // (as long as you have IDs for each item) and sometimes you need them.

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var flashcard = getItem(position);
        assert flashcard != null;

        var id = flashcard.id();
        assert id != null;

        return id;
    }
}