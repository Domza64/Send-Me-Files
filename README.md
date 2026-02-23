# Send Me Files

**Send Me Files** is a web app I mostly built for fun, but also as a project to experiment with and learn different technologies.

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

   Copy the file named `EXAMPLE.env`, rename it to `.env`

4. **Run docker compose**:
   ```
   docker compose -f compose-dev.yaml up
   ```

4. **Run both Spring Boot apps**.

> Frontend is served on **localhost:5173** automatically when running docker compose
> Core backend is listening on **localhost:8080**
> [PGWEB](https://github.com/sosedoff/pgweb) Database UI is served on **localhost:9090**
> [KAFBAT](https://github.com/kafbat/kafka-ui) Kafka UI is served on **localhost:8085**
> Minio UI (dev s3 simulation) is served on **localhost:9001**

- Add temp user for testing:
```bash 
curl -v -X POST http://localhost:8080/api/auth/addNewUser \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","email":"admin@example.com","password":"admin","role":"ADMIN"}'
```

## Why did I build this?

This project is mainly built for fun and personal use and although the app is very simple, it demonstrates how various technologies can work together, which is one of the things I love most about full-stack web development.

## TODO:

- [ ] Cleanup comments TODOs and document everything
- [ ] Consume file zip messages
- [ ] implement file downloads (single and zip)
- [ ] Delete uploaded files after download
- [ ] Rate limiting and protections
- [ ] CI/CD pipeline, frontend on Cloudflare pages... backend and db?
- [ ] username vs email or both for authentication, principal? Currently big mess!
- [ ] Use httponly cookies and csrf tokens instead of just storing JWT in session storage
- [ ] Logging: [SEQ](https://datalust.co/) - Microservices, [Sentry](https://sentry.io/welcome/) - Frontend
- [ ] Dependency scanning, aikido, snyk...

Made with ❤️ by [Domza64](https://www.domza.xyz)
