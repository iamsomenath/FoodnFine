package snd.orgn.foodnfine.activity;

import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import snd.orgn.foodnfine.R;
import snd.orgn.foodnfine.base.BaseActivity;

public class DailyBookingActivity extends BaseActivity {

    @BindView(R.id.tvBtn_dailyBookingNext)
    CardView tvBtn_dailyBookingNext;
    @BindView(R.id.iv_daily_back)
    ImageView iv_daily_back;
    @BindView(R.id.layout_date)
    LinearLayout layout_date;
    @BindView(R.id.layout_time)
    LinearLayout layout_time;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.rg_daily_booking_service)
    RadioGroup rg_daily_booking_service;
    String date = "", date2 = "";
    private String seletedType = "null";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_booking);
        ButterKnife.bind(this);
        hideStatusBarcolor();
        setupOnClick();
    }

    @Override
    public void initFields() {

    }

    @Override
    public void setupOnClick() {
        tvBtn_dailyBookingNext.setOnClickListener(v -> {

            int selectedId_meal = rg_daily_booking_service.getCheckedRadioButtonId();
            switch (selectedId_meal) {

                case R.id.rb_daily_booking_office_office_boy:
                    seletedType = "office_boy";
                    break;

                case R.id.rb_daily_booking_maid:
                    seletedType = "maid";
                    break;


                case R.id.rb_daily_booking_both:
                    seletedType = "both";
                    break;
                default:
                    seletedType = "null";
                    break;
            }
            if (seletedType.equals("null")) {
                Toast.makeText(this, "Please Select service Type", Toast.LENGTH_SHORT).show();
            } else {
                if(tv_date.getText().equals("") ||tv_date.getText().length() ==0){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "Please Select Date", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.WHITE);
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#00585e"));

                    textView.setTextSize(16);
                    snackbar.show();
                }else if(tv_time.getText().length() == 0||tv_time.getText().equals("")){
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "Please Select  Time", Snackbar.LENGTH_LONG);
                    View snackbarView = snackbar.getView();
                    snackbarView.setBackgroundColor(Color.YELLOW);
                    TextView textView = snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#00585e"));

                    textView.setTextSize(16);
                    snackbar.show();
                }else{
                    goToConfirm();
                }


            }




        });
        iv_daily_back.setOnClickListener(v -> {
            super.onBackPressed();
        });

        layout_date.setOnClickListener(v -> {
            openCalenderView();
        });
        layout_time.setOnClickListener(v -> {
            getTime();
        });
    }

    private void goToConfirm() {
        /*Intent intent = new Intent(this, OfficeBoyOrderActivity.class);
        intent.putExtra("SelectedDate",tv_date.getText().toString());
        intent.putExtra("selectedTime",tv_time.getText().toString());
        intent.putExtra("selectedServiceType",seletedType);
        startActivity(intent);*/
    }

    private void hideStatusBarcolor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            window.setStatusBarColor(getResources().getColor(R.color.white_background));
        }
    }


    private void openCalenderView() {


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(DailyBookingActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        if (monthOfYear + 1 <= 9) {
                            if (dayOfMonth <= 9) {
                                date = year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                date2 = "0" + dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
                            } else {
                                date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                date2 = dayOfMonth + "-0" + (monthOfYear + 1) + "-" + year;
                            }
                        } else {
                            if (dayOfMonth <= 9) {
                                date = year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth;
                                date2 = "0" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            } else {
                                date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                date2 = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            }
                        }
                        tv_date.setText(date);
//                            if (orders_flag) {
//                               // orderlistdetails(date);
//                            }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
      //  datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }


    private void getTime() {

        // Get Current Time
        final Calendar c = Calendar.getInstance();
        //c.add(Calendar.MINUTE, 30);
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMinute = c.get(Calendar.MINUTE);

        // Get Current Time
        //Calendar mcurrentTime = Calendar.getInstance();
        //int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        //int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(DailyBookingActivity.this/*, android.R.style.Theme_Holo_Light_Dialog*/,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                        Calendar calSet = (Calendar) c.clone();

                        calSet.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calSet.set(Calendar.MINUTE, selectedMinute);

                        if (calSet.compareTo(c) > 0) {
                            long diff = calSet.getTimeInMillis() - c.getTimeInMillis();
                            int stringPrompt = (int) diff / 60000;
                            //Log.d("!!!TIME", stringPrompt+"");
                            if (stringPrompt < 10) {
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                                        "⌚  Minimum 10 Minutes.",
                                        Snackbar.LENGTH_LONG);
                                View snackbarView = snackbar.getView();
                                snackbarView.setBackgroundColor(Color.WHITE);
                                TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                                textView.setTextColor(Color.parseColor("#00585e"));

                                textView.setTextSize(16);
                                snackbar.show();
                            } else
                                updateTime(selectedHour, selectedMinute);
                        } else {
                            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                                    "⌚ Backward Time Can't Be Accepted", Snackbar.LENGTH_LONG);
                            View snackbarView = snackbar.getView();
                            snackbarView.setBackgroundColor(Color.WHITE);
                            TextView textView = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
                            textView.setTextColor(Color.parseColor("#00585e"));

                            textView.setTextSize(16);
                            snackbar.show();
                        }
                    }
                }, mHour, mMinute, false);//Yes 24 hour time
        mTimePicker.show();
    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {

        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        // Append in a StringBuilder
        String aTime = new StringBuilder().append(hours).append(':')
                .append(minutes).append(" ").append(timeSet).toString();

        tv_time.setText(aTime);
    }


}
