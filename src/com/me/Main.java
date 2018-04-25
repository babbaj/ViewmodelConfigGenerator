package com.me;

import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import static java.lang.Double.parseDouble;

public class Main {

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Expected 4 arguments (name, offset 1, offset 2, offset 3");
            System.exit(1);
        }

        final JsonObject root = new JsonObject();
        final JsonArray weaponsArray = new JsonArray();
        final JsonArray offsets = getOffsetArray(args);

        for (String name : Weapons.NAMES) {
            JsonObject currentWeapon = new JsonObject();
            currentWeapon.add("Name", new JsonPrimitive(name));
            currentWeapon.add("Offset", offsets);
            weaponsArray.add(currentWeapon);
        }
        root.add("Weapons", weaponsArray);
        String output = new GsonBuilder().setPrettyPrinting().create().toJson(root);

        String fileName = args[0];
        if (!fileName.endsWith(".json")) fileName += ".json";
        File outputFile = new File(fileName);
        try {
            Files.write(outputFile.toPath(), output.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("Failed to save file");
            e.printStackTrace();
            System.exit(1);
        }
    }

    static JsonArray getOffsetArray(String[] args) {
        final JsonArray offsetArray = new JsonArray();
        try {
            offsetArray.add(parseDouble(args[1]));
            offsetArray.add(parseDouble(args[2]));
            offsetArray.add(parseDouble(args[3]));
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse numbers. program expects file name/number/number/number");
            System.exit(1);
        };
        return offsetArray;
    }
}
