package ru.uryupin.reflection;

import java.lang.reflect.Field;
import java.util.*;

public class Ref {

    public void cleanup(Object object, Set<String> fieldsToCleanup, Set<String> fieldsToOutput) throws NoSuchFieldException, IllegalAccessException {
        if (object instanceof Map) {
            if (!IsCorrectMapFields(object, fieldsToCleanup) ||
                    !IsCorrectMapFields(object, fieldsToOutput)) {
                throw new IllegalArgumentException();
            }
            for (String fieldName : fieldsToCleanup) {
                ((Map) object).remove(fieldName);
            }
            for (String fieldName : fieldsToOutput) {
                System.out.println(((Map) object).get(fieldName));
            }
        } else {
            Class clazz = object.getClass();
            if (!IsCorrectFields(clazz, fieldsToCleanup) || !IsCorrectFields(clazz, fieldsToOutput)) {
                throw new IllegalArgumentException();
            }
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

    private boolean IsCorrectMapFields(Object object, Set<String> fieldsList) {
        Set<String> keysMap = ((Map) object).keySet();
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
    private boolean IsCorrectFields(Class clazz, Set<String> fieldsList) {
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
