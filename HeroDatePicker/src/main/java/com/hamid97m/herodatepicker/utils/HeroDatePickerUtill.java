package com.hamid97m.herodatepicker.utils;

import android.annotation.SuppressLint;

import java.util.Calendar;
import java.util.Date;

public class HeroDatePickerUtill {

    private int day, month, year;
    private int jYear, jMonth, jDay;
    private int gYear, gMonth, gDay;
    private int leap, march;

    /**
     * Calculates the Julian Day number (JG2JD) from Gregorian or Julian
     * <p>
     * calendar dates. This integer number corresponds to the noon of the date
     * <p>
     * (i.e. 12 hours of Universal Time). The procedure was tested to be good
     * <p>
     * since 1 March, -100100 (of both the calendars) up to a few millions
     * <p>
     * (10**6) years into the future. The algorithm is based on D.A. Hatcher,
     * <p>
     * Q.Jl.R.Astron.Soc. 25(1984), 53-55 slightly modified by me (K.M.
     * <p>
     * Borkowski, Post.Astron. 25(1987), 275-279).
     *
     * @param year  int
     * @param month int
     * @param day   int
     * @param J1G0  to be set to 1 for Julian and to 0 for Gregorian calendar
     * @return Julian Day number
     */
    private int JG2JD(int year, int month, int day, int J1G0) {
        int jd = (1461 * (year + 4800 + (month - 14) / 12)) / 4
                + (367 * (month - 2 - 12 * ((month - 14) / 12))) / 12
                - (3 * ((year + 4900 + (month - 14) / 12) / 100)) / 4 + day
                - 32075;
        if (J1G0 == 0) {
            jd = jd - (year + 100100 + (month - 8) / 6) / 100 * 3 / 4 + 752;
        }
        return jd;
    }

    /**
     * Calculates Gregorian and Julian calendar dates from the Julian Day number
     * <p>
     * (JD) for the period since JD=-34839655 (i.e. the year -100100 of both the
     * <p>
     * calendars) to some millions (10**6) years ahead of the present. The
     * <p>
     * algorithm is based on D.A. Hatcher, Q.Jl.R.Astron.Soc. 25(1984), 53-55
     * <p>
     * slightly modified by me (K.M. Borkowski, Post.Astron. 25(1987), 275-279).
     *
     * @param JD   Julian day number as int
     * @param J1G0 to be set to 1 for Julian and to 0 for Gregorian calendar
     */
    private void JD2JG(int JD, int J1G0) {
        int i, j;
        j = 4 * JD + 139361631;
        if (J1G0 == 0) {
            j = j + (4 * JD + 183187720) / 146097 * 3 / 4 * 4 - 3908;
        }
        i = (j % 1461) / 4 * 5 + 308;
        gDay = (i % 153) / 5 + 1;
        gMonth = ((i / 153) % 12) + 1;
        gYear = j / 1461 - 100100 + (8 - gMonth) / 6;
    }

    /**
     * Converts the Julian Day number to a date in the Jalali calendar
     *
     * @param JDN the Julian Day number
     */
    private void JD2Jal(int JDN) {
        JD2JG(JDN, 0);
        jYear = gYear - 621;
        JalCal(jYear);
        int JDN1F = JG2JD(gYear, 3, march, 0);
        int k = JDN - JDN1F;
        if (k >= 0) {
            if (k <= 185) {
                jMonth = 1 + k / 31;
                jDay = (k % 31) + 1;
                return;
            } else {
                k = k - 186;
            }
        } else {
            jYear = jYear - 1;
            k = k + 179;
            if (leap == 1) {
                k = k + 1;
            }
        }
        jMonth = 7 + k / 30;
        jDay = (k % 30) + 1;
    }

    /**
     * Converts a date of the Jalali calendar to the Julian Day Number
     *
     * @param jY Jalali year as int
     * @param jM Jalali month as int
     * @param jD Jalali day as int
     * @return Julian day number
     */
    private int Jal2JD(int jY, int jM, int jD) {
        JalCal(jY);
        return JG2JD(gYear, 3, march, 1) + (jM - 1) * 31 - jM / 7 * (jM - 7) + jD - 1;
    }

