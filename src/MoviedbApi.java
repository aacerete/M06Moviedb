import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Usuario on 05/12/2016.
 */



public class MoviedbApi {

    private String API_KEY = "cb2e3959094291c9f867e547c47f14ba";
    private String URI = "https://api.themoviedb.org/3";

    //metode que fa crida a la api
    private String cridaAPI(String rutaURL) throws Exception {

        StringBuilder result = new StringBuilder();
        URL url = new URL(rutaURL+"?api_key="+API_KEY);
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

    //metode per a obtenir pelis segons id
    public String getPelis (int id) throws Exception {

        String rutaPelis = URI+"/movie/"+id;
        String respuesta = cridaAPI(rutaPelis);

        return respuesta ;
    }

    //metode per a obtenir els credits segons la id de la peli
    public String getCredits (int id) throws Exception {

        String rutaPelis = URI+"/movie/"+id+"/credits";
        String respuesta = cridaAPI(rutaPelis);

        return respuesta ;
    }
}
