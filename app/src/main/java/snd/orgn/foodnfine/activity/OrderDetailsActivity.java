package snd.orgn.foodnfine.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.adapter.activityAdapter.OrderListItemAdapter;
import snd.orgn.foodnfine.application.FoodnFine;
import snd.orgn.foodnfine.base.BaseActivity;
import snd.orgn.foodnfine.model.data_item.ItemDetailsResponse;
import snd.orgn.foodnfine.model.data_item.Order;

public class OrderDetailsActivity extends BaseActivity {

    @BindView(R.id.iv_orderDetails_back)
    ImageView iv_orderDetails_back;
    @BindView(R.id.order_time)
    TextView order_time;
    @BindView(R.id.order_id)
    TextView order_id;
    @BindView(R.id.shop_name)
    TextView shop_name;
    @BindView(R.id.shop_addr)
    TextView shop_addr;
    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.user_addr)
    TextView user_addr;
    @BindView(R.id.items)
    TextView items;

    @BindView(R.id.fixedcharge)
    TextView fixedcharge;
    @BindView(R.id.discountcharge)
    TextView discountcharge;
    @BindView(R.id.cancellationcharge)
    TextView cancellationcharge;
    @BindView(R.id.deliverycharge)
    TextView deliverycharge;
    @BindView(R.id.foodcharge)
    TextView foodcharge;
    @BindView(R.id.total)
    TextView total;
    @BindView(R.id.tv_orderList_deliveryAddress)
    TextView tv_orderList_deliveryAddress;
    @BindView(R.id.orderDetails)
    LinearLayout orderDetails;

    Order order;

    TextView pickUpAddress;
    TextView packageContainItems;
    TextView totalPrice;
    RecyclerView rv_itemList;
    LinearLayout layout_name;
    LinearLayout layout_item_description;
    LinearLayout layout_pickup_address;
    LinearLayout layout_delivery_address;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        initFields();
        setupOnClick();

        order = (Order) getIntent().getSerializableExtra("DETAILS");

        order_time.setText("ORDER DATE : " + order.getOrderDateTime());
        order_id.setText("ORDER #" + order.getOrderNumber());
        shop_name.setText(order.getName());
        shop_addr.setText(order.getAddress());
        user_name.setText(FoodnFine.getAppSharedPreference().getUsername());
        user_addr.setText(order.getDelivarAddress());
        //Log.d("TESTY", getIntent().getSerializableExtra("DETAILS").toString());

        pickUpAddress = (TextView) findViewById(R.id.tv_orderList_pickupAddress);
        packageContainItems = (TextView) findViewById(R.id.tv_package_content_item);
        rv_itemList = (RecyclerView) findViewById(R.id.rv_itemList);
        layout_name = (LinearLayout) findViewById(R.id.layout_name);
        layout_item_description = (LinearLayout) findViewById(R.id.layout_item_description);
        layout_pickup_address = (LinearLayout) findViewById(R.id.layout_pickup_address);
        layout_delivery_address = (LinearLayout) findViewById(R.id.layout_delivery_address);
        totalPrice = (TextView) findViewById(R.id.tv_totalAmount);

        String orderType = order.getOrderType();
        try {
            if (!orderType.equals("sendPackage")) {
                items.setVisibility(View.GONE);
                layout_item_description.setVisibility(View.VISIBLE);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
                rv_itemList.setLayoutManager(layoutManager);
                rv_itemList.setHasFixedSize(true);
                List<ItemDetailsResponse> itemArrayList = new ArrayList<>(order.getOrderDetails());
                OrderListItemAdapter orderListItemAdapter = new OrderListItemAdapter(this, this);
                rv_itemList.setAdapter(orderListItemAdapter);
                orderListItemAdapter.clearOrderDetailsList();
                orderListItemAdapter.addOrderDetailsList(itemArrayList);
                orderListItemAdapter.notifyDataSetChanged();
                layout_pickup_address.setVisibility(View.GONE);
                layout_delivery_address.setVisibility(View.GONE);
                orderDetails.setVisibility(View.VISIBLE);

                foodcharge.setText("₹" + order.getTotal_product_price());
                deliverycharge.setText("₹" + order.getDelivery_charge());
                cancellationcharge.setText("₹" + order.getCancellation_charge());
                discountcharge.setText("₹" + order.getDiscount_amount());
                fixedcharge.setText("₹" + order.getFixed_cost());
                total.setText("₹" + order.getTotalPrice() + ".00");
                totalPrice.setText("₹" + order.getTotal_product_price());

            } else {
                items.setVisibility(View.VISIBLE);
                layout_pickup_address.setVisibility(View.VISIBLE);
                layout_item_description.setVisibility(View.GONE);
                findViewById(R.id.temp).setVisibility(View.GONE);
                pickUpAddress.setVisibility(View.VISIBLE);
                packageContainItems.setVisibility(View.VISIBLE);
                layout_delivery_address.setVisibility(View.VISIBLE);
                tv_orderList_deliveryAddress.setText(order.getDelivarAddress());
                pickUpAddress.setText(order.getPickupAdd());
                orderDetails.setVisibility(View.GONE);
                packageContainItems.setText("👉 Package Contain: " + order.get_package()
                        + " \n" + "👉 Approx Distance : " + order.getDistance());
                totalPrice.setText("₹" + order.getOrder_actual_amount());
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void initFields() {

    }

    @Override
    public void setupOnClick() {
        iv_orderDetails_back.setOnClickListener(v -> {
            super.onBackPressed();
            finish();
        });
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(this, DasboardActivity.class));
        finish();
    }
}
