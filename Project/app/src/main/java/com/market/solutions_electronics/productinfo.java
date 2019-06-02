package com.market.solutions_electronics;

public class productinfo {
    String Description;
    String Name;
    String Price;
    String URL;

    public productinfo() {
    }

    public productinfo(String description, String name, String price, String URL) {
        this.Description = description;
        this.Name = name;
        this.Price = price;
        this.URL = URL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
