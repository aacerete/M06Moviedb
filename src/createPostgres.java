/**
 * Created by 46990527d on 23/01/17.
 */

import java.sql.*;
import java.util.Properties;

public class createPostgres {

    public static void main(String[] args) {
        {
            Connection c = null;
            Statement stmt = null;

            try {
                String url = "jdbc:postgresql://172.31.73.191:5432/m06moviedb";
                Properties props = new Properties();
                props.setProperty("user","aacerete");
                props.setProperty("password","aacerete");
                Class.forName("org.postgresql.Driver");

                c = DriverManager.getConnection(url,props);
                c.setAutoCommit(false);


                System.out.println("Base de Datos Creada");

                stmt = c.createStatement();


                //Creamos tablas
                String sql_movies = "CREATE TABLE MOVIES " +
                        "(ID INT PRIMARY KEY     NOT NULL," +
                        " ORIGINAL_TITLE TEXT    NOT NULL, " +
                        " RELEASE_DATE   TEXT    NOT NULL);";

                stmt.executeUpdate(sql_movies);

                String sql_actor = "CREATE TABLE ACTORS " +
                        "(ID INT    NOT NULL," +
                        " ID_PELICULA   INT    NOT NULL,"+
                        " NAME TEXT    NOT NULL, " +
                        " CHARACTER   TEXT    NOT NULL," +
                        "PRIMARY KEY (ID,ID_PELICULA));";

                stmt.executeUpdate(sql_actor);
                stmt.close();
                c.close();

                System.out.println("ha ido");


            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                System.exit(0);
            }

            System.out.println("Tablas creadas");
        }

    }


}