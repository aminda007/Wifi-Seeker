package com.codemo.www.wifiseeker.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.codemo.www.wifiseeker.model.Locations;
import com.codemo.www.wifiseeker.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by root on 4/14/17.
 */

public class DatabaseController extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "locations.db";
    private static final String TABLE_LOCATIONS = "locations";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LAT = "lat";
    private static final String COLUMN_LNG = "lng";
    private static final String COLUMN_OPEN = "open";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_REPORT = "report";

    public DatabaseController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LOCATIONS);
        String query = "CREATE TABLE " + TABLE_LOCATIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT ," +
                COLUMN_LAT + " DOUBLE ," +
                COLUMN_LNG + " DOUBLE ," +
                COLUMN_OPEN + " BOOLEAN ," +
                COLUMN_RATING + " DOUBLE ," +
                COLUMN_REPORT + " INT " +
                ");";
        try {

            db.execSQL(query);
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....oncreate....aaaaaaaaaaaaaaaaaaaaaa***************");
//            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LOCATIONS);
        }catch (SQLException e){
//            Toast.makeText(,"connection failed", Toast.LENGTH_SHORT).show();
            Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....failed....aaaaaaaaaaaaaaaaaaaaaa***************");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_LOCATIONS);
        onCreate(db);
    }

    // Add a  new location to the table
    public void addLocation(Locations location){
        ContentValues values = new ContentValues();
        values.put(COLUMN_RATING,location.getRating());
        values.put(COLUMN_NAME,location.getName());
        values.put(COLUMN_LAT,location.getLat());
//        values.put(COLUMN_ID,12);
        values.put(COLUMN_OPEN,location.isOpen());
        values.put(COLUMN_LNG,location.getLng());
        values.put(COLUMN_REPORT,location.getReport());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_LOCATIONS,null,values);
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....adding....aaaaaaaaaaaaaaaaaaaaaa***************");
        db.close();
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....closed....aaaaaaaaaaaaaaaaaaaaaa***************");
    }

    public  void deleteLocation(String id){
        SQLiteDatabase db = getWritableDatabase();
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....deleting report....aaaaaaaaaaaaaaaaaaaaaa***************");
        db.execSQL("DELETE FROM "+ TABLE_LOCATIONS+ " WHERE "+ COLUMN_ID+ " =\"" + id +"\";");
        db.close();
    }
    public  void updateReport(String id, Integer report){
        SQLiteDatabase db = getWritableDatabase();
        Integer newreport = report+1;
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....updating report....aaaaaaaaaaaaaaaaaaaaaa***************");
        db.execSQL("UPDATE "+ TABLE_LOCATIONS+ " SET report = "+newreport+" WHERE "+ COLUMN_ID+ " =\"" + id +"\";");
        db.close();
    }
    public  void updateRating(String id, Double rating){
        float currentRating = getRating(id);
        SQLiteDatabase db = getWritableDatabase();
        if(currentRating==0 || currentRating==0.0){
            // do nothing
        }else {
            rating = (currentRating+rating)/2.0;
        }

        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....updating rating....aaaaaaaaaaaaaaaaaaaaaa***************"+ rating);
        db.execSQL("UPDATE "+ TABLE_LOCATIONS+ " SET rating = "+rating+" WHERE "+ COLUMN_ID+ " =\"" + id +"\";");
        db.close();
    }

    public ArrayList<String[]> databaseTOString(){
        ArrayList<String[]> locationList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_LOCATIONS+ " WHERE 1 ";
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....start cursur....aaaaaaaaaaaaaaaaaaaaaa***************");
        // Cursor points to a location in your results
        Cursor c = db.rawQuery(query,null);
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....intiated cursur....aaaaaaaaaaaaaaaaaaaaaa***************");
        //Move to the first in your results
//        c.moveToFirst();
//        c.getColumnNames();
//         while (!c.isAfterLast()){
//             Log.v("rht","aaaaaaaaaaaaaaaaaaaa....inside while....aaaaaaaaaaaaaaaaaaaaaa***************");
//             if(c.getString(c.getColumnIndex("id"))!=null){
//                 Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside if cursur....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("lat")));
//                 dbString += c.getString(c.getColumnIndex("lat"));
//                 dbString += "\n";
//             }
//         }
        if(c.moveToFirst()){
            do{
                String[] dbString = new String[7];
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside while cursur....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("id"))+" "+c.getString(c.getColumnIndex("name"))+" "+c.getString(c.getColumnIndex("lat"))+" "+c.getString(c.getColumnIndex("lng"))+" "+c.getString(c.getColumnIndex("open")));
//                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside while cursur....aaaaaaaaaaaaaaaaaaaaaa***************"+);
//                String column1 = c.getString(2);
                dbString[0]=c.getString(c.getColumnIndex("id"));
                dbString[1]=c.getString(c.getColumnIndex("name"));
                dbString[2]=c.getString(c.getColumnIndex("lat"));
                dbString[3]=c.getString(c.getColumnIndex("lng"));
                dbString[4]=c.getString(c.getColumnIndex("open"));
                dbString[5]=c.getString(c.getColumnIndex("rating"));
                dbString[6]=c.getString(c.getColumnIndex("report"));
                locationList.add(dbString);

            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return locationList;
    }
    public ArrayList<String[]> getWifiList(String name){
        ArrayList<String[]> locationList = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_LOCATIONS+ " WHERE name ='"+name +"'";
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....start cursur getWifiList....aaaaaaaaaaaaaaaaaaaaaa***************");
        // Cursor points to a location in your results
        Cursor c = db.rawQuery(query,null);
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....intiated cursur getWifiList....aaaaaaaaaaaaaaaaaaaaaa***************");
        //Move to the first in your results
//        c.moveToFirst();
//        c.getColumnNames();
//         while (!c.isAfterLast()){
//             Log.v("rht","aaaaaaaaaaaaaaaaaaaa....inside while....aaaaaaaaaaaaaaaaaaaaaa***************");
//             if(c.getString(c.getColumnIndex("id"))!=null){
//                 Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside if cursur....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("lat")));
//                 dbString += c.getString(c.getColumnIndex("lat"));
//                 dbString += "\n";
//             }
//         }
        if(c.moveToFirst()){
            do{
                String[] dbString = new String[7];
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside while cursur getWifiList....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("id"))+" "+c.getString(c.getColumnIndex("name"))+" "+c.getString(c.getColumnIndex("lat"))+" "+c.getString(c.getColumnIndex("lng"))+" "+c.getString(c.getColumnIndex("open"))+" "+c.getString(c.getColumnIndex("rating"))+" "+c.getString(c.getColumnIndex("report")));
//                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside while cursur....aaaaaaaaaaaaaaaaaaaaaa***************"+);
//                String column1 = c.getString(2);
                dbString[0]=c.getString(c.getColumnIndex("id"));
                dbString[1]=c.getString(c.getColumnIndex("name"));
                dbString[2]=c.getString(c.getColumnIndex("lat"));
                dbString[3]=c.getString(c.getColumnIndex("lng"));
                dbString[4]=c.getString(c.getColumnIndex("open"));
                dbString[5]=c.getString(c.getColumnIndex("rating"));
                dbString[6]=c.getString(c.getColumnIndex("report"));
                locationList.add(dbString);

            }while(c.moveToNext());
        }
        c.close();
        db.close();
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....end of getWWifiList....aaaaaaaaaaaaaaaaaaaaaa*************** list length "+locationList.size());
        return locationList;
    }
    public float getRating(String id){
        ArrayList<String[]> locationList = new ArrayList<>();
        Float dbRating = new Float(1);
        String rating = new String();;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT rating FROM "+TABLE_LOCATIONS+ " WHERE id ='"+id +"'";
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....start cursur get rating....aaaaaaaaaaaaaaaaaaaaaa***************" + id);
        // Cursor points to a location in your results
        Cursor c = db.rawQuery(query,null);
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....intiated cursur. get rating...aaaaaaaaaaaaaaaaaaaaaa***************");
        //Move to the first in your results
//        c.moveToFirst();
//        c.getColumnNames();
//         while (!c.isAfterLast()){
//             Log.v("rht","aaaaaaaaaaaaaaaaaaaa....inside while....aaaaaaaaaaaaaaaaaaaaaa***************");
//             if(c.getString(c.getColumnIndex("id"))!=null){
//                 Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside if cursur....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("lat")));
//                 dbString += c.getString(c.getColumnIndex("lat"));
//                 dbString += "\n";
//             }
//         }
        if(c.moveToFirst()){
            do{
//                rating = new String();
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside while cursur get rating....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("rating")));
                rating=c.getString(c.getColumnIndex("rating"));
//                locationList.add(dbString);
//                break;

            }while(c.moveToNext());
        }
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....outside while cursur get rating....aaaaaaaaaaaaaaaaaaaaaa***************");
        c.close();
        db.close();
        dbRating = Float.parseFloat(rating);
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....before rating....aaaaaaaaaaaaaaaaaaaaaa***************"+dbRating);

        if(dbRating>=5){
            dbRating= 5.0f;
        }else if(dbRating>=4){
            dbRating= 4.0f;
        }else if(dbRating>=3){
            dbRating= 3.0f;
        }else if(dbRating>=2){
            dbRating= 2.0f;
        }else if(dbRating>=1){
            dbRating= 1.0f;
        }else{
            dbRating= Float.valueOf(0f);
        }
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....  after asigning rating....aaaaaaaaaaaaaaaaaaaaaa***************"+dbRating);
//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....rating is ....aaaaaaaaaaaaaaaaaaaaaa***************"+dbRating);
        return dbRating;
    }

    public String[] getDetails(String id){
        ArrayList<String[]> locationList = new ArrayList<>();
        String[] locationDetails = new String[3];
//        Float dbRating = new Float(1);
//        String rating = new String();;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_LOCATIONS+ " WHERE id ='"+id +"'";
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....start cursur get details....aaaaaaaaaaaaaaaaaaaaaa***************" + id);
        // Cursor points to a location in your results
        Cursor c = db.rawQuery(query,null);
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....intiated cursur. get details...aaaaaaaaaaaaaaaaaaaaaa***************");
        //Move to the first in your results
//        c.moveToFirst();
//        c.getColumnNames();
//         while (!c.isAfterLast()){
//             Log.v("rht","aaaaaaaaaaaaaaaaaaaa....inside while....aaaaaaaaaaaaaaaaaaaaaa***************");
//             if(c.getString(c.getColumnIndex("id"))!=null){
//                 Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside if cursur....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("lat")));
//                 dbString += c.getString(c.getColumnIndex("lat"));
//                 dbString += "\n";
//             }
//         }
        if(c.moveToFirst()){
            do{
//                rating = new String();
                Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....inside while cursur get details....aaaaaaaaaaaaaaaaaaaaaa***************"+c.getString(c.getColumnIndex("rating")));

                locationDetails[0]=c.getString(c.getColumnIndex("name"));
                locationDetails[1]=c.getString(c.getColumnIndex("lat"));
                locationDetails[2]=c.getString(c.getColumnIndex("lng"));
//                locationList.add(dbString);
//                break;

            }while(c.moveToNext());
        }
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....outside while cursur get details....aaaaaaaaaaaaaaaaaaaaaa***************");
        c.close();
        db.close();
//        dbRating = Float.parseFloat(rating);
//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa.....before rating....aaaaaaaaaaaaaaaaaaaaaa***************");


//        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....  after asigning rating....aaaaaaaaaaaaaaaaaaaaaa***************");
        Log.v("rht","aaaaaaaaaaaaaaaaaaaa....end of getting details....aaaaaaaaaaaaaaaaaaaaaa***************");
        return locationDetails;
    }
}
