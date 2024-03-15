package edu.ucsd.cse110.successorator.ui.goallist;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.ListItemGoalBinding;
import edu.ucsd.cse110.successorator.lib.domain.Goal;

public class GoalListAdapter extends ArrayAdapter<Goal> {
    Consumer<Integer> onCompleteClick;
    Map<Integer, Consumer<Integer>> updateGoalMap;
    public GoalListAdapter(Context context, List<Goal> goals, Consumer<Integer> onCompleteClick, Map<Integer, Consumer<Integer>> updateGoalMap) {
        super(context, 0, new ArrayList<>(goals));
        this.onCompleteClick = onCompleteClick;
        this.updateGoalMap = updateGoalMap;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the goal for this position.
        Goal goal = getItem(position);
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
        binding.goalContentText.setOnLongClickListener(v -> {
            var id = goal.id();
            assert id != null;
            v.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            var popup = new android.widget.PopupMenu(getContext(), v);
            popup.getMenuInflater().inflate(R.menu.update_goal, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                var action = updateGoalMap.get(item.getItemId());
                if (action != null) {
                    action.accept(id);
                    return true;
                }
                return false;
            });
            popup.show();
            return true;
        });
        // Populate the view with the flashcard's data.
        binding.goalContentText.setText(goal.getContent());
        binding.goalContentText.setPaintFlags(goal.isComplete() ?
                binding.goalContentText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG : 0);

        // Bind context
        Log.i("GLAdapter Context", goal.getContent() + (goal.getContext() != null ?
          goal.getContext().toString() : "NULL"));
        binding.goalContextText.setText(goal.getContext() != null ?
                                          goal.getContext().toString() : "NULL");


        return binding.getRoot();
    }

    // The below methods aren't strictly necessary

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public long getItemId(int position) {
        var goal = getItem(position);
        assert goal != null;

        var id = goal.id();
        assert id != null;

        return id;
    }
}