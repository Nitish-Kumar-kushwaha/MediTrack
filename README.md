++ MediTrack – Clinic & Appointment Management System

## Project Overview

MediTrack is a modular, object‑oriented, console‑based Java application designed to manage clinic workflows including doctor and patient records, appointment booking and cancellation, and billing. The project illustrates disciplined use of object‑oriented principles, small design patterns, and modern Java idioms (streams, generics, and lambdas) in a clean, maintainable codebase.

Key design emphases: encapsulation of domain entities, separation of concerns between services and utilities, and small, testable components.

## Key Features

- Doctor Management (Create, Read, Update, Delete)
- Patient Management (Create, Read, Update, Delete)
- Appointment booking and cancellation
- Billing system with tax calculation and immutable `BillSummary` objects
- Thread‑safe Singleton `IdGenerator` for unique IDs
- Custom exception types for clear error semantics (e.g., `InvalidDataException`, `AppointmentNotFoundException`)
- Generic in‑memory repository `DataStore<T>`
- Java Streams and Lambdas used for collection processing
- CSV persistence for doctors and patients (simple, portable storage)
- Menu‑driven console user interface for interactive operation

## Architecture Overview

The codebase follows a modular package layout to separate responsibilities and simplify reasoning:

- `entity`: Domain model classes (`Doctor`, `Patient`, `Appointment`, `Bill`, `BillSummary`, `Person`, `Specialization`)
- `service`: Business logic and orchestration (`DoctorService`, `PatientService`, `AppointmentService`)
- `util`: Utilities and infrastructure (`CSVUtil`, `DataStore`, `IdGenerator`, `Validator`)
- `exception`: Custom runtime exceptions used across services
- `interface` / `interfaces`: Small behavioral contracts (e.g., `Searchable`)
- `constants`: Application constants used by utilities and services

This separation simplifies testing, future refactoring (e.g., swapping persistence), and supports incremental feature additions.

## Folder Structure

```
Meditrack/
├── src/
│   └── com/airtribe/meditrack/
│       ├── Main.java
│       ├── constants/
│       ├── entity/
       ├── exception/
       ├── interface/ (or interfaces/)
       ├── service/
       └── util/
├── docs/
│   ├── JVM_Report.md
│   └── Setup_Instructions.md
└── README.md
```

## Technologies Used

- Java 17+ (recommended)
- Java Collections Framework
- Java Streams & Lambdas
- File I/O (CSV read/write using NIO and streams)
- Built‑in concurrency primitives (`AtomicInteger` for `IdGenerator`)
- Git and GitHub for source control and collaboration

## How to Run the Project

1. Prerequisites:
   - Install JDK 17 or later and Git.
   - Recommended IDE: Visual Studio Code (with Java Extension Pack) or IntelliJ IDEA.

2. Clone the repository:

```bash
git clone <repository-url>
cd MediTrack
```

3. Compile and run (command line):

```powershell
# compile all Java sources into the 'out' directory
Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { '"' + $_.FullName + '"' } > files.txt
javac -d out @files.txt

# run the application
java -cp out com.airtribe.meditrack.Main
```

4. Notes:
   - On startup, the application attempts to load `doctors.csv` and `patients.csv` (paths configurable via `Constants`).
   - On exit, data is auto‑saved back to CSV so subsequent runs can resume state.

## Sample Workflow

1. Add a new doctor (the system assigns a unique ID via `IdGenerator`).
2. Add a new patient.
3. Book an appointment between the doctor and the patient on a chosen date.
4. Generate a bill for the appointment (tax calculation applied; `BillSummary` returned).
5. Exit the application — data is saved to CSV automatically.

## Design Patterns and Principles

- Singleton: `IdGenerator` uses the initialization‑on‑demand holder idiom and `AtomicInteger` for thread safety.
- Strategy / Abstraction: `DataStore<T>` abstracts in‑memory storage and can be replaced by a persistent repository implementation later.
- Separation of Concerns / SOLID: Entities model data, services implement business logic, and utilities handle cross‑cutting concerns (validation, I/O).

## Why Certain Choices Were Made

- CSV persistence was chosen for prototype simplicity and portability; it allows easy inspection and manual editing of persisted state.
- Immutable `BillSummary` objects protect billing snapshots from accidental mutation and simplify sharing between components.
- Custom exceptions provide clear error semantics for validation and domain errors without cluttering business logic with generic exceptions.

## Future Improvements

- Integrate a proper persistence layer (embedded H2, SQLite, or Postgres) with DAOs or JPA for transactional safety.
- Provide a RESTful API layer and a web or desktop GUI front‑end.
- Add automated tests and CI with GitHub Actions (compile, unit tests, static analysis).
- Introduce dependency injection and configuration management for easier testing and environment separation.
- Performance: investigate CSV parsing performance and introduce streaming/parsing improvements or bulk import optimizations.

## Troubleshooting

- If you encounter `InputMismatchException` when entering numbers, prefer entering values exactly as prompted, or edit `Main.java` to use safer parsing (e.g., `nextLine()` + `Integer.parseInt()`).
- If compilation fails, ensure `JAVA_HOME` is set to a JDK 17+ installation and that `javac` is on your PATH.

## Author

- Author: Your Name (replace with your full name)
- GitHub: https://github.com/yourusername (replace with your GitHub profile link)

---

File: [README.md](README.md)
