package snd.orgn.foodnfine.adapter.activityAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import snd.orgn.foodnfine.R;

public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<String> mSliderItems;

    public SliderAdapterExample(Context context, ArrayList<String> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        String sliderItem = mSliderItems.get(position);

        Picasso.get().load(sliderItem)
                //.placeholder(R.drawable.twiclo_logo)
                //.transform(new CircleTransform())
                .into(viewHolder.itemView);
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        ImageView itemView;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            this.itemView = itemView.findViewById(R.id.iv_silderImage);
        }
    }

}