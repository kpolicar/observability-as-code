# Node Dashboard Generator

This Java project demonstrates how to generate Grafana dashboards programmatically using the official [Grafana Foundation SDK for Java](https://grafana.github.io/grafana-foundation-sdk/#java).

## Features

- Uses the official Grafana Foundation SDK for Java
- Generates a complete Node Exporter dashboard with CPU panel
- Uses proper SDK builders for type-safe dashboard creation
- Creates properly formatted Grafana dashboard JSON using `.toJSON()` method
- Includes panel positioning, queries, and styling using SDK builders

## Project Structure

```
grafana/dashboards/generators/
├── pom.xml                                    # Maven configuration with Grafana Foundation SDK
├── README.md                                  # This file
└── src/main/java/com/grafana/dashboard/
    └── NodeDashboard.java                     # Main dashboard generator using SDK
```

## Usage

### Prerequisites
- Java 11 or higher
- Maven 3.6 or higher

### Running the Application

1. Resolve the dependencies
   ```bash
   mvn dependency:resolve
   ```

2. Compile and run:
   ```bash
    mvn compile exec:java
    mvn compile exec:java -q -Dexec.args="-o=here.json"
   ```

3. Run and save to file for Grafana to automatically pick up:
   ```bash
    mvn compile exec:java -q -Dexec.args="-o=../definitions/node.json"
   ```

## Generated Dashboard

The application generates a dashboard with one panel using the Grafana Foundation SDK:

1. **CPU Usage** - Stat panel showing CPU utilization percentage

The panel uses Prometheus queries to fetch metrics from the Node Exporter.

## Dependencies

- **Grafana Foundation SDK** - Official SDK for programmatic dashboard creation

## References

- [Grafana Foundation SDK Documentation](https://grafana.github.io/grafana-foundation-sdk/#java)
- [Grafana Foundation SDK GitHub Repository](https://github.com/grafana/grafana-foundation-sdk)
