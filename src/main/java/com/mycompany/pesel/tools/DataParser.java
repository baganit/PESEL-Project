package com.mycompany.pesel.tools;

public class DataParser {

    String inputData;
    String name = "";
    String pesel;

    public DataParser(String inputData) {
        this.inputData = inputData;
    }

    public void parseData() {
        String[] splitData = inputData.split("\\s+");

        for (int i = 0; i < splitData.length; i++) {
            if (splitData[i].matches("[0-9]+"))
                pesel = splitData[i];
            if (splitData[i].matches("[a-zA-Z]+"))
                if (!name.equals(""))
                    name += " " + splitData[i];
                else
                    name = splitData[i];
        }
    }

    public String getName() {
        return name;
    }

    public String getPesel() {
        return pesel;
    }
}
