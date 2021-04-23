package ru.vsu.cs.parshina;

import java.util.Arrays;
import java.util.HashSet;

/*
    1. Месяцы - в один массив с количеством дней в каждом месяце
    2. Для рассчета года пока просто проходите по годам от 0 до бесконечности и суммируйте количество дней в них
    3. Вместо полей day, month, year - вычислять их каждый раз
    4. Форматирование -- просто по проходу по строке
 */

public class Data implements Comparable <Data>{

    private class CalculatedDate {
        public int day, month, year;

        public CalculatedDate(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }
    }

        int [] Month_notLeap = {31, 28, 31, 30, 31, 30, 31, 31,30, 31, 30, 31};
        int [] Month_isLeap = {31, 29, 31, 30, 31, 30, 31, 31,30, 31, 30, 31};
    private int days;


    public Data(int day, int month, int year){
        this.days = parseDateToDays(day, month, year);
    }


    private int parseDateToDays(int day, int month, int year) {//перевод в дни с начала летоисчесления
        checkData(year, month, day);
        this.days += (year - 1) * 365 + countLeapYears(1, year - 1);
        if (isLeapYear(year)){
            for (int i = 0; i <month-1; i++) {
                this.days+=Month_isLeap[i];
            }
        } else {
            for (int i = 0; i <month-1; i++) {
                this.days+=Month_notLeap[i];
            }
        }
        this.days += --day;
        return days;
    }

    private void checkData(int year, int month, int day) { // проверка на корректность
        if (year <= 0) {
            throw new IllegalArgumentException("Некорректно введен год!");
        } else if (month <= 0 || month > 12) {
            throw new IllegalArgumentException("Некорректно введен месяц!");
        } else {
            int daysToMonth = 0;
            if(isLeapYear(year) ){
                daysToMonth =Month_isLeap[month-1];
            } else {
                daysToMonth =Month_notLeap[month-1];
            }
            if (day <= 0 || day > daysToMonth) {
                throw new IllegalArgumentException("Некорректно введен день!");
            }
        }
    }

    private boolean isLeapYear(int year) {//когда вод високосный
       return year > 0 && year % 4 == 0 && (year % 100 != 0 || (year % 100 == 0 && year % 400 == 0));
    }

    private int countLeapYears(int start, int end) {//кол-во високосных лет
        int count = 0;
        for (int year = start; year <= end; year++) {
            if (isLeapYear(year)) {
                count++;
            }
        }
        return count;
    }


    private CalculatedDate parseDaysToDate() {//из дней в дату
        int year = 1;
        int month = 1;
        int day;
        int days_cur = this.days;
        while(this.days>366){
            if (isLeapYear(year)) {
                this.days-=366;
            } else {
                this.days-=365;
            }
            year++;
        }
        //int year = 1;
        // days++;
        /*year += days/365+1;
        year -= countLeapYears(1, year)/ 365;
        //days += years/100;
        int daysDiv = days - ((year - 1) * 365 + countLeapYears(1, year - 1));
        if ( daysDiv < 1) {
            year = days / 365;
            year -= countLeapYears(1, year) / 365;
            daysDiv = days - ((year - 1) * 365 + countLeapYears(1, year - 1));
        }*/
        int searchDay = 0;
        if (isLeapYear(year)){
            for (int i = 0; i <12; i++) {
                if (searchDay + Month_isLeap[i] <=days) {
                    searchDay +=  Month_isLeap[i];
                    month++;
                } else {
                    break;
                }
            }
        } else {
            for (int i = 0; i <12; i++) {
                if (searchDay + Month_notLeap[i] <=days) {
                    searchDay +=  Month_notLeap[i];
                   month++;
                } else {
                    break;
                }
            }
        }
        /*if (month == 0){
            month++;
        }*/
        if (month == 13){
            year++;
            month = 1;
        }
            day = this.days - searchDay + 1;
            this.days = days_cur;
        return new CalculatedDate(day, month, year);
        //System.out.println(Print(day, month, year));


    }

    public void addDays(int day) {
        this.days+=day;

    }
    public void subDays(int day) {
        this.days-=day;
    }
    public void addYears(int year) {
        this.days += year*365;
        this.days += countLeapYears(getYear(),getYear()+year);
    }
    public void subYears(int year) {
        this.days -= year*365;
        this.days -= countLeapYears(getYear(),getYear()+year);
    }

