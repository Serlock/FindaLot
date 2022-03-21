package com.example.googlemapsdemo;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private static ArrayList<Location> locations;
    private static User user;
    private static Location location;

    public Utils() {
        if (locations == null){
            locations = new ArrayList<>();
        }
    }

    public static Utils get_instance() {
        if (null != instance){
            return instance;
        }
        else{
            instance = new Utils();
            return instance;
        }
    }

    public static void setUser(int id, String name, String surname, String email, String password, String country, String city, String district){
        user = new User(id, name, surname, email, password, country, city, district);
    }

    public static void setLocation(int id, int userId, double longitude, double latitude, String addressLine, String countryName, String adminArea, String subAdminArea){
        location = new Location(id, userId, longitude, latitude, addressLine, countryName, adminArea, subAdminArea);
    }

    public static Location getLocation(){
        return location;
    }

    public static User getUser(){
        return user;
    }

    public static Location getLocationById(int locationId){
        for (Location location : locations){
            if (location.getId() == locationId){
                return location;
            }
        }
        return null;
    }

    public static ArrayList<Location> getLocations() {
        return locations;
    }

    public static boolean addToLocations(Location location){
        return locations.add(location);
    }

    public static void clearLocations(){
        locations.clear();
    }
}
