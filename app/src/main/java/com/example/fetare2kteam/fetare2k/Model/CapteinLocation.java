package com.example.fetare2kteam.fetare2k.Model;

import com.google.firebase.Timestamp;
import com.google.firebase.database.ServerValue;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class CapteinLocation {
    GeoPoint geoPoint;

  //  private Map<String,String> latestUpdateTimestamp;
    // String timestamp;
    Timestamp timestamp;
    public void CaptainLocation(){
        geoPoint=new GeoPoint(0.0,0.0);
     //   timestamp=null;
    }


   /* public CapteinLocation(GeoPoint geoPoint, Timestamp timestamp) {
        this.geoPoint = new GeoPoint(geoPoint.getLatitude(),geoPoint.getLongitude());
        this.timestamp = timestamp;

    }*/
    public CapteinLocation(GeoPoint Poin) {
        this.geoPoint = new GeoPoint(Poin.getLatitude(),Poin.getLongitude());
     //   latestUpdateTimestamp= (Map<String, String>) FieldValue.serverTimestamp();
        //firebase.firestore.Timestamp.fromDate(new Date());
        timestamp=new Timestamp(new Date());
    }


    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

  /*  public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }*/
}
