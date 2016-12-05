/**
 * Created by Usuario on 05/12/2016.
 */
public class Personaje {
    int peli_id;
    int cast_id;
    int actor_id;
    String actor;
    String character;


    public Personaje(int peli_id) {
        this.peli_id = peli_id;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public int getCast_id() {
        return cast_id;
    }

    public void setCast_id(int cast_id) {
        this.cast_id = cast_id;
    }

    public int getActor_id() {
        return actor_id;
    }

    public void setActor_id(int actor_id) {
        this.actor_id = actor_id;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }
}
