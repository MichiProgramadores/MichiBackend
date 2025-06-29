
package com.MichiSistema.conexion;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Properties;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author Ariana Mulatillo Gomez
// */
//
//
//public class DBManager {
//    private static DBManager dbManager;
//    
//    private String host;
//    private int puerto;
//    private String esquema;
//    private String usuario;
//    private String password;
//    
//    private DBManager() throws IOException {
//        cargarProperties();
//    }
//    
//    public synchronized static DBManager getInstance()  {
//        if (dbManager == null) {
//            createInstance();
//        }
//        return dbManager;
//    }
//    
//    private static void createInstance() {
//        try {
//            dbManager = new DBManager();
//        } catch (IOException ex) {
//            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    public Connection obtenerConexion()  {
//        try {
//            /* 
//            Por ahora creamos una conexion cada vez que se necesita acceder a la base de datos, 
//            por ser una aplicacion academica es una practica aceptable, en un sistema productivo
//            se debe usar un pool de conexiones.
//            */
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            String cadenaConexion = cadenaConexion(host, puerto, esquema);
//            return DriverManager.getConnection(cadenaConexion, usuario, password);
//        }
//        catch (ClassNotFoundException | SQLException e) {
//            System.err.println(e);
//        }
//        return null;
//    }
//    
//    private void cargarProperties() throws IOException {
//        Properties properties = new Properties();
//        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
//            if (input == null) {
//                System.err.println("No se pudo abrir el archivo db.properties");
//                return;
//            }
//            
//            properties.load(input);
//            
//            host = properties.getProperty("db.host");
//            puerto = Integer.parseInt(properties.getProperty("db.puerto"));
//            esquema = properties.getProperty("db.esquema");
//            usuario = properties.getProperty("db.usuario");
//            password = properties.getProperty("db.password");
//        }
//        catch (IOException e) {
//            System.err.println("No se pudo cargar el archivo db.properties");
//            throw e;
//        }
//    }
//    
//    private String cadenaConexion(String host, int puerto, String esquema) {
//        return String.format("jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true", host, puerto, esquema);
//    }
//}


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static DBManager instance;
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
        config.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.maxPoolSize")));
        config.setMinimumIdle(Integer.parseInt(properties.getProperty("db.minIdle")));  
        config.setIdleTimeout(Integer.parseInt(properties.getProperty("db.idleTimeout")));
        config.setConnectionTimeout(Integer.parseInt(properties.getProperty("db.connectionTimeout")));

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
    public HikariDataSource getDataSource() {
        return dataSource;
    }
    public static synchronized void resetInstance() {
    if (instance != null) {
        instance.cerrarPool();
        instance = null; // Fuerza la recreación en el próximo getInstance()
    }
    }
    public void cerrarPool() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}