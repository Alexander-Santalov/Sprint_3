package ru.yandex.scooter;


import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;
import java.util.Random;

public class Order {
    public String firstName;
    public String lastName;
    public String address;
    public int metroStation;
    public String phone;
    public int rentTime;
    public String deliveryDate;
    public String comment;
    public List<String> color;


    public Order(String firstName,String lastName, String address, int metroStation,
                 String phone,int rentTime,String deliveryDate, String comment, List<String> color){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    public static Order getRandomOrder(List<String> colour){
        String firstName = "Alex" + new Random().nextInt(10);
        String lastName = "Santalov" + new Random().nextInt(10);
        String address = "Zelenograd" + new Random().nextInt(10);
        int metroStation = new Random().nextInt(10);
        String phone = "+7 800 355 35 3" + new Random().nextInt(10);
        int rentTime = new Random().nextInt(10);
        String deliveryDate =  "2022-06-0" + new Random().nextInt(10);
        String comment = RandomStringUtils.randomAlphabetic(30);
        List<String> color = colour;

        return  new Order(firstName,lastName,address, metroStation,
                phone, rentTime, deliveryDate, comment, color);
    }
}
