package config;

import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexioH2 {

    public JdbcDataSource ds;

    public ConnexioH2() {
    }

    /**
     * Torna una instància de la classe que implementa la interfície Connection, amb la connexió oberta amb el SGBD
     * a la BD indicada.
     *
     * @return torna la connexió oberta
     */
    public Connection getConnexio() throws SQLException, IOException {
        if (ds == null) {
            try (InputStream is = ConnexioH2.class.getClassLoader().getResourceAsStream("db.properties")) {
                Properties properties = new Properties();
                properties.load(is);

                ds = new JdbcDataSource();
                ds.setURL(properties.getProperty("url"));
                ds.setUser(properties.getProperty("user"));
                ds.setPassword(properties.getProperty("password"));


            }
        }

        return ds.getConnection();
    }

}
