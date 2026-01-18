# 📌 Java Swing Desktop Application

## 📖 Overview

This project is a **Java-based desktop application built using Swing GUI**. It provides an interactive graphical interface for users to perform operations through buttons, forms, and dialogs. The application follows **Object-Oriented Programming (OOP)** principles and uses **event-driven programming** to respond to user actions.

The project is designed to run locally on any system that has **Java installed** and does not require a web browser or server.

This project demonstrates:

* Java fundamentals
* GUI development using Swing
* Event handling
* Maven-based project structure
* GitHub Actions CI workflows

---

## 🚀 Features

✅ Graphical User Interface (GUI) using Java Swing
✅ Interactive buttons, forms, and windows
✅ Event-driven behavior (responds to user actions)
✅ Modular object-oriented design
✅ Maven build support
✅ GitHub Actions CI integration
✅ Cross-platform support (Windows, Linux, macOS)

---

## 🛠 Technologies Used

* **Java (JDK 17)**
* **Java Swing**
* **Maven**
* **GitHub Actions**
* **Git & GitHub**

---

## 💻 How to Run the Project (Locally)

### ✅ Prerequisites

Make sure you have:

* Java JDK 17 or later
* Maven installed
* Git installed (optional)

Check:

```bash
java -version
mvn -version
```

---

### ▶ Method 1: Run Using Maven

```bash
git clone <your-repo-url>
cd <project-folder>
mvn clean compile
mvn exec:java
```

---

### ▶ Method 2: Run Using JAR File

```bash
mvn clean package
java -jar target/*.jar
```

---

### ▶ Method 3: Run from IDE (Recommended)

1. Open project in **IntelliJ IDEA / Eclipse / VS Code**
2. Locate the `Main` class
3. Click **Run**

---

## 🧪 Testing

```bash
mvn test
```

(Note: No automated tests are included yet — this command validates the build pipeline.)

---

## 🧱 Build

```bash
mvn clean package
```

Generates a runnable JAR file inside the `target/` directory.

---

## 🐳 Docker Support (Experimental)

This project uses **Java Swing GUI**, which requires a graphical display.
Standard Docker containers are **headless**, so the application cannot run normally inside Docker without advanced GUI forwarding.

Dockerization attempts are included for educational purposes only.

---

## ⚙ GitHub Actions (CI/CD)

This repository includes:

* Automatic build on push
* Pull request validation
* Artifact generation
* Dependency security scanning
* Release automation

---

## 🎯 Project Type

| Category         | Value        |
| ---------------- | ------------ |
| Language         | Java         |
| Framework        | Swing        |
| Application Type | Desktop GUI  |
| Architecture     | Event-driven |
| Deployment       | Local system |

---

## 👨‍💻 Author

**Wahib Choudry**
---

## 📜 License

This project is for academic and educational purposes.
