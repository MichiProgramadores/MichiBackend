

package com.MichiSistema.persistencia.CRUD;

import com.MichiSistema.conexion.DBManager;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Usuario
 */
public class BDTest {
    @Test
    @Disabled("Deshabilitado temporalmente para pruebas")
    public void testMultiplesConexiones() throws SQLException {
        final int numConexiones = 5;
        List<Connection> conexiones = new ArrayList<>();

        for (int i = 0; i < numConexiones; i++) {
            conexiones.add(DBManager.getInstance().obtenerConexion());
        }

        assertEquals(numConexiones, conexiones.size());

        // Cierra todas las conexiones
        conexiones.forEach(conn -> {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                fail("Error al cerrar conexión: " + e.getMessage());
            }
        });
    }
    @Test
    @Disabled("Deshabilitado temporalmente para pruebas")
    public void testMetricasPool() {
        HikariDataSource dataSource = DBManager.getInstance().getDataSource(); // Necesitarás un getter

        System.out.println("Métricas del Pool:");
        System.out.println("Conexiones activas: " + dataSource.getHikariPoolMXBean().getActiveConnections());
        System.out.println("Conexiones inactivas: " + dataSource.getHikariPoolMXBean().getIdleConnections());
        System.out.println("Total conexiones: " + dataSource.getHikariPoolMXBean().getTotalConnections());

        assertTrue(dataSource.getHikariPoolMXBean().getIdleConnections() >= 0);
    }
    
    @Test
    @Disabled("Deshabilitado temporalmente para pruebas")
    public void testEstresPool() throws SQLException {
        final int numIteraciones = 100;

        for (int i = 0; i < numIteraciones; i++) {
            try (Connection conn = DBManager.getInstance().obtenerConexion()) {
                // Ejecuta una consulta simple
                conn.createStatement().execute("SELECT 1");
            } // La conexión se cierra automáticamente aquí
        }

        HikariDataSource dataSource = DBManager.getInstance().getDataSource();
        assertEquals(0, dataSource.getHikariPoolMXBean().getActiveConnections());
    }
    @Test
    @Disabled("Deshabilitado temporalmente para pruebas")
    public void testRecuperacionConexion() {
        // Simula un fallo (ej: cerrando el pool abruptamente)
        DBManager.getInstance().cerrarPool();
        DBManager.resetInstance();
        try {
            Connection conn = DBManager.getInstance().obtenerConexion();
            assertNotNull(conn); // El pool debería reiniciarse automáticamente
        } catch (SQLException e) {
            fail("El pool no se recuperó correctamente: " + e.getMessage());
        }
    }
}
