import config.ConnexioH2;
import model.Persona;
import persistencia.PersonaDAO;
import persistencia.PersonaDAOImpl;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            ConnexioH2 connexio = new ConnexioH2();


            ConnexioH2.servidorWeb();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
