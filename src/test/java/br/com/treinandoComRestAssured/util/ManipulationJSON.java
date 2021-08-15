package br.com.treinandoComRestAssured.util;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * @author Raphael Angel
 * @version 0.1
 *
 *
 * <h1>Manipulador de JSON</h1>
 *
 *
 * <h3>Métodos que a classe possui</h3>
 * <ul>
 *     <li>
 *         <p>getJSONBody: recebe um argumento do caminho do JSON (String pathDoJson)
 *         e devolve o JSON inteiro em formato string em uma linha
 *         </p>
 *
 *     </li>
 *
 *     <li>
 *         <p>
 *             getKeyString: recebe um argumento do tipo String chave do JSON (String key)
 *             devolve o valor da chave passada.
 *             Ex: "nome": "Joao"
 *                  getKeyString(nome) == Joao
 *         </p>
 *     </li>
 *
 *
 *
 * </ul>
 *
 */

public class ManipulationJSON {
    JSONParser leitor;
    JSONObject jsonObjct;
    JSONArray jsonArray;
    String[] jsonBody;
    public ManipulationJSON(String arquivo){
        try {
            leitor = new JSONParser();
            jsonArray = new JSONArray();
            jsonBody = getJSONFile(arquivo).split("");
            if(!jsonBody[0].equals("["))
                jsonObjct = (JSONObject) leitor.parse(new FileReader(arquivo));
            System.out.println(jsonBody[0]);
            if(jsonBody[0].equals("["))
                jsonArray = (JSONArray) leitor.parse(new FileReader(arquivo));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String getJSONBodyObject(){
        return jsonObjct.toString();
    }

    public JSONObject getJSONBodyInArray(int index){
        return (JSONObject) jsonArray.get(index);

    }

    public Object getKeyObject(String key){
        return jsonObjct.get(key);
    }

    public Object getKeyArray(Integer index, String key){
        JSONObject jsonArraySelect = (JSONObject) jsonArray.get(index);
        return jsonArraySelect.get(key);
    }

    public Object getValueArrayJSON(Integer index, String key, int indexObject, String keyObject){
        JSONObject jsonObjectArray = getJSONBodyInArray(index);
        JSONArray keyArrayValue = (JSONArray) jsonObjectArray.get(key);
        JSONObject object = (JSONObject) keyArrayValue.get(indexObject);
        if(object.get(keyObject).getClass().equals(Long.class))
            return Integer.parseInt(String.valueOf(object.get(keyObject)));
        return object.get(keyObject);

    }

    public Object getValueObjectJson(String keyArray, int nElement, String keyObject){
        JSONArray array = (JSONArray) jsonObjct.get(keyArray);
        JSONObject objeto = (JSONObject) array.get(nElement);
        if(objeto.get(keyObject).getClass().equals(Long.class))
            return Integer.parseInt(String.valueOf(objeto.get(keyObject)));
        return objeto.get(keyObject);

    }

    public Object getValueObjectIntoObject(String keyObject, String keyChild){
        JSONObject objectFather = (JSONObject) jsonObjct.get(keyObject);
        if(objectFather.get(keyChild).getClass().equals(Long.class))
            return Integer.parseInt(String.valueOf(objectFather.get(keyChild)));

        return objectFather.get(keyChild);
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
