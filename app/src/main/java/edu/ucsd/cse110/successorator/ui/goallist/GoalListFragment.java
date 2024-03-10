package edu.ucsd.cse110.successorator.ui.goallist;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.R;
import edu.ucsd.cse110.successorator.databinding.FragmentGoalListBinding;


public class GoalListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentGoalListBinding view;
    private GoalListAdapter adapter;

    public GoalListFragment() {
        // Required empty public constructor
    }
  
    public static GoalListFragment newInstance() {
        GoalListFragment fragment = new GoalListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the Model
        var modelOwner = requireActivity();
        var modelFactory = ViewModelProvider.Factory.from(MainViewModel.initializer);
        var modelProvider = new ViewModelProvider(modelOwner, modelFactory);
        this.activityModel = modelProvider.get(MainViewModel.class);

        ArrayAdapter<CharSequence> SpinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.goal_list_array,
                android.R.layout.simple_spinner_item
        );
        SpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.spinner.setAdapter(SpinnerAdapter);

//        String[] items = new String[]{"Today's Goals", "Recurring Goals"};
//        ArrayAdapter<String> SpinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
//        view.spinner.setAdapter(SpinnerAdapter);
//        Log.d("checking_listSelector", "reach line 169");

//        view.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view,
//                                           int pos, long id) {
//                    activityModel.listSelector(pos);
//                }
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                    activityModel.listSelector(0);
//                }
//            });
        // Initialize the Adapter (with an empty list for now)
        this.adapter = new GoalListAdapter(requireContext(), List.of(),
                id -> {
                    activityModel.toggleGoalStatus(id);
                });
        activityModel.getOrderedGoals().observe(goals -> {
            if (goals == null) {
                return;
            }
            adapter.clear();
            adapter.addAll(new ArrayList<>(goals)); // remember the mutable copy here!
            adapter.notifyDataSetChanged();
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = FragmentGoalListBinding.inflate(inflater, container, false);

        // Set the adapter on the ListView
        view.goalList.setAdapter(adapter);


        activityModel.getIsGoalListEmpty().observe(getViewLifecycleOwner(), isEmpty -> {
            view.empty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        });

        return view.getRoot();
    }
}
