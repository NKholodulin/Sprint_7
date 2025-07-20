package Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class OrderData {
    public static Stream<Map<String, Object>> orderDataProvider() {
        return Stream.of(
                createOrderDataBlackGrey(),
                createOrderDataBlack(),
                createOrderDataGrey(),
                createOrderDataWithoutColor()
        );
    }

    private static Map<String, Object> createOrderDataBlackGrey() {
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", "Naruto");
        data.put("lastName", "Uchiha");
        data.put("address", "Konoha, 142 apt.");
        data.put("metroStation", 4);
        data.put("phone", "+7 800 355 35 35");
        data.put("rentTime", 5);
        data.put("deliveryDate", "2020-06-06");
        data.put("comment", "Saske, come back to Konoha");
        data.put("color", Arrays.asList("BLACK","GREY"));

        return data;
    }
    private static Map<String, Object> createOrderDataBlack() {
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", "Ivan");
        data.put("lastName", "Ivanov");
        data.put("address", "Moscow, 143 apt.");
        data.put("metroStation", 6);
        data.put("phone", "+7 824 355 35 35");
        data.put("rentTime", 3);
        data.put("deliveryDate", "2022-06-06");
        data.put("comment", "Come back to Konoha");
        data.put("color", Arrays.asList("BLACK"));

        return data;
    }
    private static Map<String, Object> createOrderDataGrey() {
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", "Jhon");
        data.put("lastName", "Smith");
        data.put("address", "USA, 150 apt.");
        data.put("metroStation", 10);
        data.put("phone", "+7 800 000 35 35");
        data.put("rentTime", 1);
        data.put("deliveryDate", "2024-06-06");
        data.put("comment", "come back");
        data.put("color", Arrays.asList("GREY"));

        return data;
    }
    private static Map<String, Object> createOrderDataWithoutColor() {
        Map<String, Object> data = new HashMap<>();
        data.put("firstName", "Jhonokre");
        data.put("lastName", "Smithprek");
        data.put("address", "USA, 7150 apt.");
        data.put("metroStation", 6);
        data.put("phone", "+7 999 000 35 35");
        data.put("rentTime", 10);
        data.put("deliveryDate", "2026-06-06");
        data.put("comment", "Without color");

        return data;
    }
}
