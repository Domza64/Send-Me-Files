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

3. **Create .env in project root**

   Copy the file named `EXAMPLE.env`, rename it to `.env` and fill it out

4. **Run docker compose**:
   ```
   docker compose -f compose-dev.yaml up
   ```

4. **Run both Spring Boot apps**.

> Frontend is served on **localhost:5173** automatically when running docker compose
> [PGWEB](https://github.com/sosedoff/pgweb) Database UI is served on **localhost:8081**
> Core backend is listening on **localhost:8080**


## Why did I build this?

This project is mainly built for fun and personal use and although the app is very simple, it demonstrates how various technologies can work together, which is one of the things I love most about full-stack web development.

## TODO:

- [ ] Cleanup comments TODOs and document everything
- [ ] Add kafka cluster to dev-compose (maybe even some s3 simulation service?)
- [ ] Rate limiting and protections
- [ ] Implement Kafka (do I need it? No. But why not try it out anyway?) (e.g. when user wants to download zip of all files, send message to that topic and then consumer will begin zipping it up when it recieves messaage.)
- [ ] Bunch of other stuff...
- [ ] CI/CD pipeline, frontend on Cloudflare pages... backend and db?
- [ ] username vs email or both for authentication, principal? Currently big mess!
- [ ] Use httponly cookies and csrf tokens instead of just storing JWT in session storage
- [ ] Dependency scanning, aikido, snyk...

Made with ❤️ by [Domza64](https://www.domza.xyz)
