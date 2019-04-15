package ng.edu.unn.unninfo.News_Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UNN_Database.db";
    private static final String TABLE_NAME = "MyNews";
    public static final String ID = "ID";
    public static final String IMAGE = "Image";
    public static final String TITLE = "Title";
    public static final String PostID = "PostID";
    public static final String postURL = "postURL";
    private static final String TYPE ="Type";
    public static final String BODY = "Body";
    public static final String ATTACHMENT = "Attachment";
    public static final String DATE = "Date";
    public DataBaseHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                IMAGE +" TEXT NOT NULL,"+
                TITLE +" TEXT NOT NULL,"+PostID+" INTEGER NOT NULL,"+
                postURL+" TEXT NOT NULL,"+
                TYPE+" TEXT NOT NULL,"+
                BODY +" TEXT NOT NULL,"+
                ATTACHMENT +" TEXT NOT NULL,"+
                DATE +" TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public void insertData(String Image, String Title, String postId, String postURL, String Type, String body,
                           String Attachment, String Date) throws SQLException{
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseHelper.IMAGE,Image);
        contentValues.put(DataBaseHelper.TITLE,Title);
        contentValues.put(DataBaseHelper.PostID,postId);
        contentValues.put(DataBaseHelper.postURL,postURL);
        contentValues.put(DataBaseHelper.TYPE,Type);
        contentValues.put(DataBaseHelper.ATTACHMENT,Attachment);
        contentValues.put(DataBaseHelper.BODY,body);
        contentValues.put(DataBaseHelper.DATE,Date);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();

    }

    public void deleteAttach(String post_id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE "+PostID+ "="+ post_id);
    }
    public int maxPostID() {
        int bb = 0;
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor b = db.query(TABLE_NAME, new String[]{"MAX(" + PostID + ") AS MAX"}, null,
                    null, null, null, PostID);
            b.moveToPosition(b.getCount() - 1);
            int index = b.getColumnIndex("MAX");
            String data = b.getString(index);
            bb = Integer.parseInt(data);
            b.close();
            return bb;
        }catch (NumberFormatException E){
            E.printStackTrace();
        }
        return 0;
    }

    public Cursor getAllPosts(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from "+TABLE_NAME+" WHERE "+TYPE+" = 'POST'"+" ORDER BY "+ID+" DESC",null);
    }
    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(Query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

    public Cursor getAllTEXTs(String postid){
        SQLiteDatabase db = this.getWritableDatabase();
        // SELECT ALL WHERE POST TYPE IS POST
        return db.rawQuery("Select * from "+TABLE_NAME+" WHERE "+TYPE+" = 'TEXT'"+" AND " +
                PostID+ " = "+postid+
                " ORDER BY "+ID+" DESC",null);
    }
    public Cursor getAllIMAGEs(String postid){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from "+TABLE_NAME+" WHERE "+TYPE+" = 'IMAGE'"+" AND " +
                PostID+ " = "+postid+
                " ORDER BY "+ID+" DESC",null);
    }

    public boolean updateData(String id,String imageURLs,String subject,String postId,String body,String Attachment, String Date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE,imageURLs);
        contentValues.put(TITLE,subject);
        contentValues.put(PostID,postId);
        contentValues.put(DataBaseHelper.ATTACHMENT,Attachment);
        contentValues.put(DataBaseHelper.BODY,body);
        contentValues.put(DataBaseHelper.DATE,Date);

        int result = db.update(TABLE_NAME,contentValues,ID+"=?",new String[]{id});
        return result > 0;

    }
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,ID+"=?",new String[]{id});
    }
    public Integer deletePost(String PostId){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,PostID+"=?",new String[]{PostId});
    }
    public int tableExist(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public boolean inDatabase(String value){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+value+" FROM " + TABLE_NAME+
                " WHERE "+ PostID +" = "+value+ " AND "+TYPE+" = 'POST'", null);
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
