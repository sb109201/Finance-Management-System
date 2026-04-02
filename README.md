# Finance-Management-System
Spring Boot backend for managing financial records with CRUD APIs and dashboard summary (income, expenses, balance).
---

## 🚀 Features

- Add income and expense records
- View all records
- Delete records
- Dashboard summary (total income, expenses, balance)

---

## 🛠️ Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- MySQL / H2 Database

---

## 📂 API Endpoints

### ➤ Create Record
POST /api/records

### ➤ Get All Records
GET /api/records

### ➤ Get Record by ID
GET /api/records/{id}

### ➤ Delete Record
DELETE /api/records/{id}

### ➤ Dashboard Summary
GET /api/dashboard

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
