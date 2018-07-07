package com.company;

import java.util.Random;

public class RandomApartment {

    private static String[] STREETS = {"Rotshild", "Balfur", "Jabotinsky",
            "Tcherniahovky", "Yehezkel", "Weizman", "Herlz"
    };

    private static final Integer HOUSE_NUMBER_MAX = 100;
    private static final Integer FLOOR_MAX = 7;
    private static final Integer PRICE_MIN = 20;
    private static final Integer PRICE_MAX = 50;


    private static String getStreet() {
        int randomStreetIndex = new Random().nextInt(STREETS.length);
        return STREETS[randomStreetIndex];
    }

    private static Integer getFloor() {
        /*return new Random()
                .ints(1, FLOOR_MAX + 1)
                .boxed()
                .findFirst()
                .get();*/

        return new Random().nextInt(FLOOR_MAX) + 1;
    }

    private static Integer getHouseNumber() {
        return new Random().nextInt(HOUSE_NUMBER_MAX) + 1;
    }

    private static Double getPrice() {
        //20 - 50
        Integer price = new Random()
                .ints(PRICE_MIN, PRICE_MAX + 1)
                .findFirst()
                .getAsInt();
        price = price * 100;
        return price.doubleValue();
    }

    private static Boolean getElevator() {
        return new Random().nextBoolean();
    }

    public static Apartment getRandomApartment() {
        return new Apartment(getStreet(),
                getHouseNumber(),
                getFloor(),
                getElevator(),
                getPrice());
    }

//    public static void main(String[] args) {
//        for (int i = 0; i < 100; i++) {
//            Integer floor = getFloor();
//            if (floor.intValue() <= 0) {
//                throw new RuntimeException("floor is zero");
//            }

//            if (floor.equals(7)) {
//                System.out.println("HAS 7TH FLOOR. BREAK");
//                break;
//            }
//        }
//    }
}
