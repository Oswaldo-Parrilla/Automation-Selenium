package PageObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductReader {
    public static List<String> readAltAttributesFromFile(String filePath) {
        List<String> altAttributes = new ArrayList<>();//Este es una lista vacia donde se agregara valores
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line; //variable de tipo cadena es vacia
            while ((line = br.readLine()) != null) {
                altAttributes.add(line.trim()); // Agregar cada línea (atributo) a la lista
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return altAttributes;
    }
}
