# 📚 Book Management System

A REST API built with **Spring Boot** for managing books with search, filter, and pagination features.

---

## ✨ Features
- 📖 Book CRUD (Create, Read, Update, Delete)
- 📦 Bulk book insert
- 🔍 Search by author and title keyword
- 🏷️ Filter by genre and max price
- 💰 Price range filter
- 📄 Pagination & Sorting (by price, title, id)
- 🏷️ Get all distinct genres
- 💸 Top 5 cheapest books
- ⚠️ Custom Exception Handling
- ✅ Input Validation
- 🔄 DTO Pattern (Request/Response)

---

## 🛠️ Tech Stack
- Spring Boot | Java 17
- JPA & Hibernate | MySQL
- Maven

---

## ⚙️ Setup
1. Clone the repo
2. Create MySQL database: `book_db`
3. Update `application.properties` with your DB credentials
4. Run: `mvn spring-boot:run`

---

## 🔗 Key Endpoints
GET /books → Get all books
POST /books → Add book
POST /books/bulk → Add multiple books
GET /books/search?author= → Search by author
GET /books/search/title?keyword= → Search by title
GET /books/filter?genre=&maxPrice= → Filter books
GET /books/price-range?min=&max= → Price range
GET /books/paginated?page=&size=&sortBy=&direction= → Paginated
GET /books/genres → All genres
GET /books/top5-cheapest → Top 5 cheapest
