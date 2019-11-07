package ru.uryupin.reflection;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RefTest {

    Ref ref = new Ref();

    @Test
    void cleanup() throws NoSuchFieldException, IllegalAccessException {

        Set<String> fieldsToCleanup = new HashSet<>();
        Set<String> fieldsToOutput = new HashSet<>();
        SimpleObj simpleObj = new SimpleObj();

        fieldsToCleanup.add("withMileage");
        fieldsToCleanup.add("seats");
        fieldsToCleanup.add("serial");
        fieldsToCleanup.add("cylinders");
        fieldsToCleanup.add("price");
        fieldsToCleanup.add("mileage");
        fieldsToCleanup.add("engineVolume");
        fieldsToCleanup.add("rate");
        fieldsToCleanup.add("model");
        fieldsToCleanup.add("price");
        fieldsToCleanup.add("weight");
        fieldsToCleanup.add("transmission");

        fieldsToCleanup.add("nothing_field");

        try {
            // test incorrect field
            ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        fieldsToCleanup.remove("nothing_field");

        fieldsToOutput.add("withMileage");
        fieldsToOutput.add("seats");
        fieldsToOutput.add("serial");
        fieldsToOutput.add("cylinders");
        fieldsToOutput.add("price");
        fieldsToOutput.add("mileage");
        fieldsToOutput.add("engineVolume");
        fieldsToOutput.add("rate");
        fieldsToOutput.add("model");
        fieldsToOutput.add("price");
        fieldsToOutput.add("weight");
        fieldsToOutput.add("transmission");

        fieldsToOutput.add("nothing_field");

        try {
            // test incorrect field
            ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        fieldsToOutput.remove("nothing_field");

        simpleObj.setWithMileage(true);
        simpleObj.setSeats((byte) 4);
        simpleObj.setSerial('B');
        simpleObj.setCylinders((short) 8);
        simpleObj.setPrice(680000);
        simpleObj.setMileage(150000L);
        simpleObj.setEngineVolume(1.995F);
        simpleObj.setRate(0.02458d);
        simpleObj.setModel("Lexus IS200d");
        simpleObj.setWeight(1545);
        simpleObj.setTransmission(Transmission.AUTO);

        ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);

        assertEquals(simpleObj.isWithMileage(), false);
        assertEquals(simpleObj.getSeats(), 0);
        assertEquals(simpleObj.getSerial(), '\u0000');
        assertEquals(simpleObj.getCylinders(), 0);
        assertEquals(simpleObj.getPrice(), 0);
        assertEquals(simpleObj.getMileage(), 0);
        assertEquals(simpleObj.getEngineVolume(), 0);
        assertEquals(simpleObj.getRate(), 0);
        assertEquals(simpleObj.getWeight(), null);
        assertEquals(simpleObj.getTransmission(), null);

        fieldsToCleanup.clear();

        simpleObj.setWithMileage(true);
        simpleObj.setSeats((byte) 4);
        simpleObj.setSerial('B');
        simpleObj.setCylinders((short) 8);
        simpleObj.setPrice(680000);
        simpleObj.setMileage(150000L);
        simpleObj.setEngineVolume(1.995F);
        simpleObj.setRate(0.02458d);
        simpleObj.setModel("Lexus IS200d");
        simpleObj.setWeight(1545);
        simpleObj.setTransmission(Transmission.AUTO);

        ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);

        fieldsToOutput.clear();

        Map<String, String> map = new HashMap<>();

        map.put("1", "one");
        map.put("2", "two");
        map.put("3", "three");
        map.put("4", "four");
        map.put(null, "zero");

        fieldsToCleanup.add("1");
        fieldsToCleanup.add("3");
        fieldsToCleanup.add(null);
        fieldsToCleanup.add("nothing_field");

        try {
            // test incorrect field
            ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        fieldsToCleanup.remove("nothing_field");

        ref.cleanup(map, fieldsToCleanup, fieldsToOutput);

        assertEquals(map.get("1"), null);
        assertEquals(map.get("2"), "two");
        assertEquals(map.get("3"), null);
        assertEquals(map.get("4"), "four");
        assertEquals(map.get(null), null);

        fieldsToCleanup.clear();

        fieldsToOutput.add("2");
        fieldsToOutput.add("4");
        fieldsToOutput.add(null);
        fieldsToOutput.add("nothing_field");

        try {
            // test incorrect field
            ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);
            assertTrue(false);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        fieldsToOutput.clear();
        fieldsToOutput.add("2");
        fieldsToOutput.add("4");

        ref.cleanup(map, fieldsToCleanup, fieldsToOutput);
    }
}