package com.example.comp7506assignment;

public class Item {

    private String name;
    private String institution;

    public Item(String name, String institution) {

        this.name = name;
        this.institution = institution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {

        this.institution = institution;
    }

}
