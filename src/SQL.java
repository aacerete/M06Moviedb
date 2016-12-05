import java.sql.*;

/**
 * Created by Usuario on 05/12/2016.
 */
public class SQL {

    public static void crearTabla(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            System.out.println("La BBDD ha sigut creada");

            stmt = c.createStatement();
                /*
                    Creo les taules i les executo.
                 */
            String query = "CREATE TABLE IF NOT EXISTS PELICULAS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " TITULO TEXT    NOT NULL, " +
                    " FECHA_ESTRENO   TEXT    NOT NULL)";

            stmt.executeUpdate(query);

            String query2 = "CREATE TABLE IF NOT EXISTS ACTORES " +
                    "(ID_PELICULA INT    NOT NULL," +
                    " ID_CAST   INT    NOT NULL," +
                    " ID_ACTOR   INT    NOT NULL," +
                    " ACTOR TEXT    NOT NULL, " +
                    " CHARACTER   TEXT    NOT NULL," +
                    "PRIMARY KEY (ID_PELICULA,ID_CAST, ID_ACTOR))";

            stmt.executeUpdate(query2);

        }catch(SQLException ss){

        }catch(ClassNotFoundException cc){

        }
    }

    public static void selectPeliculas(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PELICULAS;" );
            while ( rs.next() ) {
                int id = rs.getInt("ID");
                String title = rs.getString("TITULO");
                String date_release= rs.getString("FECHA_ESTRENO");

                System.out.println("ID: " + id);
                System.out.println("Titulo de la pelicula "+title);
                System.out.println("Data d'estrena : "+date_release);
                System.out.println();
            }

        }catch(SQLException ss){

        }catch(ClassNotFoundException cc){

        }
    }

    public static void insertPelicula(Pelicula peli){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            String sql_insert = "INSERT INTO PELICULAS (ID, TITULO, FECHA_ESTRENO) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setInt(1, peli.getId());
            preparedStatement.setString(2, peli.getTitulo());
            preparedStatement.setString(3, peli.getFecha_estreno());
            preparedStatement.executeUpdate();
        }catch(SQLException ss){

        }catch(ClassNotFoundException cc){

        }
    }

    public static void insertPersonaje(int peli, Personaje character){
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            String sql_insert = "INSERT INTO ACTORES (ID_PELICULA, ID_CAST, ID_ACTOR, ACTOR, CHARACTER) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = c.prepareStatement(sql_insert);
            preparedStatement.setInt(1, peli);
            preparedStatement.setInt(2, character.getCast_id());
            preparedStatement.setInt(3, character.getActor_id());
            preparedStatement.setString(4, character.getActor());
            preparedStatement.setString(5, character.getCharacter());
            preparedStatement.executeUpdate();
        }catch(SQLException ss){

        }catch (ClassNotFoundException ee){

        }
    }

}
