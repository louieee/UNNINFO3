package ng.edu.unn.unninfo.News_Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Date;
import ng.edu.unn.unninfo.R;

public class DeleteTime extends AsyncTask<String,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context c;

    public DeleteTime(Context c) {
        this.c = c;
    }

    private int Delete_Time(String key) {
        int result = 0;
        String check = "";
        if (key.equals(c.getString(R.string.pref_time_2m_value))) {
            check = "2 months";
        } else if (key.equals(c.getString(R.string.pref_time_4m_value))) {
            check = "4 months";
        } else if (key.equals(c.getString(R.string.pref_time_6m_value))) {
            check = "6 months";
        } else if (key.equals(c.getString(R.string.pref_time_1m_value))) {
            check = "1 month";
        }
        DataBaseHelper database = new DataBaseHelper(c);
        Cursor c = database.getAllPosts();
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String postID = c.getString(c.getColumnIndex(DataBaseHelper.PostID));
                String myTime = DaysGetter(c.getString(c.getColumnIndex(DataBaseHelper.DATE)));
                String time = myTime.split(" ")[1];
                if (time.equalsIgnoreCase("month") || time.equalsIgnoreCase("months")) {
                    int yourDate = Integer.parseInt(myTime.split(" ")[0]);
                    int myDate = Integer.parseInt(check.split(" ")[0]);
                    if (yourDate >= myDate) {
                        result = database.deletePost(postID);
                    }
                }
            }
        }
        return result;
    }
    private  String DaysGetter (String Date) {
        String Display = "";
        int Rem, DispMonth;
        String dayy, monthh, hourr, minn, secss;
        java.util.Date n = new Date();
        int val = (n.getYear() - (n.getYear() % 100));
        int Year = (val * 20) + (n.getYear() % 100);
        int month = n.getMonth() + 1;
        int day = n.getDate();
        int hour = n.getHours();
        int minute = n.getMinutes();
        int seconds = n.getSeconds();
        int mYear = Integer.parseInt(Date.substring(0, 4));
        int mMonth = Integer.parseInt(Date.substring(5, 7));
        int mDay = Integer.parseInt(Date.substring(8, 10));
        int mHour = Integer.parseInt(Date.substring(11, 13));
        int mMinute = Integer.parseInt(Date.substring(14, 16));
        int mSeconds = Integer.parseInt(Date.substring(17, 19));
        int DiffYear = Year - mYear;
        int MonthGotten = DiffYear * 12;
        int CurrMonth = month + MonthGotten;
        int DiffMonth = CurrMonth - mMonth;
        int DayGotten = DiffMonth * 30;
        int CurrDay = DayGotten + day;
        int DiffDay = CurrDay - mDay;
        int DiffHour = hour - mHour;
        int MinuteGotten = DiffHour * 60;
        int CurrMinute = MinuteGotten + minute;
        int DiffMinute = CurrMinute - mMinute;
        int SecondsGotten = DiffMinute * 60;
        int CurrSeconds = SecondsGotten + seconds;
        int DiffSeconds = CurrSeconds - mSeconds;
        if (DiffMonth > 1) {
            monthh = " months ago";
            Display = DiffMonth + monthh;
        } else if (DiffMonth == 1) {
            monthh = " month ago";
            Display = DiffMonth + monthh;
        } else if (DiffMonth == 0) {
            if (DiffDay > 1) {
                dayy = " days ago";
                Display = DiffDay + dayy;
            } else if (DiffDay == 1) {
                dayy = " day ago";
                Display = DiffDay + dayy;
            } else if (DiffDay == 0) {
                if (DiffHour > 1) {
                    hourr = " hours ago";
                    Display = DiffHour + hourr;
                } else if (DiffHour == 1) {
                    hourr = " hour ago";
                    Display = DiffHour + hourr;
                } else if (DiffHour == 0) {
                    if (DiffMinute > 1) {
                        minn = " minutes ago";
                        Display = DiffMinute + minn;
                    } else if (DiffMinute == 1) {
                        minn = " minute ago";
                        Display = DiffMinute + minn;
                    } else if (DiffMinute == 0) {
                        if (DiffSeconds > 1) {
                            secss = " seconds ago";
                            Display = DiffSeconds + secss;
                        } else if (DiffSeconds == 1) {
                            secss = " second ago";
                            Display = DiffSeconds + secss;
                        } else if (DiffSeconds == 0) {
                            Display = "Just Now";
                        }
                    }
                }
            }
        }
        return Display;
    }


    @Override
    protected Integer doInBackground(String... strings) {
        String s = strings[0];
        return Delete_Time(s);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        Toast.makeText(c,"Preference activated",Toast.LENGTH_SHORT).show();
    }
}

