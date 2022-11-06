package de.vkoop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static de.vkoop.MapUtils.flatten;
import static de.vkoop.MapUtils.unflatten;

@QuarkusMain
public class App {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: <flatten | unflatten> <input-file> <output-file>");
            System.exit(1);
        }

        String mode = args[0];
        if (!mode.equals("flatten") && !mode.equals("unflatten")) {
            System.out.println("Mode should be 'flatten' or 'unflatten'");
            System.exit(1);
        }

        try {
            final String fileContent = Files.readString(Path.of(args[1]), StandardCharsets.UTF_8);

            var mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);


            Map<String, ?> value = mapper.readValue(fileContent, Map.class);

            Map<String, Object> transformedMap;
            if (mode.equals("flatten")) {
                transformedMap = flatten(value);
            } else {
                transformedMap = unflatten(value);
            }

            final File outputFile = new File(args[2]);
            outputFile.createNewFile();

            mapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, transformedMap);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}
