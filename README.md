
# Send Me Files

**Send Me Files** is a web app I built to easily transfer files, specifically pictures and videos (though it supports any type of file). I created it for my own needs, as a way to receive files without relying on apps like WhatsApp. It’s also a cool portfolio project that demonstrates my skills in full-stack development.

---

### Development Setup

To run this webapp in **development mode**:

1. **Ensure Docker is installed**

2. **Clone the repo**:

    ```bash
    git clone https://github.com/Domza64/Send-Me-Files.git
    cd Send-Me-Files
    ```

3. **Create .env.development in /frontend**

   Copy the file named `EXAMPLE.env.development` and rename it to `.env.development`.

4. **Create .env in project root**

   Copy the file named `EXAMPLE.env` and rename it to `.env`.

5. **Run the Spring Boot app** (backend):

   Simply run the Spring Boot application (`./mvnw spring-boot:run` or via your IDE).

> **Note:** Frontend will be automatically served on localhost:5173 by default.


### Why did I build this?

This project is mainly built for personal use, but also as a **portfolio project** to showcase my ability to use:
- **Frontend**: React (not the best React app ever since primary focus is on backend)
- **Backend**: Spring Boot
- **Docker**: For containerization and scalability
- **JWT Authentication**: To manage secure user sessions

Although the app is very simple, it demonstrates how various technologies can work together, which is one of the things I love most about web development. This project showcases my ability to build full-stack applications, manage secure user sessions, and leverage containerization for modern web app deployment.
