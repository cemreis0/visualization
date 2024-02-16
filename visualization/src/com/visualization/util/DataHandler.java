package com.visualization.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DataHandler implements Serializable {
    // constructors
    public DataHandler() {
        // default constructor
    }

    // class methods
    public List<String> extractDataFromFile(File file, String country, int year) {
        List<String> data = extractAllDataFromFile(file);
        List<String> filteredData = new ArrayList<>();

        // iterate through each line in the data, skipping the first
        for (int i = 1; i < data.size(); i++) {
            String line = data.get(i);

            // split the line by comma assuming it's a CSV format
            String[] parts = line.split(",");

            // assuming the country is at index 0 and year is at index 2
            if (parts.length >= 4 && parts[0].equals(country) && (Integer.parseInt(parts[2]) == year)) {
                // if the line matches the specified country and year, add it to filteredData
                filteredData.add(line);
            }
        }

        return filteredData;
    }
    public List<String> extractDataFromFile(File file, String country) {
        List<String> data = extractAllDataFromFile(file);
        List<String> filteredData = new ArrayList<>();

        // iterate through each line in the data, skipping the first
        for (int i = 1; i < data.size(); i++) {
            String line = data.get(i);

            // split the line by comma assuming it's a CSV format
            String[] parts = line.split(",");

            // assuming the country is at index 0
            if (parts.length >= 4 && parts[0].equals(country)) {
                // if the line matches the specified country, add it to filteredData
                filteredData.add(line);
            }
        }

        return filteredData;
    }
    public List<String> extractDataFromFile(File file, int year) {
        List<String> data = extractAllDataFromFile(file);
        List<String> filteredData = new ArrayList<>();

        // iterate through each line in the data, skipping the first
        for (int i = 1; i < data.size(); i++) {
            String line = data.get(i);

            // split the line by comma assuming it's a CSV format
            String[] parts = line.split(",");

            // assuming the country is at year is at index 2
            if (parts.length >= 4 && (Integer.parseInt(parts[2]) == year)) {
                // if the line matches the specified year, add it to filteredData
                filteredData.add(line);
            }
        }

        return filteredData;
    }
    public List<String> extractDataFromFile(File file) {
        return extractAllDataFromFile(file);
    }

    private List<String> extractAllDataFromFile(File file) {
        List<String> data = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                data.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }
}
