
# Send Me Files

**Send Me Files** is a web app I built for fun. I made it for my own needs, as a way to easily transfer files (i guess mostly pctures and videos) without relying on apps like WhatsApp. It’s also a cool learning and portfolio project.

---

## Development Setup

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

> **Note:** Frontend is automatically served on localhost:5173 and [PGWEB](https://github.com/sosedoff/pgweb) UI is served on localhost:8081 cause it's usefull in dev :)


## Why did I build this?

This project is mainly built for fun and personal use and although the app is very simple, it demonstrates how various technologies can work together, which is one of the things I love most about full-stack web development.

## TODO:

- [ ] Finish all TODOs in code...
- [ ] Implement kafka (do i need it? No. But why not try it out anyway?) (eg. when user wants to download zip of all files, send message to that topic and then consumer will begin zipping it up when it recieves messaage.)
- [ ] Bunch of other stuff...

Made with ❤️ by [Domza64](https://www.domza.xyz)
