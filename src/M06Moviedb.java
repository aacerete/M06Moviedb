/**
 * Created by 46990527d on 23/01/17.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;

class M06Moviedb {

    static String api_key="cb2e3959094291c9f867e547c47f14ba";
    static int ID_PELICULA;


    public static String getHTML(String urlToRead) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlToRead);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        return result.toString();
    }


    public static void main(String[] args){
        String s = "";

        createPostgres.main(args);

        for (int i = 0; i < 40; i++) {
            int peli = 600 +i;
            String film = String.valueOf(peli);

            //URL para pelis

            String pelisRequest = "https://api.themoviedb.org/3/movie/"+film+"?api_key="+api_key;
            String actorsRequest="https://api.themoviedb.org/3/movie/"+film+"/casts?api_key="+api_key;

            try {
                s = getHTML(pelisRequest);
                GetPelis(s);
                s=getHTML(actorsRequest);
                GetActors(s);
                System.out.println("-----------------------");
            } catch (Exception e) {

            }
        }
        createPostgres.main(args);

    }

    /**
     * Extraer pelis i hacer insert a partir del JSON
     * @param cadena
     */
    public static void GetPelis(String cadena){


         //Extraemos info del JSON

        Object obj02 =JSONValue.parse(cadena);
        JSONObject arra02=(JSONObject)obj02;

        int ID = Integer.parseInt(String.valueOf(arra02.get("id")));
        ID_PELICULA=ID;

        String ORIGINAL_TITLE = String.valueOf(arra02.get("original_title"));
        String RELEASE_DATE=String.valueOf(arra02.get("release_date"));

        System.out.println("ID :"+ID);
        System.out.println("Titulo :"+ORIGINAL_TITLE);
        System.out.println("Fecha estreno :" + RELEASE_DATE);

        //hacemos el insert
        insertPostgres.insertMovies(ID, ORIGINAL_TITLE, RELEASE_DATE);

    }

    /**
     * Extraer actores i hacer insert a partir del JSON
     * @param cadena
     */
    public static void GetActors(String cadena){

        Object obj02 =JSONValue.parse(cadena);
        JSONObject arra02=(JSONObject)obj02;
        JSONArray arra03 = (JSONArray)arra02.get("cast");

        for (int i = 0; i < arra03.size(); i++) {

            JSONObject jb= (JSONObject)arra03.get(i);

            int ID = Integer.parseInt(String.valueOf(jb.get("id")));
            String NAME = String.valueOf(jb.get("name"));
            String CHARACTER=String.valueOf(jb.get("character"));

            System.out.println("ID :"+ID);
            System.out.println("ID pelicula :"+ID_PELICULA);
            System.out.println("Nombre :"+NAME);
            System.out.println("Personaje :"+CHARACTER);
            System.out.println();


            //insteramos
            insertPostgres.insertActor(ID,ID_PELICULA,NAME,CHARACTER);


        }

    }

}