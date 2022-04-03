package ru.praktikum_services.qa_scooter;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    public List<String> color;
    public String address;
    public String firstName;
    public String lastName;
    public String deliveryDate;
    public String metroStation;
    public String phone;
    public int rentTime;
    public String comment;

    public Order(List<String> color) {
        this.color = color;
        this.address = "ул. Ленина, 20";
        this.firstName = "Тест";
        this.lastName = "Тестовый";
        this.deliveryDate = LocalDateTime.now().toString();
        this.metroStation = "3";
        this.phone = "89999999999";
        this.rentTime = 2;
        this.comment = "Тест";
    }
}
