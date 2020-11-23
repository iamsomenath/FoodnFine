package snd.orgn.foodnfine.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.fragmentAdapter.MealsFragAdpater;
import snd.orgn.foodnfine.base.BaseFragment;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_MEALS;

/**

 * create an instance of this fragment.
 */
public class MealsFragment extends BaseFragment {

    View parentView;
    private static ViewPager viewpager;
    private int page;
    private String title;
    Unbinder unbinder;
    MealsFragAdpater adpter;
    @BindView(R.id.rv_meals)
    RecyclerView recyclerView;

    public MealsFragment() {
        // Required empty public constructor
    }


    public static MealsFragment newInstance(String page, String title) {
        MealsFragment thirdfragment = new MealsFragment();
        Bundle args = new Bundle();
        args.putString("1", page);
        args.putString(PAGE_TYPE_MEALS, title);
        thirdfragment.setArguments(args);
        return thirdfragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("1", 0);
        title = getArguments().getString(PAGE_TYPE_MEALS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_meals, container, false);
        ButterKnife.bind(this,parentView);
        initRecyclerDessertsListView();
        return parentView;
    }

    private void initRecyclerDessertsListView() {

        adpter= new MealsFragAdpater(getActivity());
        //  adapter.setCallbackAddTocart(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adpter);

    }


}
