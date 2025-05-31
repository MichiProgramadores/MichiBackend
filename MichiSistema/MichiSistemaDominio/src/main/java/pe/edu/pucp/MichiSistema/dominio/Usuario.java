package pe.edu.pucp.MichiSistema.dominio;

public class Usuario {
    private String nombreUsuario;
    private int id; // ID del sistema, asociado al Trabajador
    private String contrasena;

    // Constructor vacío
    public Usuario() {
        
    }

    // Constructor con parámetros
    public Usuario(int id, String contrasena) {
        this.id = id;
        this.contrasena = contrasena;
        this.nombreUsuario="user";
//        TrabajadorCRUD trabajadorCrud = new TrabajadorCRUD();
//        Trabajador trabaj = trabajadorCrud.obtenerPorId(id);         
//        // Generar el nombre de usuario combinando los nombres y apellidos
//        String nombre = trabaj.getNombres();  // Nombre del trabajador
//        String apellido = trabaj.getApellidos();  // Apellidos del trabajador
//        
//        // Concatenar el nombre y apellido
//        // La primera letra de cada uno en mayúscula, el resto en minúscula
//        this.nombreUsuario = capitalizeFirstLetter(nombre) + capitalizeFirstLetter(apellido);
        
    }
//    private String capitalizeFirstLetter(String text) {
//        if (text == null || text.isEmpty()) {
//            return text;
//        }
//        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
//    }
    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    /**
     * @return the nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * @param nombreUsuario the nombreUsuario to set
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}