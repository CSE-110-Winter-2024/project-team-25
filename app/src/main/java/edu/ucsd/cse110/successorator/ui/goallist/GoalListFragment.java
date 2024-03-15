package edu.ucsd.cse110.successorator.ui.goallist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import edu.ucsd.cse110.successorator.MainViewModel;
import edu.ucsd.cse110.successorator.databinding.FragmentGoalListBinding;


public class GoalListFragment extends Fragment {
    private MainViewModel activityModel;
    private FragmentGoalListBinding view;
    private ArrayAdapter adapter;

    private List<ArrayAdapter> adapterList;
    // private ArrayAdapter<String>  SpinnerAdapter;

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


//        String[] items = new String[]{"Today's Goals", "Tomorrow", "Recurring Goals"};
//        SpinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, items);
        adapterList = new ArrayList<>();
        adapterList.add(new GoalListAdapter(requireContext(), List.of(),
                id -> {
                    activityModel.toggleGoalStatus(id);
                }, activityModel.getUpdateGoalMap()));
        adapterList.add(new GoalListAdapter(requireContext(), List.of(),
                id -> {
                    activityModel.toggleGoalStatus(id);
                }, activityModel.getUpdateGoalMap()));
        adapterList.add(new GoalListAdapter(requireContext(), List.of(),
                id -> {
                    activityModel.deleteGoal(id); // delete recurring goal --> all dated goal from that
                    //recurring goal need to be deleted
        }, activityModel.getUpdateGoalMap()));
        adapterList.add(new PendingListAdapter(requireContext(), List.of(),
                 activityModel.getUpdateGoalMap()));
        this.adapter = adapterList.get(0);

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
        activityModel.getAdapterIndexAsSubject().observe(id -> {
            adapter = adapterList.get(id);
            view.goalList.setAdapter(adapter);
        });

        activityModel.getIsGoalListEmpty().observe(getViewLifecycleOwner(), isEmpty -> {
            view.empty.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        });
        return view.getRoot();
    }
}
