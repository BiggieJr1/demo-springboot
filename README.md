# 📝 API REST de Gestión de Tareas (To-Do List)

Esta es una API RESTful desarrollada con **Spring Boot** para gestionar una lista de tareas. Permite realizar operaciones CRUD completas (Crear, Leer, Actualizar y Eliminar), incluye persistencia de datos en **MySQL** y soporte para actualizaciones parciales (PATCH).

## 🚀 Tecnologías Usadas

* **Java 17** (o superior)
* **Spring Boot 3** (Web, Data JPA)
* **MySQL** (Persistencia de datos)
* **Maven** (Gestión de dependencias)

---

## ⚙️ Configuración y Ejecución

### 1. Requisitos Previos
Asegúrate de tener instalado:
* Java JDK 17+
* MySQL Server

### 2. Base de Datos
Ingresa a tu cliente de MySQL y ejecuta el siguiente comando para crear la base de datos (Spring se encargará de crear las tablas):

```sql
CREATE DATABASE tareas_db;
