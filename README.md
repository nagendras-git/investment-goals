# 📋 Investment Goals Tracker

A personal investment goals tracker built with Spring Boot + HTML/CSS/JS.

---

## 🗂 Project Structure

```
investment-goals/
├── src/main/java/com/investment/goals/
│   ├── InvestmentGoalsApplication.java   ← Main class
│   ├── entity/
│   │   ├── Goal.java                     ← Goal entity
│   │   └── Fund.java                     ← Fund entity (child of Goal)
│   ├── dto/
│   │   └── GoalDTO.java                  ← Request & Response DTOs
│   ├── repository/
│   │   └── GoalRepository.java           ← JPA Repository
│   ├── service/
│   │   └── GoalService.java              ← Business logic + mapper
│   ├── controller/
│   │   └── GoalController.java           ← REST API endpoints
│   └── config/
│       ├── CorsConfig.java               ← CORS settings
│       ├── DataSeeder.java               ← Seeds default 7 goals on first run
│       └── GlobalExceptionHandler.java   ← Centralized error handling
├── src/main/resources/
│   ├── static/index.html                 ← Frontend (served by Spring Boot)
│   ├── application.properties            ← Base config
│   ├── application-dev.properties        ← H2 in-memory (local dev)
│   └── application-prod.properties       ← PostgreSQL (Railway production)
└── pom.xml
```

---

## 🚀 Local Development

### Prerequisites
- Java 17+
- Maven 3.8+

### Run locally
```bash
# Clone the repo
git clone https://github.com/YOUR_USERNAME/investment-goals.git
cd investment-goals

# Run with dev profile (H2 in-memory DB)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# App starts at: http://localhost:8080
# H2 Console at: http://localhost:8080/h2-console
#   JDBC URL: jdbc:h2:mem:investmentdb
#   Username: sa  |  Password: (empty)
```

---

## 🌐 REST API Endpoints

| Method | Endpoint              | Description          |
|--------|-----------------------|----------------------|
| GET    | `/api/goals`          | Get all goals        |
| GET    | `/api/goals/{id}`     | Get single goal      |
| POST   | `/api/goals`          | Create new goal      |
| PUT    | `/api/goals/{id}`     | Update goal          |
| DELETE | `/api/goals/{id}`     | Delete goal          |
| PUT    | `/api/goals/reorder`  | Reorder goals        |

### Sample Request Body (POST/PUT)
```json
{
  "name": "Son's Graduation",
  "category": "Education",
  "emoji": "🎓",
  "status": "on-track",
  "target": "₹1.3 Crore",
  "targetYear": 2040,
  "current": "Corpus building",
  "note": "Exit: 2037 → Move to HDFC BAF → SWP from 2040",
  "funds": [
    { "name": "HDFC Mid Cap", "amount": "₹15,000" },
    { "name": "Bandhan Small Cap", "amount": "₹7,000" }
  ]
}
```

### Status Values
| Value         | Display       |
|---------------|---------------|
| `on-track`    | On Track      |
| `in-progress` | In Progress   |
| `auto-funded` | Auto-Funded   |
| `not-started` | Not Started   |
| `complete`    | Complete ✓    |

---

## ☁️ Deploy to Railway (Free)

### Step 1 — Push to GitHub
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/YOUR_USERNAME/investment-goals.git
git push -u origin main
```

### Step 2 — Deploy on Railway
1. Go to [railway.app](https://railway.app) → Sign in with GitHub
2. Click **New Project** → **Deploy from GitHub repo**
3. Select your `investment-goals` repo
4. Railway auto-detects Spring Boot ✅

### Step 3 — Add PostgreSQL
1. In your Railway project, click **+ New** → **Database** → **PostgreSQL**
2. Railway auto-injects `DATABASE_URL` into your app ✅

### Step 4 — Set Production Profile
In Railway → your service → **Variables**, add:
```
SPRING_PROFILES_ACTIVE=prod
```

### Step 5 — Access Your App
Railway gives you a URL like:
```
https://investment-goals-production.up.railway.app
```

Add it to your iPhone home screen:
- Open URL in Safari
- Tap **Share** → **Add to Home Screen**
- Done! Works like a native app 📱

---

## 📱 Add to iPhone Home Screen

1. Open your Railway app URL in **Safari** (not Chrome)
2. Tap the **Share** button (box with arrow)
3. Scroll down → **Add to Home Screen**
4. Name it "Goals" → tap **Add**

---

## 🔮 Future Enhancements

- [ ] Spring Security + JWT authentication
- [ ] SIP corpus calculator (compound interest projection)
- [ ] Annual review email notifications
- [ ] PDF export of all goals
- [ ] Multi-user support (share with family)
- [ ] Dark mode

---

## 🛠 Tech Stack

| Layer    | Technology              |
|----------|-------------------------|
| Backend  | Spring Boot 3.2, Java 17|
| Database | H2 (dev), PostgreSQL (prod) |
| ORM      | Spring Data JPA + Hibernate |
| Frontend | Vanilla HTML/CSS/JS     |
| Hosting  | Railway.app             |
