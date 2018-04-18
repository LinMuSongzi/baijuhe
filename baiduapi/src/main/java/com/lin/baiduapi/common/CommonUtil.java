package com.lin.baiduapi.common;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import com.lin.baiduapi.BaiduMapImp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by linhui on 2018/3/28.
 */
public class CommonUtil {


    private static final String TAG = "CommonUtil";
    private static final LocationListener L = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i(TAG, "onLocationChanged: ");
            //得到纬度
            double latitude = location.getLatitude();
            //得到经度
            double longitude = location.getLongitude();
            chechCity(latitude, longitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i(TAG, "onStatusChanged: ");

        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i(TAG, "onProviderEnabled: ");

        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i(TAG, "onProviderDisabled: ");

        }
    };

    /**
     * 获取经纬度
     *
     * @return
     */
    public static String getLngAndLat() {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) BaiduMapImp.getInstance().getContext().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            } else {//当GPS信号弱没获取到位置的时候又从网络获取
                return getLngAndLatWithNetwork();
            }
        } else {    //从网络获取经纬度
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, L);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }
        }
        chechCity(latitude,longitude);
        Log.i(TAG, "getLngAndLat: "+longitude + "," + latitude);
        return longitude + "," + latitude;
    }

    //从网络获取经纬度
    public static String getLngAndLatWithNetwork() {
        double latitude = 0.0;
        double longitude = 0.0;
        LocationManager locationManager = (LocationManager) BaiduMapImp.getInstance().getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, L);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        Log.i(TAG, "getLngAndLatWithNetwork: "+longitude + "," + latitude);
        chechCity(latitude,longitude);
        return longitude + "," + latitude;
    }


    public static void getLocationManager() {

        LocationManager locationManager = (LocationManager) BaiduMapImp.getInstance().getContext().getSystemService(Context.LOCATION_SERVICE);
        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);//1.通过GPS定位，较精确。也比較耗电
        LocationProvider netProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);//2.通过网络定位。对定位精度度不高或省点情况可考虑使用

        if (gpsProvider != null || netProvider != null) {

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, L);
        }


    }

    public static void chechCity(double latitude, double longitude) {


        Geocoder gc = new Geocoder(BaiduMapImp.getInstance().getContext(), Locale.getDefault());
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Address address = locationList.get(0);//得到Address实例
        //Log.i(TAG, "address =" + address);
        String countryName = address.getCountryName();//得到国家名称，比方：中国
        Log.i(TAG, "countryName = " + countryName);
        String locality = address.getLocality();//得到城市名称，比方：北京市
        Log.i(TAG, "locality = " + locality);
        for (int i = 0; address.getAddressLine(i) != null; i++) {
            String addressLine = address.getAddressLine(i);//得到周边信息。包含街道等。i=0，得到街道名称
            Log.i(TAG, "addressLine = " + addressLine);
        }
    }


}
