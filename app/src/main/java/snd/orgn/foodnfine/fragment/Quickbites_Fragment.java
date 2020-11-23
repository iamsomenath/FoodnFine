package snd.orgn.foodnfine.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.fragmentAdapter.QuickBiteFragAdapater;
import snd.orgn.foodnfine.base.BaseFragment;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_BREAK_FAST;
import static snd.orgn.foodnfine.constant.AppConstants.PAGE_TYPE_QUICK_BITES;

/**
 * create an instance of this fragment.
 */
public class Quickbites_Fragment extends BaseFragment {
    View parentView;
@BindView(R.id.rv_recyclerView)
    RecyclerView rv_recyclerView;
QuickBiteFragAdapater quickBiteFragAdapater;
    private int page;
    private String title;
    Unbinder unbinder;

    boolean firstLoad;
    public Quickbites_Fragment() {
        // Required empty public constructor
    }

    public static Quickbites_Fragment newInstance(String page, String title) {
        Quickbites_Fragment fragment = new Quickbites_Fragment();
        Bundle args = new Bundle();
        args.putString("1", page);
        args.putString(PAGE_TYPE_QUICK_BITES, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("1", 0);
        title = getArguments().getString(PAGE_TYPE_BREAK_FAST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_quickbites_, container, false);
        unbinder = ButterKnife.bind(this, parentView);

        firstLoad = true;
        initRecyclerBreakfastListView();
        return parentView;
    }


    private void initRecyclerBreakfastListView() {

        quickBiteFragAdapater= new QuickBiteFragAdapater(getActivity());
        //  adapter.setCallbackAddTocart(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), VERTICAL, false);
        rv_recyclerView.setLayoutManager(layoutManager);
        rv_recyclerView.setAdapter(quickBiteFragAdapater);

    }






}
