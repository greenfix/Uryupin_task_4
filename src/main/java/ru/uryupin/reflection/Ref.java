package ru.uryupin.reflection;

import java.lang.reflect.Field;
import java.util.*;

public class Ref {

    public void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws NoSuchFieldException, IllegalAccessException {
        if (object instanceof Map) {
            cleanupMap(object, fieldsToCleanup, fieldsToOutput);
        } else {
            cleanupObj(object, fieldsToCleanup, fieldsToOutput);
        }
    }

    /**
     * @param object          object
     * @param fieldsToCleanup fieldsToCleanup
     * @param fieldsToOutput  fieldsToOutput
     */
    private void cleanupMap(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) {
        Map map = (Map) object;
        if (!IsCorrectMapFields(map, fieldsToCleanup, fieldsToOutput)) {
            throw new IllegalArgumentException();
        }
        // Cleanup
        for (String fieldName : fieldsToCleanup) {
            map.remove(fieldName);
        }
        // Output
        for (String fieldName : fieldsToOutput) {
            System.out.println(map.get(fieldName));
        }
    }

    /**
     * @param object          object
     * @param fieldsToCleanup fieldsToCleanup
     * @param fieldsToOutput  fieldsToOutput
     * @throws NoSuchFieldException   NoSuchFieldException
     * @throws IllegalAccessException IllegalAccessException
     */
    private void cleanupObj(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws NoSuchFieldException, IllegalAccessException {
        if (!IsCorrectFieldsObj(object.getClass(), fieldsToCleanup, fieldsToOutput)) {
            throw new IllegalArgumentException();
        }
        doCleanObj(object, fieldsToCleanup);
        doOutObj(object, fieldsToOutput);
    }

    /**
     * @param object          object
     * @param fieldsToCleanup fieldsToCleanup
     * @throws NoSuchFieldException   NoSuchFieldException
     * @throws IllegalAccessException IllegalAccessException
     */
    private void doCleanObj(Object object, Set<String> fieldsToCleanup) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = object.getClass();
        String fieldType;
        Field field;
        for (String fieldName : fieldsToCleanup) {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldType = field.getType().toString();
            if (IsPrimitive(fieldType)) {
                resetField(fieldType, field, object);
            } else {
                field.set(object, null);
            }
        }
    }

    /**
     * @param object object
     * @param fieldsToOutput fieldsToOutput
     * @throws NoSuchFieldException NoSuchFieldException
     * @throws IllegalAccessException IllegalAccessException
     */
    private void doOutObj(Object object, Set<String> fieldsToOutput) throws NoSuchFieldException, IllegalAccessException {
        Class clazz = object.getClass();
        String fieldType;
        Field field;
        for (String fieldName : fieldsToOutput) {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            fieldType = field.getType().toString();
            Object f = field.get(object);
            System.out.print(fieldName);
            if (f != null) {
                if (IsPrimitive(fieldType)) {

                    System.out.print("(primitive): ");
                    System.out.println(String.valueOf(f));
                } else {
                    System.out.print("(object): ");
                    System.out.println(f.toString());
                }
            } else {
                System.out.println(": null");
            }
        }
        System.out.println("--------------------------------------");
    }

    /**
     * @param fieldType fieldType
     * @param field     field
     * @param object    object
     * @throws IllegalAccessException IllegalAccessException
     */
    private void resetField(String fieldType, Field field, Object object) throws IllegalAccessException {
        switch (fieldType) {
            case "boolean":
                field.set(object, false);
                break;
            case "char":
                field.set(object, '\u0000');
                break;
            case "long":
                field.set(object, 0L);
                break;
            case "byte":
                field.set(object, (byte) 0);
                break;
            case "short":
                field.set(object, (short) 0);
                break;
            case "float":
                field.set(object, 0f);
                break;
            case "double":
                field.set(object, 0d);
                break;
            default: // "int"
                field.set(object, 0);
        }
    }

    /**
     * @param typeField typeField
     * @return IsPrimitive
     */
    private boolean IsPrimitive(String typeField) {
        List<String> primitive = Arrays.asList("boolean", "byte", "char", "short", "int", "long", "float", "double");

        return (primitive.indexOf(typeField) != -1);
    }

    /**
     * @param map         map
     * @param fieldsList  fieldsList
     * @param fieldsList1 fieldsList1
     * @return IsCorrectMapFields
     */
    private boolean IsCorrectMapFields(Map map, Set<String> fieldsList, Set<String> fieldsList1) {

        return checkFieldsMap(map, fieldsList) && checkFieldsMap(map, fieldsList1);
    }

    /**
     * @param map        map
     * @param fieldsList fieldsList
     * @return checkFieldsMap
     */
    private boolean checkFieldsMap(Map map, Set<String> fieldsList) {
        Set keysMap = map.keySet();
        for (String fieldName : fieldsList) {
            if (!keysMap.contains(fieldName)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param clazz      clazz
     * @param fieldsList fieldsToCleanup
     * @return IsCorrectFields
     */
    private boolean IsCorrectFieldsObj(Class clazz, Set<String> fieldsList, Set<String> fieldsList1) {

        return checkFieldsObj(clazz, fieldsList) && checkFieldsObj(clazz, fieldsList1);
    }

    /**
     * @param clazz      clazz
     * @param fieldsList fieldsList
     * @return checkFieldsObj
     */
    private boolean checkFieldsObj(Class clazz, Set<String> fieldsList) {
        List<String> strFieldsObj = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            String[] parts = field.toString().split("\\.");
            strFieldsObj.add(parts[parts.length - 1]);
        }
        for (String field : fieldsList) {
            if (strFieldsObj.indexOf(field) == -1) {
                return false;
            }
        }

        return true;
    }
}
