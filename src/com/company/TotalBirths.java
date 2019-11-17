package com.company;

import org.apache.commons.csv.*;
import edu.duke.*;
import java.io.File;

public class TotalBirths {

    // Print birth counts based on a file
    public void totalBirths(FileResource fr){
        int totalBirths = 0;
        int totalGirls = 0;
        int totalBoys = 0;
        int totalBoyNames = 0;
        int totalGirlNames = 0;

        // Iterate over each row in the file
        for(CSVRecord rec : fr.getCSVParser(false)){
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;

            if(rec.get(1).equals("M")){
                totalBoys += numBorn;
                totalBoyNames++;
            } else {
                totalGirls += numBorn;
                totalGirlNames++;
            }
        }

        // Print out the important information
        System.out.println("Total births: " + totalBirths);
        System.out.println("Total boys: " + totalBoys);
        System.out.println("Total girls: " + totalGirls);
        System.out.println("Total boy names: " + totalBoyNames);
        System.out.println("Total girl names: " + totalGirlNames);
    }

    // Find rank based on year, name and gender
    public int getRank(int year, String name, String gender){
        int rank = -1;
        int count = 0;
        int girlCount = 0;

        // Find the correct file and iterate over it
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        for(CSVRecord rec : fr.getCSVParser(false)){
            count++;

            // Count all females
            if(rec.get(1).equals("F")){
                girlCount++;
            }

            // If name and gender is correct
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                if(gender.equals("F")){
                    return girlCount;

                    // Remove the girl count since they appear above the boys in the file
                } else {
                    return count - girlCount;
                }
            }
        }
        return rank;
    }

    // Find name based on year, gender and rank
    public String getName(int year, String gender, int rank){
        int count = 0;
        int femaleCount = 0;

        // Find the correct file and iterate over it
        FileResource newFr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        for(CSVRecord rec : newFr.getCSVParser(false)) {
            count++;

            // Count females
            if(rec.get(1).equals("F")){
                femaleCount++;
            }

            // If female and correct rank
            if(gender.equals("F") && rec.get(1).equals("F") && rank == femaleCount) {
                return rec.get(0);
            }

            // If male and correct rank
            if(gender.equals("M") && rec.get(1).equals("M") && rank == count - femaleCount){
                return rec.get(0);
            }
        }

        // If not present, return NO NAME
        return "NO NAME";
    }


    // Find the name rank in year1 and return the name with the same rank in year2
    public void whatIsNameInYear(String name, int year1, int year2, String gender){

        // Find rank in year 1
        int currentRank = getRank(year1, name, gender);

        // Find name in current rank in year 2
        String newName = getName(year2, gender, currentRank);

        // Print the results
        if(currentRank <= -1){
            System.out.println(name + " was not found in the " + year1 + " data.");
        } else if(newName == ""){
            System.out.println("Could not find the name..");
        } else {
            System.out.println(name + " born in " + year1 + " would be " + newName + " if born in " + year2);
        }
    }

    // Find the year a name had the highest rank
    public int yearOfHighestRank(String name, String gender){
        int highestSoFar = -1;
        int highestYear = 0;

        // Iterate over the files you choose in the popup window
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){

            // Get the year and find the current rank
            int year = Integer.parseInt(f.getName().substring(3,7));
            int currentRank = getRank(year, name, gender);

            // If the name is present and the rank is higher than previous
            if(highestYear == 0 || highestSoFar == -1 || currentRank != -1 && currentRank < highestSoFar){
                highestYear = year;
                highestSoFar = currentRank;
            }
        }
        return highestYear;
    }

    // Find the average rank for a name
    public double getAverageRank(String name, String gender){
        int sumSoFar = 0;
        double count = 0.0;

        // Iterate over the files you choose in the popup window
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            int year = Integer.parseInt(f.getName().substring(3,7));
            int currentRank = getRank(year, name, gender);

            // If the name is present, count the results
            if(currentRank > 0){
                count++;
                sumSoFar += currentRank;
            }
        }
        // Return the average
        if(count == 0.0){
            return 0.0;
        } else {
            return sumSoFar / count;
        }
    }

    public int getTotalBirthsRankedHigher(int year, String name, String gender){
        int totalBirths = 0;

        // Find the correct file and iterate over it
        FileResource fr = new FileResource("data/us_babynames_by_year/yob" + year + ".csv");
        for(CSVRecord rec : fr.getCSVParser(false)){

            // If the current name is correct, return totalBirths
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)){
                return totalBirths;
            }

            // Add the birth counts if gender is correct.
            if(rec.get(1).equals(gender)){
                totalBirths += Integer.parseInt(rec.get(2));
            }
        }
        return totalBirths;
    }


    // Tests
    public void testTotalBirths(){
        FileResource fr = new FileResource("data/us_babynames_test/example-small.csv");
        totalBirths(fr);
    }

    public void testGetRank(){
        int rank = getRank(2012, "Ava", "F");
        System.out.println("Rank is: " + rank);
    }

    public void testWhatIsNameInYear(){
        whatIsNameInYear("Mason", 2012, 2014, "M");
    }

    public void testYearOfHighestRank(){
        int testRank = yearOfHighestRank("Mason", "M");
        System.out.println("Mason' highest rank is: " + testRank);
    }

    public void testGetAverageRank(){
        double masonRank = getAverageRank("Mason", "M");
        System.out.println("Masons rank is: " + masonRank);
    }

    public void testGetTotalBirthsRankedHigher(){
        int ethanRank = getTotalBirthsRankedHigher(2012, "Ethan", "M");
        System.out.println("Ethans rank is: " + ethanRank);
    }
}
