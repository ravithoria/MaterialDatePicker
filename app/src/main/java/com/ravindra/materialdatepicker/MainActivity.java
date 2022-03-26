package com.ravindra.materialdatepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.CompositeDateValidator;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button mPickDateButton;
    private TextView mShowSelectedDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // now register the text view and the button with
        // their appropriate IDs
        mPickDateButton = findViewById(R.id.pick_date_button);
        mShowSelectedDateText = findViewById(R.id.show_selected_date);

        MaterialDatePicker.Builder<Long>
                materialDateBuilder = MaterialDatePicker.Builder.datePicker();

        // ============= Prev. - Future date enable disable ======================

        CalendarConstraints.Builder calendarConstraints = new CalendarConstraints.Builder();
        // 1.
        //calendarConstraints.setValidator(DateValidatorPointForward.now()); // disable past date, not today date
        // 2.
        //calendarConstraints.setValidator(DateValidatorPointBackward.now()); // disable future date, not today date

        // ====================Custom Calendar
        //Calendar calendar = Calendar.getInstance();

        // 3. - issue - today date disable but by default selected
        //custom - "disable past date with today date" using Calendar.DATE
        /*calendar.add(Calendar.DATE, 0);
        calendarConstraints.setValidator(DateValidatorPointForward.from(calendar.getTimeInMillis()));*/

        // 4.
        //custom - "disable future date with today date" using Calendar.DATE
        /*calendar.add(Calendar.DATE, -1);
        calendarConstraints.setValidator(DateValidatorPointBackward.before(calendar.getTimeInMillis()));*/

        // 5. - issue - today date disable but by default selected
        //custom - "disable past 18 years date with today date" using Calendar.YEAR
        /*calendar.add(Calendar.YEAR, -18); // disable from current date to 18 years past
        calendarConstraints.setValidator(DateValidatorPointForward.from(calendar.getTimeInMillis()));*/

        // selected what calendar set from past - future -> using Calendar.DATE
        //materialDateBuilder.setSelection(calendar.getTimeInMillis());

        // ===================Custom Calendar Over


        // 6.
        // disable weekend (Saturday-Sunday) -> using DateValidatorWeekdays class
        //calendarConstraints.setValidator(new DateValidatorWeekdays());

        // 7.
        // Opens the date picker with today's date selected.
        //materialDateBuilder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());

        materialDateBuilder.setTitleText("SELECT A DATE");
        // Single calendarConstraints
        materialDateBuilder.setCalendarConstraints(calendarConstraints.build());


        //==================
        // Multiple calendarConstraints
        ArrayList<CalendarConstraints.DateValidator> validators = new ArrayList<>();

        // 1.
        //validators.add(DateValidatorPointForward.now()); //disable past date, not today
        // 2.
        //validators.add(new DateValidatorWeekdays()); //disable weekend (Saturday-Sunday)

        /*calendarConstraints.setValidator(CompositeDateValidator.allOf(validators));
        materialDateBuilder.setCalendarConstraints(calendarConstraints.build());*/
        //==================


        // ============= Prev. - Future date enable disable Over ======================



        final MaterialDatePicker<Long> materialDatePicker = materialDateBuilder.build();

        mPickDateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(),
                                "MATERIAL_DATE_PICKER");
                    }
                });

        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        // if the user clicks on the positive
                        // button that is ok button update the
                        // selected date
                        mShowSelectedDateText.setText(
                                "Selected Date is : " + materialDatePicker.getHeaderText());
                        String strDate = materialDatePicker.getHeaderText();

                        // Change date format from String Date
                        parseDate(strDate);
                        Log.d("TAG", "formatDate:: " + parseDate(strDate));

                        //String to Date
                        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
                        try {
                            Date date = format.parse(strDate);
                            System.out.println(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    public String parseDate(String time) {
        String inputPattern = "MMM dd, yyyy"; // Material Date picker default date format
        //String inputPattern = "EEE MMM dd HH:mm:ss zzzz yyyy"; // Android default date format
        String outputPattern = "dd-MM-yyyy"; // What we want date format
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date;
        String str = null;

        try {
            date = inputFormat.parse(time);
            if (date != null) {
                str = outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}