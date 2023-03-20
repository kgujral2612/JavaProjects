package edu.pdx.cs410J.kgujral;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class InternalStorageHelper {
    private String path;
    private Context context;
    public InternalStorageHelper(String path, Context context) {
        this.path = path;
        this.context = context;
    }

    public void writeToFile(ArrayList<Airline> airlines){
        // Serialize the list of objects to JSON
        Gson gson = new Gson();
        String json = gson.toJson(airlines);

        // Get a reference to the internal storage directory
        File directory = this.context.getFilesDir();

        // Create a file to store the data
        File file = new File(directory, this.path);

        // Write the JSON string to the file
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Airline> readFromFile(){
        // Get a reference to the internal storage directory
        File directory = this.context.getFilesDir();

        // Get a reference to the file containing the data
        File file = new File(directory, path);

        // Read the contents of the file into a string
        String json;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            json = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        // Deserialize the JSON string to a List<Person>
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Airline>>(){}.getType();
        ArrayList<Airline> airlines = gson.fromJson(json, type);
        return airlines;
    }
}
