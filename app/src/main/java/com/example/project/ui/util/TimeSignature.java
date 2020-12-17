package com.example.project.ui.util;

public class TimeSignature {
    public static String secondsToTime(int seconds){
        final int SEC_IN_MIN = 60;
        final int SEC_IN_HR = 60 * 60;
        final int SEC_IN_DAY = 24 * 60 * 60;
        final int SEC_IN_YEAR = 24 * 60 * 60 * 365;

        // gets the largest whole number of years
        int yrs = seconds / SEC_IN_YEAR;
        seconds %= SEC_IN_YEAR;

        // gets the largest whole number of days on whats left
        int days = seconds / SEC_IN_DAY;
        seconds %= SEC_IN_DAY;

        // repeat for hours
        int hrs = seconds / SEC_IN_HR;
        seconds %= SEC_IN_HR;

        // repeat for minutes
        int mins = seconds / SEC_IN_MIN;
        seconds %= SEC_IN_MIN;

        // return different strings based on which time level is set
        if (yrs > 0) return yrs + "y " + days + "d";
        if (days > 0) return days + "d " + hrs + "h";
        else if (hrs > 0) return hrs + "h " + mins + "m";
        else if (mins > 0) return mins + "m " + seconds + "s";
        else return seconds + "s";
    }
}
