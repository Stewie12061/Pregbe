package com.example.pregbe.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.pregbe.R;

import java.util.Calendar;
import java.util.TimeZone;


public class LichFragment extends Fragment {

    String mDay, mMonth, mYear;
    private int mHour, mMinute;

    Button themLichKham;
    TextView txtTime, txtLich, txtTieuDe, txtNoiDung;

    public LichFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lich, container, false);

        CalendarView simpleCalendarView = (CalendarView) view.findViewById(R.id.calendarView); // get the reference of CalendarView

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 1, 1);
        simpleCalendarView.setMinDate(System.currentTimeMillis());
        simpleCalendarView.setMaxDate(calendar.getTimeInMillis());

        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                mDay = String.valueOf(dayOfMonth);
                mMonth = String.valueOf(month+1);
                mYear = String.valueOf(year);
            }
        });

        txtTime = view.findViewById(R.id.txtTime);

        themLichKham = view.findViewById(R.id.themLichKham);
        themLichKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayDialog();
            }

        });



        return (view);
    }

    private void displayDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_custom_dialog, null);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();

        LinearLayout settime = dialogView.findViewById(R.id.addTime);
        EditText edtTime = dialogView.findViewById(R.id.edtTime);
        txtLich = dialogView.findViewById(R.id.ngayDatLich);
        txtLich.setText(mDay + "-" + mMonth + "-" + mYear);


        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                edtTime.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });

        alertDialog.show();
    }

}