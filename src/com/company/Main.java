package com.company;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    static Set<Apartment> apartments = new HashSet<>();

    public static void main(String[] args) {
        init();

        getStreetPrice();
        System.out.println("----------");
        mapStreetHouseNumberAndPrice();
        System.out.println("----------");
        mapStreetHouseNumberFloorToPrice();
        System.out.println("----------");
        getStreetAveragePrice();

        System.out.println("----------");
        getApartmentsPerStreet();

        System.out.println("----------");
        getMostExpensiveStreet();

        System.out.println("----------");
        getAveragePricePerStreetWithElevator();
        System.out.println("----------");
        getAveragePricePerFloor();

        System.out.println("----------");
        getApartmentsPerFloorCount();

        System.out.println("----------");
        cascadeSearch();
    }

    //Map<street, List<price>>
    private static void getStreetPrice() {
        apartments
                .stream()
                .collect(Collectors.groupingBy(
                        x -> x.getStreet(), Collectors.mapping(Apartment::getPrice, Collectors.toList())
                ))
                .entrySet()
                .forEach(x -> System.out.printf("%s - %s\n", x.getKey(), x.getValue()));
    }

    //Map<String, List<Double>> string - Street, house number, list<double> prices at this address
    private static void mapStreetHouseNumberAndPrice() {
        //Set<Apartment>
        apartments
                .stream()
                .collect(
                        //GroupingBy:
                        //1. Generates MAP
                        //2. Updates value by duplicated key (.toMap(key, value) throws an error on duplicated key)
                        //3. value:
                        // - Collectors.counting() - how many times were the same key detected
                        // - Collectors.mapping(field, collection) - Map<String, List<String>> or Map<String, Set<String>>
                        Collectors.groupingBy(
                                /*KEY:*/    x -> x.getStreet() + ", " + x.getHouseNumber(),
                                /*VALUE: */ Collectors.mapping(x -> x.getPrice(), Collectors.toList())
                        ))
                .entrySet()
                .forEach(x -> System.out.printf("%s - %s\n", x.getKey(), x.getValue()));
    }

    //Map<String, List<Double>>
    private static void mapStreetHouseNumberFloorToPrice() {
        //String: street, house number and floor
        Map<String, List<Double>> data = apartments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getStreet() + ", " + x.getHouseNumber() + ":" + x.getFloor(),
                                Collectors.mapping(x -> x.getPrice(), Collectors.toList())
                        )
                );

        for (Map.Entry<String, List<Double>> map : data.entrySet()) {
            System.out.println(map.getKey() + " - " + map.getValue());
        }
    }

    //Map<street, double> - double - average price
    private static void getStreetAveragePrice() {
        //jabotinsky, 2000
        //weizman, 4800
        //jabotinsky, 4000 (jabotinky, 3000)
        apartments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getStreet(),
                                Collectors.averagingDouble(x -> x.getPrice())
                        )
                )
                //Map<String, Double>
                .entrySet() // -> Set<Entry> -> Entry: key, value
                .stream()
                //Map<String, String>
                .collect(
                        Collectors.toMap(
                                x -> x.getKey(),
//                                x -> String.format("%.2f", x.getValue())
                                x -> {
                                    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
                                    decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
                                    return decimalFormat.format(x.getValue());
                                }
                        )
                )
                .entrySet()
                .forEach(x -> System.out.printf("%s - %s\n", x.getKey(), x.getValue()));

    }

    private static void getApartmentsPerStreet() {
        apartments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getStreet(),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .forEach(x -> System.out.printf("%s - %s\n", x.getKey(), x.getValue()));
    }

    private static void getMostExpensiveStreet() {
        Map.Entry<String, Double> mostExpensiveStreet = apartments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getStreet(),
                                Collectors.averagingDouble(x -> x.getPrice())
                        )
                )
                .entrySet()
                .stream()
                .max(Comparator.comparing(x -> -x.getValue()))
                .get();

        System.out.printf("Most expensive street: %s - %s\n", mostExpensiveStreet.getKey(), mostExpensiveStreet.getValue());

    }

    private static void getAveragePricePerStreetWithElevator() {
        System.out.println("With elevator");
        apartments
                .stream()
                .filter(x -> x.getHasElevator())
//                .filter( x-> x.getFloor() > 6)
                .collect(
                        Collectors.groupingBy(
                                x -> x.getStreet(),
                                Collectors.averagingDouble(x -> x.getPrice())
                        )
                )
                .entrySet()
                .stream()
                .collect(
                        Collectors.toMap(
                                x -> x.getKey(),
                                x -> String.format("%.2f", x.getValue())
                        )
                )
                .entrySet()
                .stream()
                .forEach(x -> System.out.printf("%s - %s\n", x.getKey(), x.getValue()));
    }

    //Map<Integer, Double> - apartment floor, price
    private static void getAveragePricePerFloor() {
        apartments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getFloor(),
                                Collectors.averagingDouble(x -> x.getPrice())
                        )
                )
                .entrySet()
                .stream()
                .sorted((x, y) -> y.getKey().compareTo(x.getKey()))
                .collect(Collectors.toList()) //List<Entry>
                .forEach(x -> System.out.printf("%s - %.2f\n", x.getKey(), x.getValue()));
    }

    private static void getApartmentsPerFloorCount() {
        apartments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getFloor(),
                                Collectors.counting()
                        )
                )
                .entrySet()
                .stream()
                .sorted((a, b) -> b.getKey().compareTo(a.getKey()))
                .forEach(x -> System.out.printf("%s - %s\n", x.getKey(), x.getValue()));
    }

    //all apartments on Jabotinsky street
    public static void cascadeSearch() {
        apartments
                .stream()
                .collect(
                        Collectors.groupingBy(
                                x -> x.getStreet(),
                                Collectors.mapping(Function.identity(), Collectors.toSet())
                        )
                )//Map<String, Set<Apartments>> -> string-street
                .entrySet()
                .stream()
                .filter(x -> x.getKey().equals("Jabotinsky"))
                //before this line: Stream<Entry<String, Set<Apartments>>
                .flatMap(x -> x.getValue().stream())
                //Stream<Apartments>
                .collect(
                        Collectors.groupingBy(
                                x -> x.getHasElevator(),
                                Collectors.mapping(Function.identity(), Collectors.toSet())
                        )
                )
                //Map<Boolean, Set<Apartments>> has elevator, set apartments
                .entrySet()
                .stream()
                .filter(x -> x.getKey())
                .flatMap(x -> x.getValue().stream())
                .filter(x -> x.getFloor() >= 5)
                .collect(
                        Collectors.groupingBy(
                                x -> x.getPrice(),
                                Collectors.mapping(Function.identity(), Collectors.toSet())
                        )
                )
                .entrySet()
                .stream()
                .filter(x -> x.getKey() > 2000 && x.getKey() < 4000)
                .collect(Collectors.toList())
                .forEach(x -> System.out.println(x));
    }


    private static void init() {
        for (int i = 0; i < 1000; i++) {
            apartments.add(RandomApartment.getRandomApartment());
        }
    }
}
