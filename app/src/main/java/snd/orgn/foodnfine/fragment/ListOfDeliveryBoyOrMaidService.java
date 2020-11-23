package snd.orgn.foodnfine.fragment;

import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseFragment;
import snd.orgn.foodnfine.callbacks.CallbackServiceActivity;

/**
 * Created By Sukdev

 */
public class ListOfDeliveryBoyOrMaidService extends BaseFragment{

    private CallbackServiceActivity callback;

    public ListOfDeliveryBoyOrMaidService() {
        // Required empty public constructor
    }


    public ListOfDeliveryBoyOrMaidService(CallbackServiceActivity callback) {
        this.callback = callback;
    }

    public static ListOfDeliveryBoyOrMaidService newInstance(String param1, String param2) {
        ListOfDeliveryBoyOrMaidService fragment = new ListOfDeliveryBoyOrMaidService();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_delivery_boy_or_maid, container, false);
    }




    @Override
    public void onDetach() {
        super.onDetach();

    }




    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
