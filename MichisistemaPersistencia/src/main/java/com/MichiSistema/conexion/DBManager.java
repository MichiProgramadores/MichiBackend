
package com.MichiSistema.conexion;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Ariana Mulatillo Gomez
 */


public class DBManager {
    private static DBManager instance;

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    private HikariDataSource dataSource;

    // Constructor privado para evitar instanciación externa
    private DBManager() {
        configurar();
    }

    // Método para obtener la instancia del Singleton
    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    // Método para configurar el pool de conexiones
    private void configurar() {
        Properties properties = new Properties();
        String propertiesFile = "db.properties";

        try (InputStream input = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            if (input == null) {
                throw new IOException("No se pudo encontrar el archivo de propiedades: " + propertiesFile);
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo de propiedades", e);
        }

        HikariConfig config = new HikariConfig();
        String dbType = properties.getProperty("db.type").toLowerCase();
        config.setJdbcUrl(properties.getProperty(dbType + ".jdbcUrl"));
        config.setUsername(properties.getProperty(dbType + ".username"));
        config.setPassword(properties.getProperty(dbType + ".password"));

        // Configuración del pool
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(100000); // 2 minutos
        config.setConnectionTimeout(20000); // 20 segundos

        // Configuraciones específicas según el tipo de base de datos
        if ("mysql".equals(dbType)) {
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        } else if ("postgresql".equals(dbType)) {
            // Configuraciones específicas para PostgreSQL (si es necesario)
        }
        dataSource = new HikariDataSource(config);
    }

    public Connection obtenerConexion() throws SQLException {
        return dataSource.getConnection();
    }

    public void cerrarPool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}