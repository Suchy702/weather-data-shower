package pl.edu.pwr.tkubik.jp.lab04.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


public class JSONParser {
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static JsonNode parse(String src) throws JsonProcessingException {
        return objectMapper.readTree(src);
    }

    public static JsonNode parseJSONToNode(String rawJSON){
        try{
            return JSONParser.parse(rawJSON);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