    /**
     * This procedure determines if the Jalali (Persian) year is leap (366-day
     * <p>
     * long) or is the common year (365 days), and finds the day in March
     * <p>
     * (Gregorian calendar) of the first day of the Jalali year (jYear)
     *
     * @param jY Jalali calendar year (-61 to 3177)
     */
    private void JalCal(int jY) {
        march = 0;
        leap = 0;
        int[] breaks = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
                1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        gYear = jY + 621;
        int leapJ = -14;
        int jp = breaks[0];
        int jump = 0;
        for (int j = 1; j <= 19; j++) {
            int jm = breaks[j];
            jump = jm - jp;
            if (jY < jm) {
                int N = jY - jp;
                leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4;
                if ((jump % 33) == 4 && (jump - N) == 4) {
                    leapJ = leapJ + 1;
                }
                int leapG = (gYear / 4) - (gYear / 100 + 1) * 3 / 4 - 150;
                march = 20 + leapJ - leapG;
                if ((jump - N) < 6) {
                    N = N - jump + (jump + 4) / 33 * 33;
                }
                leap = ((((N + 1) % 33) - 1) % 4);
                if (leap == -1) {
                    leap = 4;
                }
                break;
            }
            leapJ = leapJ + jump / 33 * 8 + (jump % 33) / 4;
            jp = jm;
        }
    }

    public static boolean isFarsiLeap(int jY) {
        int march = 0;
        int leap = 0;
        int[] breaks = {-61, 9, 38, 199, 426, 686, 756, 818, 1111, 1181, 1210,
                1635, 2060, 2097, 2192, 2262, 2324, 2394, 2456, 3178};
        int gYear = jY + 621;
        int leapJ = -14;
        int jp = breaks[0];
        int jump = 0;
        for (int j = 1; j <= 19; j++) {
            int jm = breaks[j];
            jump = jm - jp;
            if (jY < jm) {
                int N = jY - jp;
                leapJ = leapJ + N / 33 * 8 + (N % 33 + 3) / 4;
                if ((jump % 33) == 4 && (jump - N) == 4) {
                    leapJ = leapJ + 1;
                }
                int leapG = (gYear / 4) - (gYear / 100 + 1) * 3 / 4 - 150;
                march = 20 + leapJ - leapG;
                if ((jump - N) < 6) {
                    N = N - jump + (jump + 4) / 33 * 33;
                }
                leap = ((((N + 1) % 33) - 1) % 4);
                if (leap == -1) {
                    leap = 4;
                }
                break;
            }
            leapJ = leapJ + jump / 33 * 8 + (jump % 33) / 4;
            jp = jm;
        }
        return leap == 1;
    }

    /**
     * Modified toString() method that represents date string
     *
     * @return Date as String
     */
    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        return String.format("%04d-%02d-%02d", getYear(), getMonth(), getDay());
    }

    /**
     * Converts Gregorian date to Persian(Jalali) date
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    public void gregorianToPersian(int year, int month, int day) {
        int jd = JG2JD(year, month, day, 0);
        JD2Jal(jd);
        this.year = jYear;
        this.month = jMonth;
        this.day = jDay;
    }

    public void gregorianToPersian(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        gregorianToPersian(cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Converts Persian(Jalali) date to Gregorian date
     *
     * @param year  int
     * @param month int
     * @param day   int
     */
    public void persianToGregorian(int year, int month, int day) {
        int jd = Jal2JD(year, month, day);
        JD2JG(jd, 0);
        this.year = gYear;
        this.month = gMonth;
        this.day = gDay;
    }

    /**
     * Get manipulated day
     *
     * @return Day as int
     */
    public int getDay() {
        return day;
    }

    /**
     * Get manipulated month
     *
     * @return Month as int
     */
    public int getMonth() {
        return month;
    }

    /**
     * Get manipulated year
     *
     * @return Year as int
     */
    public int getYear() {
        return year;
    }
}