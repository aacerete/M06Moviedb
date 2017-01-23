/**
 * Created by 46990527d on 23/01/17.
 */

import java.sql.*;
import java.util.Properties;

public class insertPostgres {

    public static void insertMovies(int ID, String ORIGINAL_TITLE,String RELEASE_DATE) {

        Connection c = null;
        Statement stmt = null;
        try {

            // Conectamos con la base de dat0s
            String url = "jdbc:postgresql://172.31.73.191:5432/m06moviedb";
            Properties props = new Properties();
            props.setProperty("user","aacerete");
            props.setProperty("password","aacerete");
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url,props);
            c.setAutoCommit(false);


            //insertam0s
            String sql_insert = "INSERT INTO MOVIES" +
                    " (ID,ORIGINAL_TITLE,RELEASE_DATE) VALUES" +
                    " (?, ?, ?);";

            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, ORIGINAL_TITLE);
            preparedStatement.setString(3, RELEASE_DATE);

            //ejecutamos insert
            preparedStatement.executeUpdate();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void insertActor(int ID,int ID_PELICULA,String NAME, String CHARACTER) {

        Connection c = null;
        Statement stmt = null;
        try {

            // Conectamos con la base de dat0s
            String url = "jdbc:postgresql://172.31.73.191:5432/m06moviedb";
            Properties props = new Properties();
            props.setProperty("user","aacerete");
            props.setProperty("password","aacerete");
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url,props);
            c.setAutoCommit(false);


            //insertam0s
            String sql_insert = "INSERT INTO ACTORS" +
                    " (ID,ID_PELICULA,NAME,CHARACTER) VALUES" +
                    " (?, ?, ?,?);";


            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setInt(1, ID);
            preparedStatement.setInt(2, ID_PELICULA);
            preparedStatement.setString(3, NAME);
            preparedStatement.setString(4, CHARACTER);


            //ejecutamos insert
            preparedStatement.executeUpdate();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



}