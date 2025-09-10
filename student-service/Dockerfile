# ---------- build stage ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# 1) Önce POM'ları kopyala (kök ve modül)
COPY pom.xml ./pom.xml
COPY student-service/pom.xml student-service/pom.xml

# 2) Maven cache için bağımlılıkları indir (sadece modülü hedefle)
RUN mvn -q -DskipTests -pl student-service -am dependency:go-offline

# 3) Kaynak kodları kopyala ve derle (sadece modül)
COPY student-service/src student-service/src
RUN mvn -q -DskipTests -pl student-service -am clean package

# ---------- run stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Üretilen jar'ı kopyala
COPY --from=build /app/student-service/target/*.jar /app/app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