    public void addMonths(int month) {
        int year = getYear();
        int d;
        if (isLeapYear(year)) {
            for (int i = getMonth() - 1, j = 0; j < month; i++, j++) {
                d = Month_isLeap[i];
                this.days += d;
                if (i == 11) {
                    i = -1;
                }
            }
        } else {
            for (int i = getMonth() - 1, j = 0; j < month; i++, j++) {

                d = Month_notLeap[i];
                this.days += d;
                if (i == 11) {
                    i = -1;
                }
            }
        }
    }

    public void subMonths(int month) {
        int year = getYear();
        parseDaysToDate();
        int d;
        if (isLeapYear(year)) {
            for (int i = getMonth() - 1, j = 0; j < month; i--, j++) {
                if (i < 0) {
                    i = 11;
                }
                d = Month_isLeap[i];
                this.days -= d;

            }
        } else {
            for (int i = getMonth() - 2, j = 0; j < month; i--, j++) {
                if (i < 0) {
                    i = 11;
                }
                d = Month_notLeap[i];
                this.days -= d;

            }
        }

    }


    public static Data ReadLine(String line){//Разбор строки 
        String[] parts = line.split("-");
        return new Data(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
    }

    /*private char getPrefix(String pattern) {
        char prefix = 0;
        pattern = pattern.replace("D", "").replaceAll("M", "").replaceAll("Y", "");
        if (pattern.length() > 0) {
            prefix = pattern.charAt(0);
        }
        return prefix;
    }*/

    public String format(String pattern) {
        //parseDaysToDate();
        //char prefix = getPrefix(pattern);
        //String[] formats = pattern.split("");
        //String[] formats = getPatternWithoutPrefix(pattern, prefix);
        //String pref = String.valueOf(prefix);
        //String[] formats = pattern.split(pref);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < pattern.length()) {
            int dCount = 0, mCount = 0, yCount = 0;
            while (i < pattern.length() && pattern.charAt(i) == 'D') {
                dCount++;
                i++;
            }

            if (dCount > 0) {
                // append day
                sb.append(getDayy(dCount));
            }

            while (i < pattern.length() && pattern.charAt(i) == 'Y') {
                yCount++;
                i++;
            }
            if (yCount > 0) {
                // append day
                sb.append(getYearr(yCount));
            }
            while (i < pattern.length() && pattern.charAt(i) == 'M') {
                mCount++;
                i++;
            }
            if (mCount > 0) {
                // append day
                sb.append(getMonthh(mCount));
            }

            // Y
            // M

            if (dCount == 0 && yCount == 0 && mCount == 0){
                sb.append(pattern.charAt(i));
                i++;
            }
        }

        return sb.toString();
        // return sb.toString();
    }

    private String getMonthh(int k) {
        String receivedMonth = "";
        switch (k) {
            case 2:
                if (getMonth()<10){
                    receivedMonth = String.format("%s%d", "0", getMonth());
                } else {
                    receivedMonth = String.format("%s%d", "", getMonth());
                }
                break;
            case 1:
                receivedMonth = Integer.toString(getMonth() % 10);
                break;
        }
        return receivedMonth;
    }

    private String getYearr(int k) {
        int receivedYear = 0;
        switch (k) {
            case 2:
                receivedYear = getYear() % 100;
                break;
            case 4:
                receivedYear = getYear();
                break;
        }
        return Integer.toString(receivedYear);
    }

    private String getDayy(int k) {
        String receivedDay = "";
        switch (k) {
            case 1:
                receivedDay = Integer.toString(getDay() % 10);
                break;
            case 2:
                if (getDay()<10){
                    receivedDay = String.format("%s%d", "0", getDay());
                } else {
                    receivedDay = String.format("%s%d", "", getDay());
                }
                break;
        }
        return receivedDay;
    }

    public int getYear() {
        return parseDaysToDate().year;
    }

    public int getMonth() {
        return parseDaysToDate().month;
    }
    public int getDay() {
        return parseDaysToDate().day;
    }

    @Override
    public int compareTo(Data o) {
        if(this.days==o.days){
        return 0;} else return -1;
    }

    /*private String[] getPatternWithoutPrefix(String pattern, char prefix) {
        String pref = String.valueOf(prefix);
        String[] formats = pattern.split(pref);
        //pattern = pattern.trim();
        parseDaysToDate();
        int d;

        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < pattern.length(); index++) {
            if (pattern.charAt(index) != prefix) {
                sb.append(pattern.charAt(index));
            } else {
                sb.append("\n");
            }
        }
        formats = sb.toString().split("\n");

        return formats;
    }*/

}


