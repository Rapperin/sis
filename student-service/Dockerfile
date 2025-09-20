# -------- Build stage --------
FROM eclipse-temurin:17-jre as build
WORKDIR /build

# Tüm repo (pom’lar dahil) konteynıra gelsin
COPY . .

# Sadece student-service modülünü derle/paketle (testleri atla)
RUN mvn -q -DskipTests -pl student-service -am package

# -------- Run stage --------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Modülün jar'ını al
COPY student-service/target/*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
