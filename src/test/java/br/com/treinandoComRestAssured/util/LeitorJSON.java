package br.com.treinandoComRestAssured.util;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeitorJSON {
    JSONParser leitor;
    JSONObject jsonObjct;

    public String getJSONBody(String arquivoNome){
        leitor = new JSONParser();
        try {
            jsonObjct = (JSONObject) leitor.parse(new FileReader(arquivoNome));
            return jsonObjct.toJSONString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getUsingKey(String arquivo, String key){
        leitor = new JSONParser();
        try {
            jsonObjct = (JSONObject) leitor.parse(new FileReader(arquivo));
            return (String) jsonObjct.get(key);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getJSONFile(String nomePath){
        try {
            return new String(Files.readAllBytes(Paths.get(nomePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
