
# DEBUG_README.md

## Remote Debug Setup (Docker + Tomcat + Eclipse)

This document explains how to debug the **Order Manager** application running inside Docker using **Eclipse Remote Debugging**.

---

# Architecture

Eclipse Debugger
        │
        │ JDWP (port 5005)
        ▼
Host Machine
        │
        │ Docker Port Mapping
        ▼
Docker Container
        │
        ▼
Tomcat JVM (JDWP Agent Enabled)

---

# Debug Port Configuration

The JVM inside the container must be started with the **JDWP debug agent**.

Docker Compose configuration:

```yaml
services:
  tomcat:
    build: .
    container_name: order-manager-tomcat
    ports:
      - "8080:8080"
      - "5005:5005"
    environment:
      JAVA_TOOL_OPTIONS: >
        -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005
```

Explanation:

| Parameter | Meaning |
|---|---|
| transport=dt_socket | Debug over socket |
| server=y | JVM waits for debugger |
| suspend=n | Application starts immediately |
| address=5005 | Debug port |

---

# Dockerfile

The container exposes the debug port.

```dockerfile
FROM maven:3.9.9-eclipse-temurin-8 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM tomcat:9.0-jdk8-temurin

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/target/order-manager.war /usr/local/tomcat/webapps/order-manager.war

EXPOSE 8080
EXPOSE 5005

CMD ["catalina.sh", "run"]
```

---

# Start the Application

Run the containers:

```bash
docker compose up --build
```

Verify the debug port is active:

```bash
docker compose logs tomcat
```

Expected output:

```
Listening for transport dt_socket at address: 5005
```

---

# Eclipse Configuration

Open Eclipse and configure **Remote Java Debugging**.

Steps:

1. Run
2. Debug Configurations
3. Remote Java Application
4. Create new configuration

Configuration:

Project: order-manager
Connection Type: Standard (Socket Attach)
Host: localhost
Port: 5005

Click **Debug**.

---

# Debug Workflow

1. Start containers

docker compose up

2. Set breakpoints in Eclipse

3. Start Remote Debug

4. Trigger endpoint

http://localhost:8080/order-manager/api/...

5. Execution stops at breakpoint.

---

# Optional: Debug Startup Code

If you want Tomcat to **wait for the debugger before starting**, change:

suspend=n

to

suspend=y

---

# Troubleshooting

## Connection Refused

Check container status:

docker ps

You should see:

0.0.0.0:8080->8080/tcp
0.0.0.0:5005->5005/tcp

---

## Verify Debug Port

nc -vz localhost 5005

Expected:

Connection to localhost 5005 succeeded

---

## Breakpoints Not Triggering

Possible causes:

- WAR file not rebuilt
- Source code mismatch
- Breakpoint in code not executed

Rebuild container:

docker compose down
docker compose up --build

---

# Useful Commands

Show containers:

docker ps

View logs:

docker compose logs -f tomcat

Restart environment:

docker compose down
docker compose up --build

---

# End

Remote debugging is now available for the **Order Manager** application.
