import java.util.ArrayList;

/**
 * Created by Usuario on 05/12/2016.
 */
public class Pelicula {

    String titulo;
    String fecha_estreno;
    ArrayList <Personaje> personajes = new ArrayList<Personaje>();
    int id;

    public Pelicula() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha_estreno() {
        return fecha_estreno;
    }

    public void setFecha_estreno(String fecha_estreno) {
        this.fecha_estreno = fecha_estreno;
    }

    public ArrayList<Personaje> getPersonajes() {
        return personajes;
    }

    public void setPersonajes(ArrayList<Personaje> personajes) {
        this.personajes = personajes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
