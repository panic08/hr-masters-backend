# HR Masters

HR Masters is a service providing an easy way to search for employees using simple queries. The project includes DevOps tools and monitoring to ensure stability and availability.

## Technology Stack

- **Backend**: Java, Spring, jOOQ, Flyway
- **Database**: PostgreSQL
- **Monitoring**: Micrometer, Grafana, Prometheus
- **Containerization**: Docker, Docker-Compose

## About the Project

HR Masters enables HR professionals to search for potential employees based on various parameters with simple queries.

## Getting Started

1. **Clone the repository**
    ```bash
    git clone https://github.com/panic08/hr-masters-service.git
    cd hr-masters-service
    ```

2. **Run using Docker Compose**
    ```bash
    docker-compose up --build
    ```

3. **Access metrics and monitoring**
    - **Grafana**: [http://localhost:3000](http://localhost:3000)
    - **Prometheus**: [http://localhost:9090](http://localhost:9090)

## Project Structure

- `main-service/` - Application source code
- `devops/` - DevOps related configurations
    - `docker/` - Docker and Docker Compose configurations
    - `metrics/` - Grafana and Prometheus configurations

