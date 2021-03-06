package ru.uryupin.reflection;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RefTest {

    private Ref ref = new Ref();

    @Test
    void cleanupTest() throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Set<String> fieldsToCleanup = new HashSet<>();
        Set<String> fieldsToOutput = new HashSet<>();
        SimpleObj simpleObj = new SimpleObj();

        fieldsToCleanup.add("withMileage");
        fieldsToCleanup.add("seats");
        fieldsToCleanup.add("serial");
        fieldsToCleanup.add("cylinders");
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
            fail();
        } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            assertTrue(true);
        }

        fieldsToCleanup.remove("nothing_field");

        fieldsToOutput.add("withMileage");
        fieldsToOutput.add("seats");
        fieldsToOutput.add("serial");
        fieldsToOutput.add("cylinders");
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
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        fieldsToOutput.remove("nothing_field");

        simpleObj.setWithMileage();
        simpleObj.setSeats();
        simpleObj.setSerial();
        simpleObj.setCylinders();
        simpleObj.setPrice();
        simpleObj.setMileage();
        simpleObj.setEngineVolume();
        simpleObj.setRate();
        simpleObj.setModel();
        simpleObj.setWeight();
        simpleObj.setTransmission();

        ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);

        assertFalse(simpleObj.isWithMileage());
        assertEquals(simpleObj.getSeats(), 0);
        assertEquals(simpleObj.getSerial(), '\u0000');
        assertEquals(simpleObj.getCylinders(), 0);
        assertEquals(simpleObj.getPrice(), 0);
        assertEquals(simpleObj.getMileage(), 0);
        assertEquals(simpleObj.getEngineVolume(), 0);
        assertEquals(simpleObj.getRate(), 0);
        assertNull(simpleObj.getModel());
        assertNull(simpleObj.getWeight());
        assertNull(simpleObj.getTransmission());

        fieldsToCleanup.clear();

        simpleObj.setWithMileage();
        simpleObj.setSeats();
        simpleObj.setSerial();
        simpleObj.setCylinders();
        simpleObj.setPrice();
        simpleObj.setMileage();
        simpleObj.setEngineVolume();
        simpleObj.setRate();
        simpleObj.setModel();
        simpleObj.setWeight();
        simpleObj.setTransmission();

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
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        fieldsToCleanup.remove("nothing_field");

        ref.cleanup(map, fieldsToCleanup, fieldsToOutput);

        assertNull(map.get("1"));
        assertEquals(map.get("2"), "two");
        assertNull(map.get("3"));
        assertEquals(map.get("4"), "four");
        assertNull(map.get(null));

        fieldsToCleanup.clear();

        fieldsToOutput.add("2");
        fieldsToOutput.add("4");
        fieldsToOutput.add(null);
        fieldsToOutput.add("nothing_field");

        try {
            // test incorrect field
            ref.cleanup(simpleObj, fieldsToCleanup, fieldsToOutput);
            fail();
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

        fieldsToOutput.clear();
        fieldsToOutput.add("2");
        fieldsToOutput.add("4");

        ref.cleanup(map, fieldsToCleanup, fieldsToOutput);
    }
}