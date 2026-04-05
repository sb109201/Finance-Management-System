# Finance-Management-System
Spring Boot backend for managing financial records with CRUD APIs and dashboard summary (income, expenses, balance).
---
# 🚀 Features

### 1. User & Role Management
- Create, read, update, and manage users
- Assign roles: **Viewer, Analyst, Admin**
- Manage user status (**Active/Inactive**)  
- Restrict API access based on user role:
  - **Viewer:** Can view dashboard data only  
  - **Analyst:** Can view records and summary analytics  
  - **Admin:** Full access to create, update, delete records and manage users  

### 2. Financial Records Management
- Add, update, delete, and view financial records  
- Record fields include:
  - `Amount`
  - `Type` (Income / Expense)
  - `Category` (auto-categorized based on description)
  - `Date` (yyyy-MM-dd)
  - `Description/Notes`
- Filter records by:
  - Type (`EXPENSE` / `INCOME`)
  - Category (`Food`, `Travel`, `Rent`, etc.)
  - Date range
- Paginated record listing

### 3. Dashboard Summary APIs
- Total income, total expenses, net balance  
- Category-wise totals  
- Recent activity (latest 5 records)  
- Monthly/weekly trend summaries  

### 4. Access Control & Validation
- Role-based restrictions enforced in the backend  
- Validation for missing or invalid inputs using `@Valid` and proper HTTP status codes  
- Clear error messages for invalid operations or unauthorized access  

### 5. Optional Enhancements
- Pagination for record listing (`/paginated` endpoint)  
- Auto-categorization of records from description  
- Soft delete support (can be extended)  
- Prepared for token-based authentication (mock ready)  
- API documentation ready (Postman collection can be provided)  

---



## 💻 Tech Stack

- **Language:** Java 17  
- **Framework:** Spring Boot 3  
- **Database:** H2 (in-memory, can be switched to MySQL/PostgreSQL)  
- **ORM:** Spring Data JPA (Hibernate)  
- **Validation:** Jakarta Validation API (`@Valid`, `@NotNull`, `@NotBlank`)  
- **JSON Handling:** Jackson (`@JsonFormat`)  
- **Build Tool:** Maven  
- **Testing (optional):** JUnit 5, Spring Boot Test  

---

## 📂 API Endpoints

| Method | Endpoint | Description | Role |
|--------|---------|-------------|------|
| GET | `/api/records?userId={id}` | Get all records | Admin, Analyst |
| GET | `/api/records/{id}?userId={id}` | Get record by ID | Admin, Analyst |
| POST | `/api/records?userId={id}` | Create new record | Admin only |
| PUT | `/api/records/{id}?userId={id}` | Update record | Admin only |
| DELETE | `/api/records/{id}?userId={id}` | Delete record | Admin only |
| GET | `/api/records/filter?userId={id}&type=EXPENSE` | Filter by type | Admin, Analyst |
| GET | `/api/records/filter?userId={id}&category=Food` | Filter by category | Admin, Analyst |
| GET | `/api/records/paginated?userId={id}&page=0&size=5` | Paginated listing | Admin, Analyst |

> Note: `{id}` refers to the **userId** used to authorize the action.

---

## ▶️ How to Run

1. Clone the repository
2. Open in IntelliJ
3. Run `FinanceApplication.java`
4. Test APIs using Postman

---

## 📸 Sample JSON

```json
{
  "type": "EXPENSE",
  "amount": 500,
  "category": "Food",
  "date": "2026-04-02",
  "description": "Lunch"
}

## 📝 Project Overview

This backend application is designed for a **finance dashboard system**, enabling users to manage financial records with strict **role-based access control**.  

The backend provides **CRUD operations**, filtering, paginated data retrieval, summary analytics, and optional enhancements like authentication and soft deletes.  

It demonstrates best practices in **API design, data modeling, business logic implementation, validation, and access control**.

---

## ⚙️ Setup Instructions

1. **Clone the repository**

```bash
git clone https://github.com/YourUsername/finance-backend.git
cd finance-backend
