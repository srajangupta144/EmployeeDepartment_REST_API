# 🏢 Employee-Department REST API

A Spring Boot project that provides a RESTful API for managing **Employees** and **Departments**, built using the **Spring MVC architecture**, integrated with **Hibernate** for database interaction, and connected to a **MySQL** database.

## 📌 Features

- ✅ Create, Read, Update (PUT/PATCH), and Delete operations for Employees and Departments.
- 🔁 Bi-directional mapping between Employee and Department.
- 📄 View all employees of a department.
- 🔍 Get employee details along with department information.
- 📑 Pagination and Sorting for listing endpoints.
- 📦 Follows REST principles and clean code practices.

## 🛠️ Technologies Used

- **Java**
- **Spring Boot**
- **Spring MVC**
- **Spring Data JPA (Hibernate)**
- **MySQL**
- **Maven**
- **Postman** (for testing)

## 🗂️ Project Structure

```bash
src/
 └── main/
     ├── java/
     │   └── com/yourname/employeedepartment/
     │       ├── controller/
     │       ├── service/
     │       ├── repository/
     │       ├── model/
     │       └── dto/
     └── resources/
         ├── application.properties
````

## 🔗 API Endpoints

### Department Endpoints

* `POST /departments` – Add a new department
* `GET /departments` – Get list of departments (with pagination & sorting)
* `GET /departments/{id}` – Get department by ID
* `GET /departments/{id}/employees` – Get all employees in a department
* `PUT /departments/{id}` – Full update
* `PATCH /departments/{id}` – Partial update
* `DELETE /departments/{id}` – Delete department

### Employee Endpoints

* `POST /employees` – Add a new employee
* `GET /employees` – Get list of employees (with pagination & sorting)
* `GET /employees/{id}` – Get employee by ID (includes department details)
* `PUT /employees/{id}` – Full update
* `PATCH /employees/{id}` – Partial update
* `DELETE /employees/{id}` – Delete employee

## ⚙️ Configuration

In `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employeedb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 📦 How to Run

1. Clone the repository

   ```bash
   git clone https://github.com/yourusername/employee-department-api.git
   ```

2. Navigate to the project folder

   ```bash
   cd employee-department-api
   ```

3. Configure your `application.properties` with your MySQL credentials.

4. Run the application

   ```bash
   mvn spring-boot:run
   ```

5. Access API using Postman or any API testing tool at

   ```
   http://localhost:8080/
   ```

## 📝 License

This project is licensed under the MIT License.

---

## 🙌 Acknowledgements

* Spring Boot Documentation
* Hibernate Documentation
* MySQL
* Postman

---

Feel free to contribute or raise issues. ⭐ the repo if you found it helpful!
