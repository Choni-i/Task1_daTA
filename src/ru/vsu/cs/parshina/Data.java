package ru.vsu.cs.parshina;

import java.util.Arrays;
import java.util.HashSet;

public class Data {


        private int days_all;
        int day;
        int month;
        int year;
        Integer[] Months_30 = {
            4, 6, 9, 11
        };
        HashSet<Integer> Month_30 = new HashSet<>(Arrays.asList(Months_30));
        Integer[] Months_31 = {
            1, 3, 5, 7, 8, 10, 12
        };
        HashSet<Integer> Month_31 = new HashSet<>(Arrays.asList(Months_31));

        public Data(int day, int month, int year ){
            this.day = day;
            this.month = month;
            this.year = year;
            int feb;
            if (year%4==0){
                feb = 29;
            } else{
                feb = 28;
            }
            year--;//чтобы не возиться когда у нас введенный год високос
            int years_visokos =(year/4) - year/100 + year/400;
            int years_not_visokos = year-years_visokos;
            days_all = years_visokos*366 +years_not_visokos*365;
            for (int i = 1; i < month; i++) {
                if (i==2){
                    days_all+=feb;
                }
                    if (Month_30.contains(i)) {
                        days_all +=30;
                    } else if (Month_31.contains(i)){
                        days_all +=31;
                    }
                }
            days_all +=day;
        }

        public String day_in_form(int days) {
            int years = 1;
            int months = 1;
           // days++;
            years += days/365;
            int days_visokos =(years/4) - years/100 + years/400;
            days-= (years-1)* 365;
            days -= days_visokos;
            //days += years/100;
            for (int i = 1; i <= 12; i++) {
                if (days<28){
                    break;}
                if (i==2){
                    if (years%4==0){
                        days-=29;
                    }else {
                        days-=28;
                    }
                    months++;
                }
                if (Month_30.contains(i)) {
                    days -=30;
                    months++;
                } else if (Month_31.contains(i)){
                    days -=31;
                    months++;
                }

        }
            String day = Integer.toString(days);
            String month = Integer.toString(months);
            StringBuilder year = new StringBuilder(Integer.toString(years));
            if (day.length()!=2){
                day = "0"+day;
            }
            if (month.length()!=2){
                month = "0"+month;
            }
            while (year.length()!=4){
                year.insert(0, "0");
            }

            return day + "." + month + "." + year;
        }

    public void addDays(int days) {
            days_all+=days;
        // System.out.println(day_in_form(days_all));
       day_in_form(days_all);

    }
    public void subDays(int days) {

    }
    public void addYears(int days) {
            int year_notVisokos = days/4;
            int year = days - year_notVisokos;
        days_all+=year*365+year_notVisokos*366 ;
        // System.out.println(day_in_form(days_all));
       day_in_form(days_all);

    }

}


