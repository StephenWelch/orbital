package io.github.stephenwelch.orbital;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class Util {

    private static Random random = new Random();
    
    public double normalizeAngleDegrees(double angle) {
        while(angle < 0) angle += 180;
        while(angle > 360) angle -= 180;
        return angle;
    }

    public static String loadFromFile(FileHandle fileHandle) {
        Gson gson = new Gson();
        String file = "";
        // 8192 is the default buffer size, anyway
        try(BufferedReader jsonReader = fileHandle.reader(8192)) {
            file = jsonReader.lines().collect(Collectors.joining());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static <T> T loadFromJson(FileHandle fileHandle, TypeToken type) {
        Gson gson = new Gson();
        String json = Util.loadFromFile(fileHandle);
        return gson.fromJson(json, type.getType());
    }

    public static Vector2 translateAndRotateVector(Vector2 translation, float rotation, Vector2 vector) {
        // add() and rotate() modify the vector itself, so we apply our changes to the new vector we have created instead.
        return new Vector2().add(vector).rotateRad((float)Math.toRadians(rotation)).add(translation);
    }


    public static Vector2[] translateAndRotateVectors(Vector2 translation, float rotation, Vector2 ... vectors) {
        return Arrays.stream(vectors).map(v -> translateAndRotateVector(translation, rotation, v)).map(Vector2::cpy).toArray(Vector2[]::new);
    }

    public static Vector2 translateVector(Vector2 translation, Vector2 vector) {
        return new Vector2().add(vector).add(translation);
    }

    public static Vector2[] translateVectors(Vector2 translation, Vector2 ... vectors) {
        return Arrays.stream(vectors).map(v -> translateVector(translation, v)).map(Vector2::cpy).toArray(Vector2[]::new);
    }
    
    public static Vector3 translateVector(Vector3 translation, Vector3 vector) {
        return new Vector3().add(vector).add(translation);
    }

    public static Vector3[] translateVectors(Vector3 translation, Vector3 ... vectors) {
        return Arrays.stream(vectors).map(v -> translateVector(translation, v)).map(Vector3::cpy).toArray(Vector3[]::new);
    }

    public static Vector2 truncateVector(Vector3 vector) {
        return new Vector2(vector.x, vector.y);
    }

    public static Vector2[] truncateVectors(Vector3 ... vectors) {
        return Arrays.stream(vectors).map(Util::truncateVector).toArray(Vector2[]::new);
    }

    public static float getRandomNumber(float min, float max, long seed) {
        random.setSeed(seed);
        return (random.nextFloat() * (max - min)) + min;
    }

    public static int getRandomNumber(int min, int max, long seed) {
        random.setSeed(seed);
        return (random.nextInt() * (max - min)) + min;
    }

}
