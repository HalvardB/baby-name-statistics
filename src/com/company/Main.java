package com.company;

import edu.duke.*;

public class Main {

    public static void main(String[] args) {
        TotalBirths totalBirths = new TotalBirths();

        // Tests
        // totalBirths.testTotalBirths();
        // totalBirths.testGetRank();
        // totalBirths.testWhatIsNameInYear();
        // totalBirths.testYearOfHighestRank();
        // totalBirths.testGetAverageRank();
        // totalBirths.testGetTotalBirthsRankedHigher();

        FileResource fr = new FileResource("data/us_babynames_by_year/yob1905.csv");

        // Quiz questions
        // totalBirths.totalBirths(fr);
        // int answer = totalBirths.getRank(1971,"Frank", "M");
        // String answer = totalBirths.getName(1982, "M", 450);
        // String answer = totalBirths.getName(1980, "F", 350);
        // totalBirths.whatIsNameInYear("Owen", 1974, 2014, "M");
        // int answer = totalBirths.yearOfHighestRank("Mich", "M");
        // double answer = totalBirths.getAverageRank("Robert", "M");
        // int answer = totalBirths.getTotalBirthsRankedHigher(1990, "Drew", "M");
        // System.out.println("The answer is: " + answer);
    }
}
