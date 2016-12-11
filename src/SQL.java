import java.sql.*;

/**
 * Created by Usuario on 05/12/2016.
 */
public class SQL {

    //metode que crea les taules
    public static void crearTabla(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            System.out.println("La BBDD ha sigut creada");

            stmt = c.createStatement();

            //Creem la taula pelicules
            String query = "CREATE TABLE IF NOT EXISTS PELICULAS " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " TITULO TEXT    NOT NULL, " +
                    " FECHA_ESTRENO   TEXT    NOT NULL)";

            stmt.executeUpdate(query);

            //creeem la taula actors
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

    //metode que fa el select de totes les pelicules y les imprimeix per pantalla
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

    //metode que fa select d'una peli segons el seu id i la mostra per pantalla
    public static void selectPeliculasByID(int ident){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PELICULAS WHERE ID="+ident+";" );
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

    //metode per a inserir pelis a la BBDD
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

    //metode per a inserir personatjes a la BBDD
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

    //metode que ens mostra el contingut de la taula actors per pantalla
    public static void selectActores(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM ACTORES;" );
            while ( rs.next() ) {

                int id_pelicula = rs.getInt("ID_PELICULA");
                int id_cast= rs.getInt("ID_CAST");
                int id_actor = rs.getInt("ID_ACTOR");
                String actor= rs.getString("ACTOR");
                String character= rs.getString("CHARACTER");

                System.out.println("ID PELICULA: " + id_pelicula);
                System.out.println("ID ACTOR " + id_actor);
                System.out.println("Nom Actor : "+ actor);
                System.out.println(" Personatje : " + character);
                System.out.println();
            }

        }catch(SQLException ss){

        }catch(ClassNotFoundException cc){

        }
    }


    //metode que ens fa una select de les pelicules que ha fet un actor segons la seva id i les mostra per pantalla
    public static void selectPeliculasByActorid(int ident){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:m06moviedb.db");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT ACTORES.ID_ACTOR, ACTORES.ACTOR, PELICULAS.TITULO FROM ACTORES JOIN PELICULAS WHERE ACTORES.ID_ACTOR="+ident+";" );
            while ( rs.next() ) {

                int id_actor = rs.getInt("ID_ACTOR");
                String actor= rs.getString("ACTOR");
                String pelicula= rs.getString("TITULO");

                System.out.println("ID ACTOR " + id_actor);
                System.out.println("Nom Actor : "+ actor);
                System.out.println(" Pelicula : " + pelicula);
                System.out.println();
            }

        }catch(SQLException ss){

        }catch(ClassNotFoundException cc){

        }
    }

}
