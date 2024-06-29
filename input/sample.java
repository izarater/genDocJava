package com.example.usermanagement;

/**
 * Clase principal que contiene el punto de entrada al sistema de gestión de usuarios.
 */
public class UserManagementSystem2 {
    public static void main(String[] args) {
        // Ejemplo de uso de las clases y métodos
        User user = new User("1", "John Doe", "john.doe@example.com");
        AdminUser admin = new AdminUser("2", "Admin User", "admin@example.com", "SuperAdmin");
        UserService userService = new UserService();

        userService.addUser(user);
        userService.addUser(admin);

        System.out.println(userService.getUserInfo("1"));
        System.out.println(userService.getUserInfo("2"));
    }
}

/**
 * Clase que representa a un usuario en el sistema.
 */
class User {
    /**
     * Identificador único del usuario.
     */
    private String id;

    /**
     * Nombre del usuario.
     */
    private String name;

    /**
     * Dirección de correo electrónico del usuario.
     */
    private String email;

    /**
     * Constructor de la clase User.
     * @param id Identificador único del usuario.
     * @param name Nombre del usuario.
     * @param email Dirección de correo electrónico del usuario.
     */
    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Obtiene el identificador único del usuario.
     * @return El identificador único del usuario.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del usuario.
     * @param name El nuevo nombre para el usuario.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la dirección de correo electrónico del usuario.
     * @return La dirección de correo electrónico del usuario.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece la dirección de correo electrónico del usuario.
     * @param email La nueva dirección de correo electrónico del usuario.
     */
    public void setEmail(String email) {
        this.email = email;
    }
}

/**
 * Clase que representa a un usuario administrador, que extiende a la clase User.
 */
class AdminUser extends User {
    /**
     * Nivel de administración del usuario.
     */
    private String adminLevel;

    /**
     * Constructor de la clase AdminUser.
     * @param id Identificador único del usuario.
     * @param name Nombre del usuario.
     * @param email Dirección de correo electrónico del usuario.
     * @param adminLevel Nivel de administración del usuario.
     */
    public AdminUser(String id, String name, String email, String adminLevel) {
        super(id, name, email);
        this.adminLevel = adminLevel;
    }

    /**
     * Obtiene el nivel de administración del usuario.
     * @return El nivel de administración del usuario.
     */
    public String getAdminLevel() {
        return adminLevel;
    }

    /**
     * Establece el nivel de administración del usuario.
     * @param adminLevel El nuevo nivel de administración para el usuario.
     */
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
}

/**
 * Interfaz que define el contrato para el repositorio de usuarios.
 */
interface UserRepository {
    /**
     * Añade un nuevo usuario al repositorio.
     * @param user El usuario a añadir.
     */
    void addUser(User user);

    /**
     * Obtiene la información de un usuario basado en su identificador.
     * @param userId El identificador del usuario.
     * @return La información del usuario.
     */
    String getUserInfo(String userId);
}

/**
 * Clase de servicio que gestiona las operaciones con los usuarios.
 */
class UserService implements UserRepository {
    /**
     * Colección de usuarios gestionada por el servicio.
     */
    private Map<String, User> users = new HashMap<>();

    /**
     * Añade un nuevo usuario al repositorio.
     * @param user El usuario a añadir.
     */
    @Override
    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    /**
     * Obtiene la información de un usuario basado en su identificador.
     * @param userId El identificador del usuario.
     * @return La información del usuario.
     */
    @Override
    public String getUserInfo(String userId) {
        User user = users.get(userId);
        if (user != null) {
            return "User ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail();
        } else {
            return "User not found";
        }
    }
}
