# Dockerized-Data-Services (No Spring)

## 📌 Opis projektu

**Dockerized-Data-Services** to projekt demonstrujący integrację **MongoDB** z aplikacją **Java**, wykorzystując **Hibernate OGM** zamiast Springa. Projekt obejmuje operacje **CRUD**, a także **konfigurację Dockera** do uruchamiania powiązanych usług, takich jak **Kafka, Redis oraz MongoDB**.

## 🛠 Wymagania

Aby uruchomić projekt, potrzebujesz:

- **Java 17** lub nowsza
- **Maven** do zarządzania zależnościami
- **Docker + Docker Compose** (do uruchomienia usług MongoDB, Redis, Kafka)

## 🚀 Instalacja

### 1️⃣ Klonowanie repozytorium:
```bash
git clone https://github.com/MatiLUzak/nbdMongoDB.git
cd nbdMongoDB
```

### 2️⃣ Instalacja zależności:
```bash
mvn install
```

### 3️⃣ Uruchomienie niezbędnych usług w Dockerze:

- **MongoDB**:
  ```bash
  cd dockerMongo
  docker-compose up -d
  ```

- **Kafka**:
  ```bash
  cd dockerKafka
  docker-compose up -d
  ```

- **MongoDB + Redis**:
  ```bash
  cd dockerMongoRedis
  docker-compose up -d
  ```

## ⚙️ Konfiguracja aplikacji

Przed uruchomieniem upewnij się, że **plik konfiguracyjny Hibernate OGM** (`persistence.xml`) zawiera poprawne dane do połączenia z uruchomionymi usługami.

## ▶️ Uruchomienie aplikacji

Aby uruchomić aplikację Java, użyj:
```bash
mvn exec:java -Dexec.mainClass="com.twoja.domena.MainClass"
```
ℹ️ Zamień `com.twoja.domena.MainClass` na pełną nazwę klasy głównej Twojego projektu.

## 📂 Struktura projektu

```
nbdMongoDB/
├── dockerKafka/
│   ├── docker-compose.yml
│   └── ...
├── dockerMongo/
│   ├── docker-compose.yml
│   └── ...
├── dockerMongoRedis/
│   ├── docker-compose.yml
│   └── ...
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
│   └── test/
├── .gitignore
├── README.md
└── pom.xml
```

- **dockerKafka/** – konfiguracja Dockera dla Kafki
- **dockerMongo/** – konfiguracja Dockera dla MongoDB
- **dockerMongoRedis/** – konfiguracja Dockera dla MongoDB i Redis
- **src/** – kod źródłowy projektu
- **pom.xml** – plik konfiguracyjny Mavena

## ✍️ Autor

- **MatiLUzak** – [GitHub](https://github.com/MatiLUzak)

## 📜 Licencja

Ten projekt jest licencjonowany na podstawie licencji MIT. Szczegóły znajdują się w pliku `LICENSE`.
