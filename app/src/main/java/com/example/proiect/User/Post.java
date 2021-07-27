package com.example.proiect.User;

public class Post {

    private String color,description,imageurl,make,model,postid,publisher,price,year;

    public Post(){

    }

    public Post(String color, String description, String imageurl, String make, String model, String postid, String price, String publisher, String year) {
        this.color = color;
        this.description = description;
        this.imageurl = imageurl;
        this.make = make;
        this.model = model;
        this.postid = postid;
        this.price = price;
       this.publisher = publisher;
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publicsher) {
        this.publisher = publicsher;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
