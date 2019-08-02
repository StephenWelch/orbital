package io.github.stephenwelch.orbital;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
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

}
