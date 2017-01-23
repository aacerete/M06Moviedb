/**
 * Created by 46990527d on 23/01/17.
 */

import java.sql.*;
import java.util.Scanner;

public class selectPostgres {

    public static void main(String[] args) {

        int menu = 0;
        Scanner teclat = new Scanner(System.in);

        do {

            System.out.println("Menu principal");
            System.out.println("---------------------------------------------------");

            System.out.println("1. Ver peliculas ");
            System.out.println("2. Ver una pelicula ");
            System.out.println("3. Ver actores");
            System.out.println("4. Ver peliculas por actor");
            System.out.println("5. Salir");
            System.out.println("---------------------------------------------------");
            System.out.println();

            menu = teclat.nextInt();

            switch (menu) {
                case 1:
                    verMovies();
                    break;
                case 2:
                    System.out.println("Introduce el Id de la pelicula");
                    int ID = teclat.nextInt();

                    verMovies(ID);
                    break;
                case 3:
                    verActors();
                    break;
                case 4:
                    System.out.println("Introduce el ID del actor");
                    ID = teclat.nextInt();
                    selectPostgres.verMoviesActors(ID);
                    break;
                case 5:
                    System.out.println("Fin");
                    break;
            }
        } while (menu != 5);
    }


    public static void verMovies()    {

        Connection c = null;
        Statement stmt = null;

        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://172.31.73.191:5432/m06moviedb","aacerete","aacerete");


            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM MOVIES;" );


            while ( rs.next() ) {
                int id = rs.getInt("ID");
                String title = rs.getString("ORIGINAL_TITLE");
                String date_release= rs.getString("RELEASE_DATE");

                System.out.println("ID: " + id);
                System.out.println("Titulo de la pelicula "+title);
                System.out.println("Fecha de estreno : "+ date_release);
                System.out.println();

            }
            rs.close();
            stmt.close();
            c.close();

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }


    public static void verMovies(int ID){

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://172.31.73.191:5432/m06moviedb","aacerete","aacerete");


            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM MOVIES;" );
            while ( rs.next() ) {

                int id = rs.getInt("ID");
                String title = rs.getString("ORIGINAL_TITLE");
                String date_release= rs.getString("RELEASE_DATE");

                if(id==ID) {
                    System.out.println("ID: " + id);
                    System.out.println("Titulo de la pelicula "+title);
                    System.out.println("Fecha de estreno: "+ date_release);
                }

            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("Error");
            System.exit(0);
        }
    }
    public static void verActors(){

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://172.31.73.191:5432/m06moviedb","aacerete","aacerete");


            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACTORS;" );
            while ( rs.next() ) {

                int id = rs.getInt("ID");
                String name= rs.getString("NAME");

                System.out.println("ID: " + id);
                System.out.println("Nombre : "+name);
                System.out.println();
            }

            rs.close();
            stmt.close();

            c.close();
        } catch ( Exception e ) {
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static void verMoviesActors(int ID){

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://172.31.73.191:5432/m06moviedb","aacerete","aacerete");


            stmt = c.createStatement();

            ResultSet rs = stmt.executeQuery( "SELECT ACTORS.CHARACTER, MOVIES.ORIGINAL_TITLE \n" +
                    "FROM ACTORS\n" +
                    "INNER JOIN MOVIES\n" +
                    "ON MOVIES.ID = ACTORS.ID_PELICULA \n" +
                    "WHERE ACTORS.ID = "+ID  );

            while ( rs.next() ) {

                String title = rs.getString("ORIGINAL_TITLE");
                String character = rs.getString("CHARACTER");
                System.out.println();
                System.out.println("Pelicula :" + title);
                System.out.println("Personaje : " + character);
                System.out.println();

            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.out.println("Error");
            System.exit(0);
        }
    }


}