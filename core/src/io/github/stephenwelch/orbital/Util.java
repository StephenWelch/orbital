package io.github.stephenwelch.orbital;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.stream.Collectors;

public class Util {

    public double normalizeAngleDegrees(double angle) {
        while(angle < 0) angle += 180;
        while(angle > 360) angle -= 180;
        return angle;
    }

    public static Vector2 truncateVector(Vector3 vector) {
        return new Vector2(vector.x, vector.y);
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

    public static Vector2[] translateAndRotateVectors(Vector2 translation, float rotation, Vector2 ... vectors) {
        Vector2[] translatedVectors = new Vector2[vectors.length];
        for(int index = 0; index < vectors.length; index++) {
            translatedVectors[index] = translateAndRotate(translation, rotation, vectors[index]);
        }
        return translatedVectors;
    }

    public static Vector2 translateAndRotate(Vector2 translation, float rotation, Vector2 vector) {
        // add() and rotate() modify the vector itself, so we apply our changes to the new vector we have created instead.
        return new Vector2().add(vector).rotateRad((float)Math.toRadians(rotation)).add(translation);
    }

    public static Vector3 translate(Vector3 translation, Vector3 vector) {
        return new Vector3().add(vector).add(translation);
    }

}
