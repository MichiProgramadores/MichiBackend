package com.MichiSistema.persistencia.CRUD;

import com.MichiSistema.conexion.DBManager;
import com.MichiSistema.persistencia.dao.BaseDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ariana Mulatillo Gomez
 */
public abstract class BaseCRUD<T> implements BaseDAO<T> {

    protected abstract PreparedStatement getInsertPS(Connection conn, T entity) throws SQLException;
    protected abstract PreparedStatement getUpdatePS(Connection conn, T entity) throws SQLException;
    protected abstract PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException;
    protected abstract PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException;
    protected abstract PreparedStatement getSelectAllPS(Connection conn) throws SQLException;

    protected abstract T createFromResultSet(ResultSet rs) throws SQLException;
    protected abstract void setId(T entity, Integer id);

    @Override
    public void insertar(T entity) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = getInsertPS(conn, entity)) {
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    setId(entity, rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar entidad", e);
        }
    }

    @Override
    public T obtenerPorId(Integer id) {
        System.out.println("HOLA ESTOY ENTRANDO AQUI JEJE");
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = getSelectByIdPS(conn, id);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return createFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener entidad", e);
        }
        return null;
    }

    @Override
    public List<T> obtenerTodos() {
        List<T> entities = new ArrayList<>();
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = getSelectAllPS(conn);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                entities.add(createFromResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar entidades", e);
        }
        return entities;
    }

    @Override
    public void actualizar(T entity) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = getUpdatePS(conn, entity)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar entidad", e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        try (Connection conn = DBManager.getInstance().obtenerConexion();
             PreparedStatement ps = getDeletePS(conn, id)) {

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar entidad", e);
        }
    }
}
