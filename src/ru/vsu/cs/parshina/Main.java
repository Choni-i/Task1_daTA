package ru.vsu.cs.parshina;


import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дату(в формате дд-мм-гггг):");
        String line = scanner.nextLine();
        Data data1 = Data.ReadLine(line);
     System.out.println(data1.format("DD/MM/YYYY"));
        data1.addYears(18);
        System.out.println(data1.format("DD/MM/YYYY"));
        data1.subYears(18);
        //data1.format("DD/MM/YYYY");
         System.out.println(data1.format("DD/MM/YYYY"));
       data1.addMonths(3);
        System.out.println(data1.format("DD/MM/YYYY"));
        data1.subMonths(3);
        System.out.println(data1.format("DD/MM/YYYY"));
        data1.addDays(3);
        System.out.println(data1.format("DD/MM/YYYY"));
        data1.subDays(3);
        System.out.println(data1.format("DD*MM/YYYY"));


	    Data data3 = new Data(3, 3, 13);
        Data data4 = new Data(3, 3, 13);
        //data.addDays(15);
        System.out.println(data3.compareTo(data1));
        System.out.println(data3.compareTo(data4));
        System.out.println(data3.format("DD-MM-YYYY"));
        System.out.println(data3.format("D+M+YY"));
        //   data3.format("DD/MM/YYYY");
    }
}
