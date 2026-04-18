# Proyecto Gestión de Usuarios con JDBC

Aplicación de escritorio en Java para la gestión básica de usuarios utilizando JDBC para conexión a MySQL.

## Requisitos

- Java 17 o superior
- MySQL Server
- Base de datos `jdbc_2026` con tabla `usuarios`

## Estructura del Proyecto

```
src/main/java/com/mycompany/
├── model/
│   └── Usuario.java
├── dao/
│   └── UsuarioDAO.java
├── dao/impl/
│   └── UsuarioDAOImpl.java
├── conexion/
│   └── ConexionDB.java
└── controller/
    └── Principal.java
```

## Configuración de Base de Datos

Crear la base de datos y tabla:

```sql
CREATE DATABASE jdbc_2026;
USE jdbc_2026;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(255),
    contraseña VARCHAR(255)
);
```

## Ejecución

### Opción 1: Desde VS Code

1. Abrir la carpeta del proyecto en VS Code
2. Ir a la pestaña "Run and Debug" (Ctrl+Shift+D)
3. Seleccionar "Launch Principal"
4. Presionar F5 o el botón de play

### Opción 2: Desde Terminal

```bash
# Compilar
javac -cp "C:\Users\Lenovo\.m2\repository\com\mysql\mysql-connector-j\9.6.0\mysql-connector-j-9.6.0.jar" -d target/classes src/main/java/com/mycompany/model/Usuario.java src/main/java/com/mycompany/dao/UsuarioDAO.java src/main/java/com/mycompany/dao/impl/UsuarioDAOImpl.java src/main/java/com/mycompany/conexion/ConexionDB.java src/main/java/com/mycompany/controller/Principal.java

# Ejecutar
java -cp "target/classes;C:\Users\Lenovo\.m2\repository\com\mysql\mysql-connector-j\9.6.0\mysql-connector-j-9.6.0.jar" com.mycompany.controller.Principal
```

## Funcionalidades

- Insertar nuevos usuarios
- Listar todos los usuarios
- Actualizar contraseña de usuario
- Eliminar usuario por ID

## Dependencias

- MySQL Connector/J 9.6.0 (incluido en el classpath)