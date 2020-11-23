package snd.orgn.foodnfine.activity;

import android.os.Bundle;

import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.callbacks.CallbackServiceActivity;
import snd.orgn.foodnfine.fragment.ListOfDeliveryBoyOrMaidService;

public class ServiceActivity extends BaseActivity implements CallbackServiceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        loadInitialFragment();
    }

    @Override
    public void initFields() {

    }

    @Override
    public void setupOnClick() {

    }


    private void loadInitialFragment() {
        ListOfDeliveryBoyOrMaidService fragment_boyOrMaidSerciec = new ListOfDeliveryBoyOrMaidService(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerLayout_serviceActivity, fragment_boyOrMaidSerciec, "fragment")
                .commit();
    }

    @Override
    public void onServiceAdded() {

    }
}
