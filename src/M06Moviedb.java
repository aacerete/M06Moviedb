/**
 * Created by 46990527d on 23/01/17.
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.net.*;

class M06Moviedb {

    static String APIKEY ="cb2e3959094291c9f867e547c47f14ba";
    static int PELI_ID;


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

        //Creamos la BBDD y las tablas
        createPostgres.main(args);

        //Cojemos 10 peliculas a partir de la 200
        for (int i = 0; i < 10; i++) {

            int peli = 200 +i;
            String film = String.valueOf(peli);

            //URLs para pelis y actores
            String pelisRequest = "https://api.themoviedb.org/3/movie/"+film+"?APIKEY="+ APIKEY;
            String actorsRequest="https://api.themoviedb.org/3/movie/"+film+"/casts?APIKEY="+ APIKEY;


            try {
                s = getHTML(pelisRequest);
                GetPelis(s);

                s=getHTML(actorsRequest);
                GetActors(s);

                System.out.println("\n");

            } catch (Exception e) {

            }
        }

        //Una vez hechas las tablas y lso inserts lanzamos el menu
        selectPostgres.main(args);

    }

    /**
     * Extraer pelis i hacer insert a partir del JSON
     * @param cadena
     */

    public static void GetPelis(String cadena){


         //Extraemos info del JSON

        Object objPelis =JSONValue.parse(cadena);
        JSONObject arrayPelis=(JSONObject)objPelis;

        int ID = Integer.parseInt(String.valueOf(arrayPelis.get("id")));
        PELI_ID =ID;

        String ORIGINAL_TITLE = String.valueOf(arrayPelis.get("original_title"));
        String RELEASE_DATE=String.valueOf(arrayPelis.get("release_date"));

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

        Object objActors =JSONValue.parse(cadena);
        JSONObject arrayActors=(JSONObject)objActors;
        JSONArray arrayCasting = (JSONArray)arrayActors.get("cast");

        for (int i = 0; i < arrayCasting.size(); i++) {

            JSONObject jb= (JSONObject)arrayCasting.get(i);

            int ID = Integer.parseInt(String.valueOf(jb.get("id")));
            String NAME = String.valueOf(jb.get("name"));
            String CHARACTER=String.valueOf(jb.get("character"));

            System.out.println("ID :"+ID);
            System.out.println("ID pelicula :"+ PELI_ID);
            System.out.println("Nombre :"+NAME);
            System.out.println("Personaje :"+CHARACTER);
            System.out.println();


            //insertamos en la BBDD
            insertPostgres.insertActor(ID, PELI_ID,NAME,CHARACTER);


        }

    }



}