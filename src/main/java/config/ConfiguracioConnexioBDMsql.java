package config;

import org.h2.jdbcx.JdbcDataSource; //es troba al driver jdbc de Mysql
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;

public class ConfiguracioConnexioBDMsql {
    private static DataSource ds;
    public ConfiguracioConnexioBDMsql() {
    }
    /**
     * Torna una instància de la classe que implementa la interfície Connection, amb la connexió oberta amb el SGBD
     * a la BD indicada.
     *
     * @return torna la connexió oberta
     */
    public static Connection getConnexio() throws SQLException, IOException  {
        if(ds == null) {
            try (InputStream input = ConfiguracioConnexioBDMsql.class.getClassLoader().
                    getResourceAsStream("db.properties")) {
                //Carreguem a un objecte Properties les dades de connexió existents al fitxer db.properties
                Properties props = new Properties();
                props.load(input);
                MysqlDataSource myds = new MysqlDataSource();
                myds.setURL(props.getProperty("url"));
                myds.setUser(props.getProperty("user"));
                myds.setPassword(props.getProperty("password"));
                ds = (DataSource)myds;
            }

        }
        return ds.getConnection();
    }
}
