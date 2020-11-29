package snd.orgn.foodnfine.adapter.activityAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.data.shared_presferences.SessionManager;

public class ImageSliderAdaper extends SliderViewAdapter<ImageSliderAdaper.SliderAdapterVH> {

    private Context context;
    private SessionManager sessionManager;

    public ImageSliderAdaper(Context context) {
        this.context = context;
        sessionManager = new SessionManager(context);
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, int position) {
     //   viewHolder.textViewDescription.setText("This is slider item " + position);

        switch (position) {
            case 0:
                //viewHolder.imageViewBackground.setBackgroundResource(R.drawable.slide_img);
                Glide.with(viewHolder.itemView)
                        .load("https://img.etimg.com/thumb/msid-74723587,width-650,imgsize-245027,,resizemode-4,quality-100/talli-turmeric-fb.jpg")
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                //viewHolder.imageViewBackground.setBackgroundResource(R.drawable.slide_img);
                Glide.with(viewHolder.itemView)
                        .load("https://images.financialexpress.com/2018/08/gst-eatery.jpg")
                        .into(viewHolder.imageViewBackground);
                break;

            default:
                //viewHolder.imageViewBackground.setBackgroundResource(R.drawable.slide_img);
                Glide.with(viewHolder.itemView)
                        .load("https://www.hospitalityandcateringnews.com/wp-content/uploads/2018/02/bills-restaurants-rolls-out-new-concept-sites-dialling-up-the-best-of-bills-1.jpg")
                        .into(viewHolder.imageViewBackground);
                break;
        }

        /*viewHolder.imageViewBackground.setOnClickListener(v -> {
            if (position == 0) {
                // for Swadesi product
                DeliveryEverything.getAppSharedPreference().saveDeliveryCost("0");

                Intent intent = new Intent(context, SwadesiProductActivity.class);
                intent.putExtra("type", "SWADESI");
                new SessionManager(context).setShopId("0");
                sessionManager.setKeyOrderType("5");//SWADESI
                context.startActivity(intent);
            } else if (position == 2) {
                Intent intent = new Intent(context, GroceryListActivity.class);
                sessionManager.setKeyOrderType("2");//GROC
                context.startActivity(intent);
            } else if (position == 1) {
                Intent intent = new Intent(context, ElecMedListActivity.class);
                intent.putExtra("TYPE", "Medicine Store");
                sessionManager.setKeyOrderType("6");//MEDICINE
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return 3;
    }


    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        RoundedImageView imageViewBackground;
        TextView textViewDescription1;
        TextView textViewDescription2;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_silderImage);
            textViewDescription1 = itemView.findViewById(R.id.tv_sliderText1);
            textViewDescription2 = itemView.findViewById(R.id.tv_sliderText2);
            this.itemView = itemView;
        }
    }
}

