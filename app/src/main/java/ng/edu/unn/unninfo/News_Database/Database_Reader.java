package ng.edu.unn.unninfo.News_Database;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;

import ng.edu.unn.unninfo.News_Object.Database_List;
import ng.edu.unn.unninfo.News_UI.CustomAdapter;

public class Database_Reader extends AsyncTask<Void,Void,Integer> {
    @SuppressLint("StaticFieldLeak")
    private Context con;
    private ArrayList<Database_List> NewsList = new ArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private RecyclerView rv;


    public Database_Reader(Context c, RecyclerView rv) {
        this.con = c;
        this.rv = rv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
            return this.ReadData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (result == 0) {

        } else {
            //BIND DATA TO RECYCLER VIEW
            CustomAdapter customAdapter = new CustomAdapter(NewsList,con);
            rv.setAdapter(customAdapter);
        }

    }
    private int ReadData(){
        DataBaseHelper database = new DataBaseHelper(con);
        Database_List databaseList;
        NewsList.clear();
        Cursor c = database.getAllPosts();
        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                String id = c.getString(c.getColumnIndex(DataBaseHelper.ID));
                String title = c.getString(c.getColumnIndex(DataBaseHelper.TITLE));
                String time = DaysGetter(c.getString(c.getColumnIndex(DataBaseHelper.DATE)));
                String date = DateGetter(c.getString(c.getColumnIndex(DataBaseHelper.DATE)));
                String postID = c.getString(c.getColumnIndex(DataBaseHelper.PostID));
                String PostUrl = c.getString(c.getColumnIndex(DataBaseHelper.postURL));
                Cursor d = database.getAllTEXTs(postID);
                String [] allAttachment = new String [d.getCount()];
                if (d.getCount() > 0){
                    int count = 0;
                    while (d.moveToNext()) {
                        allAttachment[count] = d.getString(d.getColumnIndex(DataBaseHelper.ATTACHMENT));
                        count++;
                    }
                }
                Cursor e = database.getAllIMAGEs(postID);
                String [] image = new String [e.getCount()];
                if (e.getCount() > 0) {
                    int count = 0;
                    while (e.moveToNext()) {
                        String ImageURL  = e.getString(e.getColumnIndex(DataBaseHelper.IMAGE));

                        image[count] = ImageURL;
                        count ++;
                    }
                }
                String body = c.getString(c.getColumnIndex(DataBaseHelper.BODY));
                databaseList = new Database_List();
                databaseList.setId(id);
                databaseList.setAttachment(allAttachment);
                databaseList.setImg(image);
                databaseList.setTime(time);
                databaseList.setBody(body);
                databaseList.setDate(date);
                databaseList.setPostUrl(PostUrl);
                databaseList.setTitle(title);
                NewsList.add(databaseList);
            }

        }
        int count = NewsList.size();
        if (count > 0){
            return 1;
        }else{
            return 0;
        }
    }
    public  String DaysGetter (String Date) {
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
        } else if (DiffMonth == 1 && DiffDay > 30) {
            monthh = " month ago";
            Display = DiffMonth + monthh;
        } else if (DiffMonth == 1 && DiffDay < 30) {
            dayy = " days  ago";
            Display = DiffDay + dayy;

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
    public String GetDate() {
        String dat = new Date().toGMTString();
        return dat.substring(0, 11);
    }
    public static String DateGetter (String Date){
        String m_date = Date.substring(0,10);
        int Year = Integer.parseInt(m_date.substring(0,4));
        int month = Integer.parseInt(m_date.substring(5,7));
        int day = Integer.parseInt(m_date.substring(8,10));
        String d_month = null;
        String stuff,the_Date;
        switch (month){
            case 1 : d_month = "January";break;case 6 : d_month = "June";break;
            case 2 : d_month = "February";break;case 7 : d_month = "July";break;
            case 3 : d_month = "March";break;case 8 : d_month = "August";break;
            case 4 : d_month = "April";break;case 9 : d_month = "September";break;
            case 5 : d_month = "May";break;case 10 : d_month = "October";break;
            case 11: d_month = "November";break; case 12 : d_month = "December";break;
        }
        if ((day == 1)||(day == 21) ||(day == 31)) {
            stuff = "st";
        }else if ((day == 2)||(day == 22)) {
            stuff = "nd";
        }else if ((day == 3)||(day == 23)) {
            stuff = "rd";
        }else {
            stuff = "th";
        }
        the_Date = (day+stuff+" of "+d_month+", "+Year);
        return the_Date;
    }


}
