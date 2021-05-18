package com.airbus.exception;

public class NoProductsForCategory extends Exception{
    private String category;
    public NoProductsForCategory(String category){
        super("No Products available for \"" + category + "\" category");
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
