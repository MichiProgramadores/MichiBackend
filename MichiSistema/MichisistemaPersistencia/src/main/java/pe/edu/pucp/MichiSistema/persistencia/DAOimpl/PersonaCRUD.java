

package pe.edu.pucp.MichiSistema.persistencia.DAOimpl;
import pe.edu.pucp.MichiSistema.dominio.Persona;
import pe.edu.pucp.MichiSistema.persistencia.DAOimpl.BaseCRUD;
import pe.edu.pucp.MichiSistema.persistencia.DAO.PersonaDAO;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Mulatillo Gomez, Ariana Camila
 */

public class PersonaCRUD extends BaseCRUD<Persona> implements PersonaDAO {
    @Override
    protected Persona createFromResultSet(ResultSet rs) throws SQLException {
        Persona persona = new Persona() {};  // Persona es abstracta, así que no se puede instanciar directamente
        persona.setPersona_id(rs.getInt("persona_id"));
        persona.setNombres(rs.getString("nombres"));
        persona.setApellidos(rs.getString("apellidos"));
        persona.setCelular(rs.getInt("celular"));
        persona.setEmail(rs.getString("email"));
        return persona;
    }

    @Override
    protected PreparedStatement getInsertPS(Connection conn, Persona persona) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Persona(nombres, apellidos, celular, email) "
                + "VALUES(?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, persona.getNombres());
        ps.setString(2, persona.getApellidos());
        ps.setInt(3, persona.getCelular());
        ps.setString(4, persona.getEmail());
        return ps; 
    }

    @Override
    protected PreparedStatement getUpdatePS(Connection conn, Persona persona) throws SQLException {
       PreparedStatement ps = conn.prepareStatement("UPDATE Persona SET nombres=?, "
               + "apellidos=?, celular=?, email=? WHERE persona_id=?");
        ps.setString(1, persona.getNombres());
        ps.setString(2, persona.getApellidos());
        ps.setInt(3, persona.getCelular());
        ps.setString(4, persona.getEmail());
        ps.setInt(5, persona.getPersona_id());  // Usamos persona_id como referencia para actualizar
        
       return ps;
    }

    @Override
    protected PreparedStatement getDeletePS(Connection conn, Integer id) throws SQLException {
        PreparedStatement ps = conn.prepareStatement(
            "UPDATE personas SET estado=0 WHERE persona_id=?"
        );
        ps.setInt(1, id);
        return ps;
    }

    @Override
    protected PreparedStatement getSelectByIdPS(Connection conn, Integer id) throws SQLException {
       PreparedStatement ps = conn.prepareStatement("SELECT persona_id, nombres, apellidos, celular,"
               + " email FROM Persona WHERE persona_id=?");
       ps.setInt(1, id); 
       return ps;
    }

    @Override
    protected PreparedStatement getSelectAllPS(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT persona_id, nombres, apellidos,"
                + " celular, email FROM Persona");
        return ps; 
    }

    @Override
    protected void setId(Persona entity, Integer id) {
         entity.setPersona_id(id);
    }
}

