import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Usuario on 05/12/2016.
 */
public class Main {

    public static void main(String[] args) {
        try {
            ArrayList<Pelicula> peliculas = new ArrayList<>();
            MoviedbApi api = new MoviedbApi();
            Scanner sc = new Scanner(System.in);

            int option;
            int identifier;
            System.out.println("Menu Principal");
            System.out.println("-------------------------------------------");
            System.out.println("1. Crear tablas");
            System.out.println("2. Insertar pelicula en la base de datos");
            System.out.println("3. Consultar peliculas");
            System.out.println("4. Consultar actores");

            option = sc.nextInt();
            switch (option){
                case 1:
                    SQL.crearTabla();
                    break;
                case 2:
                    System.out.println("Introduce un id de pelicula");

                    identifier = sc.nextInt();
                    String respuestaPeli = api.getPelis(identifier);
                    String respuestaCredits = api.getCredits(identifier);

                    JSONObject jsonPeli = parserJson(respuestaPeli);
                    JSONObject jsonCredits = parserJson(respuestaCredits);

                    Pelicula peli = new Pelicula();
                    peli.setTitulo(jsonPeli.get("title").toString());
                    peli.setFecha_estreno(jsonPeli.get("release_date").toString());
                    peli.setId(Integer.parseInt(jsonPeli.get("id").toString()));

                    SQL.insertPelicula(peli);

                    JSONArray asd = (JSONArray) jsonCredits.get("cast");
                    ArrayList<Personaje> pjs = new ArrayList<>();
                    for (int i = 0; i < asd.size(); i++) {
                        JSONObject cast = (JSONObject) asd.get(i);
                        Personaje character = new Personaje(800);
                        character.setActor_id(Integer.parseInt(cast.get("id").toString()));
                        character.setCast_id(Integer.parseInt(cast.get("cast_id").toString()));
                        character.setActor(cast.get("name").toString());
                        character.setCharacter(cast.get("character").toString());
                        pjs.add(character);


                        SQL.insertPersonaje(peli.getId(), character);

                    }
                    peli.setPersonajes(pjs);

                    peliculas.add(peli);

                    break;
                case 3:
                    SQL.selectPeliculas();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("introduzca una opcion valida");

            }


            //

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }


    }
public static JSONObject parserJson (String html) throws ParseException {

    JSONParser parser = new JSONParser();
    JSONObject json = (JSONObject) parser.parse(html);

    return json;
}



}
