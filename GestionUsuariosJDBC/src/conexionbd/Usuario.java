package conexionbd;

public class Usuario {
    private int id;
    private String nombre;
    private String correo;
    private int edad;

    public Usuario(String nombre, String correo, int edad) {
        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public int getEdad() { return edad; }

    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setEdad(int edad) { this.edad = edad; }
}
