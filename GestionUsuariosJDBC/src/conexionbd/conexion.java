package conexionbd;

import java.sql.*;
import java.util.Scanner;
public class conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/gestion_usuarios";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "Casadelocos1."; // Cambia por tu contraseña

    // Conexión a la base de datos
    public static Connection conectar() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException e) {
            System.out.println("❌ Driver JDBC no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar a la base de datos.");
            e.printStackTrace();
        }
        return conexion;
    }

    // INSERTAR
    public void insertarUsuario(Usuario usuario) {
        try (Connection conn = conectar()) {
            String sql = "INSERT INTO usuarios (nombre, correo, edad) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setInt(3, usuario.getEdad());

            stmt.executeUpdate();
            System.out.println("✅ Usuario insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar usuario.");
            e.printStackTrace();
        }
    }

    // LISTAR
    public void listarUsuarios() {
        try (Connection conn = conectar()) {
            String sql = "SELECT * FROM usuarios";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nombre: " + rs.getString("nombre"));
                System.out.println("Correo: " + rs.getString("correo"));
                System.out.println("Edad: " + rs.getInt("edad"));
                System.out.println("------------------------");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al listar usuarios.");
            e.printStackTrace();
        }
    }

    // ACTUALIZAR
    public void actualizarUsuario(int id, Usuario usuario) {
        try (Connection conn = conectar()) {
            String sql = "UPDATE usuarios SET nombre = ?, correo = ?, edad = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCorreo());
            stmt.setInt(3, usuario.getEdad());
            stmt.setInt(4, id);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("✅ Usuario actualizado correctamente.");
            } else {
                System.out.println("⚠️ No se encontró un usuario con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar usuario.");
            e.printStackTrace();
        }
    }

    // ELIMINAR
    public void eliminarUsuario(int id) {
        try (Connection conn = conectar()) {
            String sql = "DELETE FROM usuarios WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("🗑️ Usuario eliminado correctamente.");
            } else {
                System.out.println("⚠️ No se encontró un usuario con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar usuario.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        conexion con = new conexion();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENÚ DE USUARIOS ===");
            System.out.println("1. Insertar usuario");
            System.out.println("2. Listar usuarios");
            System.out.println("3. Actualizar usuario");
            System.out.println("4. Eliminar usuario");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    System.out.print("Edad: ");
                    int edad = scanner.nextInt();
                    scanner.nextLine();
                    Usuario nuevo = new Usuario(nombre, correo, edad);
                    con.insertarUsuario(nuevo);
                    break;
                case 2:
                    con.listarUsuarios();
                    break;
                case 3:
                    System.out.print("ID del usuario a actualizar: ");
                    int idActualizar = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = scanner.nextLine();
                    System.out.print("Nuevo correo: ");
                    String nuevoCorreo = scanner.nextLine();
                    System.out.print("Nueva edad: ");
                    int nuevaEdad = scanner.nextInt();
                    scanner.nextLine();
                    Usuario actualizado = new Usuario(nuevoNombre, nuevoCorreo, nuevaEdad);
                    con.actualizarUsuario(idActualizar, actualizado);
                    break;
                case 4:
                    System.out.print("ID del usuario a eliminar: ");
                    int idEliminar = scanner.nextInt();
                    scanner.nextLine();
                    con.eliminarUsuario(idEliminar);
                    break;
                case 0:
                    System.out.println("👋 Saliendo del programa...");
                    break;
                default:
                    System.out.println("❗ Opción inválida.");
            }
        } while (opcion != 0);

        scanner.close();
    }
}

