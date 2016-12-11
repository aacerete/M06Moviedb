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

// probar la cerca de pelis per actor amb el id 12452, shirley douglas, mostrara les diferents pelis que ha fet
public class Main {


    
    public static void main(String[] args) {

        try {
            ArrayList<Pelicula> peliculas = new ArrayList<>();
            MoviedbApi api = new MoviedbApi();
            Scanner sc = new Scanner(System.in);

            int option;
            int identifier;
            int idquery;

            //Menu principal d'opcions
            System.out.println("Menu Principal");
            System.out.println("-------------------------------------------");
            System.out.println("1. Crear tablas");
            System.out.println("2. Insertar pelicula en la base de datos");
            System.out.println("3. Consultar todas peliculas");
            System.out.println("4. Consultar pelicula por ID");
            System.out.println("5. Consultar todos los actores");
            System.out.println("6. Consultar peliculas en las que ha actuado un actor por el ID de actor");

            option = sc.nextInt();
            switch (option){
                case 1:
                    //creem la taula si no existeix
                    SQL.crearTabla();
                    break;
                case 2:
                    System.out.println("Introduce un id de pelicula");

                    identifier = sc.nextInt();
                    String respuestaPeli = api.getPelis(identifier);
                    String respuestaCredits = api.getCredits(identifier);

                    JSONObject jsonPeli = parserJson(respuestaPeli);
                    JSONObject jsonCredits = parserJson(respuestaCredits);

                    //a partir de la resposta Json, creem la pelicula
                    Pelicula peli = new Pelicula();
                    peli.setTitulo(jsonPeli.get("title").toString());
                    peli.setFecha_estreno(jsonPeli.get("release_date").toString());
                    peli.setId(Integer.parseInt(jsonPeli.get("id").toString()));

                    //la insertem a la base de dades
                    SQL.insertPelicula(peli);

                    //insertem tambe la info que conte cast (personatjes)
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

                        //insertem la info del personatje a la pelicula
                        SQL.insertPersonaje(peli.getId(), character);

                    }
                    //modifiquem la pelicula
                    peli.setPersonajes(pjs);

                    //la afegim
                    peliculas.add(peli);

                    break;
                case 3:
                    //select amb totes les pelis de la bbdd
                    SQL.selectPeliculas();
                    break;
                case 4:
                    //select de la info de una peli a partir del seu id
                    System.out.println("Introduce el ID de la pelicula");
                    idquery = sc.nextInt();
                    SQL.selectPeliculasByID(idquery);
                    break;
                case 5:
                    //llistat d'actors amb info de la bbdd
                    SQL.selectActores();
                    break;
                case 6:
                    //listat de pelicules que ha realitzat un actor segons la seva id
                    System.out.println("Introduce el ID del actor");
                    idquery = sc.nextInt();
                    SQL.selectPeliculasByActorid(idquery);
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
