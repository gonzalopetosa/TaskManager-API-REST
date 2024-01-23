### TaskManager - API REST

#### about
En este proyecto hago practica de lo aprendido sobre API REST, Spring Boot, Mockito, entre demas tecnologias.
El funcionamiento de la aplicacion consta de 2 entidades usuarios y tareas, esta segunda entidad depende de lois usuarios ya que las tareas se crean para un usuario creado en la base de daros. Con ambas entidades se puede hacer las operaciones basicas de una API REST, CREATE-GET-PUT-DELETE.
Para la persistencia de los datos se utilizo PostgreSQL.

# Crear User
------------
endopoint:  **/api/TK/user/create** (CREATE)
```java
{
        "fullName": "usuario",
        "email": "usuario@gmail.com",
        "phoneNumber": "+54 6477-8656",
        "gender": "male",
        "country": "Argentina"
}
```
------------
# Obtener todo los usuarios

------------
endopoint:  **/api/TK/users** (GET) 

------------

------------
# Obtener usuario por ID

------------
endopoint:  **/api/TK/user/{id}** (GET) 

------------

------------
# Actualizar usuario

------------
endopoint:  **/api/TK/user/update/{id}** (PUT)
```JAVA
{
        "fullName": "modificacion",
        "email": "md@gmail.com",
        "phoneNumber": "+54 6477-8656",
        "gender": "male",
        "country": "Argentina"
}
```

------------

# Borrar usuario

------------
endopoint:  **/api/TK/user/delete/{id}** (DELETE)

------------

# Crear Task  

------------
endopoint:  **/api/TK/task/{userID}**  (CREATE)

```
{
    "monthCreation": "Diciembre",
    "data": "tarea"    
}
```
------------

# Obtener todas las tareas

------------

endopoint:  **/api/TK/tasks**  (GET)

------------

# Obtener todas las tareas de un usuario

------------

endopoint:  **/api/TK/task/{userID}**  (GET)

------------

# Actualizar una tarea

------------

endopoint:  **/api/TK/task/{taskId}**  (PUT)
```
{
    "monthCreation": "Diciembre",
    "data": "modificacion"    
}
```
------------

# Borrar una tarea

------------

endopoint:  **/api/TK/task/{taskId}**   (DELETE)

------------
