package pe.edu.pucp.MichiSistema.dominio;

/**
 *
 * @author OsquiCnapi
 */
public abstract class Persona {
    
    protected int persona_id;
    protected String nombres;
    protected String apellidos;
    protected int celular;
    private String email;
    private boolean estado;
    // Constructor con parámetros
    public Persona(String nombres, String apellidos, int celular, String email){

        this.nombres = nombres;           // Asigna el valor de nombres
        this.apellidos = apellidos;       // Asigna el valor de apellidos
        this.celular = celular;           // Asigna el valor de celular
        this.email = email; 
        estado=true;
    }
    // Constructor sin parámetros (con valores predeterminados)
    public Persona() {
        this.persona_id = 0;     // Valor predeterminado para persona_id (vacío)
        this.nombres = "";        // Valor predeterminado para nombres (vacío)
        this.apellidos = "";      // Valor predeterminado para apellidos (vacío)
        this.celular = 0;         // Valor predeterminado para celular (0)
        this.email = "";          // Valor predeterminado para email (vacío)
    }

    public int getPersona_id() {
        return persona_id;
    }

    public void setPersona_id(int persona_id) {
        this.persona_id = persona_id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
}
