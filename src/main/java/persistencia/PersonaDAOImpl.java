package persistencia;

import config.ConnexioH2;
import model.Persona;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAOImpl implements PersonaDAO {

    private ConnexioH2 conn;

    public PersonaDAOImpl(ConnexioH2 connH2) {
        this.conn = connH2;
    }

    /**
     * @param persona
     * @return
     */
    @Override
    public boolean afegir(Persona persona) {

        String query = "INSERT INTO persona (NIF, NOM, EDAT) VALUES (?, ?, ?)";

        try (Connection con = this.conn.getConnexio();
             PreparedStatement ps = con.prepareStatement(query);) {
            ps.setString(1, persona.getNif());
            ps.setString(2, persona.getNombre());
            ps.setInt(3, persona.getEdat());

            return ps.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            throw new PersonaException("Error afegint persona", e);
        }

    }

    /**
     * @param nif
     * @return Persona
     */
    @Override
    public Persona cercarPerNif(String nif) {
        String query = "SELECT * FROM persona WHERE NIF = ?";
        Persona persona = null;

        try (Connection con = this.conn.getConnexio();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, nif);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    persona = new Persona(rs.getString("NIF"), rs.getString("NOM"), rs.getInt("EDAT"));
                }
            }
        } catch (SQLException | IOException e) {
            throw new PersonaException("No se a trobat la persona per NIF", e);
        }
        return persona;
    }

    /**
     * @return
     */
    @Override
    public List<Persona> cercarTots() {
        String query = "SELECT * FROM persona";
        List<Persona> persones = new ArrayList<>();

        try (Connection con = this.conn.getConnexio();
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                persones.add(new Persona(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }

        } catch (SQLException | IOException e) {
            throw new PersonaException("Error cercant persones", e);
        }

        return persones;


    }

    /**
     * @param nif
     * @return 1 || 0
     */
    @Override
    public boolean eliminar(String nif) {
        String query = "DELETE FROM persona WHERE NIF = ?";

        try (Connection con = this.conn.getConnexio();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nif);
            return ps.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            throw new PersonaException("Error eliminant la persona", e);
        }
    }

    /**
     * @param persona
     * @return 1||0
     */
    @Override
    public boolean actualizar(Persona persona) {
        String query = "UPDATE persona SET NOM = ?, EDAT = ? WHERE NIF = ?";

        try (Connection con = this.conn.getConnexio();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, persona.getNombre());
            ps.setInt(2, persona.getEdat());
            ps.setString(3, persona.getNif());
            return ps.executeUpdate() > 0;
        } catch (SQLException | IOException e) {
            throw new PersonaException("Error actualitzant la persona", e);
        }

    }
}
