# MediTrack — Design Decisions

This document records the principal architectural and design decisions made during the development of the MediTrack prototype. The explanations are written in an academic, assignment‑ready style, and document the rationale behind key choices to aid maintainability and future evolution.

## 1. Why an Object‑Oriented Structure

- Encapsulation and modelling: The domain of a medical appointment system maps naturally to objects (Doctor, Patient, Appointment, Bill). Encapsulation localises state and behaviour (e.g., `Appointment.book`, `Patient.clone`) and reduces accidental coupling.
- Reuse and extension: OOP supports inheritance and polymorphism where useful (e.g., `Person` as a base for `Doctor` and `Patient`), enabling shared behaviour and smaller, focused classes.
- Readability and maintainability: Grouping related data and methods in coherent classes improves the code‑reading mental model for new contributors and eases debugging and refactoring.

The choice of OOP is pragmatic: it reflects the domain model and simplifies representing real‑world entities with identity, attributes, and behaviour.

## 2. Why a Singleton for `IdGenerator`

- Global uniqueness: The `IdGenerator` provides system‑wide unique identifiers for entities. A single shared instance avoids ID collisions that could arise from multiple generators with independent state.
- Thread safety and central control: Implemented with a thread‑safe counter (e.g., `AtomicInteger`) and the initialization‑on‑demand holder idiom, the singleton centralises ID generation while ensuring safe concurrent access.
- Bootstrapping persisted data: A single `IdGenerator` allows the system to be bootstrapped from persisted records (CSV import) and to `ensureAtLeast(max)` the internal counter so new IDs do not conflict with existing ones.

Trade-offs: Singletons introduce a global dependency that can complicate testing. To mitigate this, the implementation exposes minimal state and could be replaced by an injectable strategy for unit tests or future DI integration.

## 3. Why a Generic `DataStore<T>`

- Generic, reusable in‑memory repository: `DataStore<T>` abstracts storage operations (add, remove, find, list) without tying business logic to a concrete collection type. It reduces duplication across services (`DoctorService`, `PatientService`, `AppointmentService`).
- Type safety: Generics ensure compile‑time type correctness and eliminate unchecked casts when retrieving stored entities.
- Testability and future replacement: Having a small repository abstraction makes it straightforward to replace the in‑memory `DataStore` with a persistence implementation (e.g., DAO backed by a database) with a minimal change surface.

## 4. Why `BillSummary` Is Immutable

- Value semantics: `BillSummary` represents a computed value (a snapshot of billing information). Immutable value objects prevent accidental mutation and simplify reasoning about state after the bill is produced.
- Thread safety: Immutability provides inherent thread safety; no synchronization is required when `BillSummary` instances are shared across threads.
- Safe publishing: Returning an immutable `BillSummary` from `AppointmentService.generateBill()` avoids leaking internal mutable state and prevents callers from altering domain objects inadvertently.

Design trade-offs: While immutable objects can require copying for modifications, `BillSummary` is intended as a read‑only report object; therefore immutability is a net positive.

## 5. Why Custom Exceptions Were Introduced

- Domain‑specific clarity: Custom exceptions such as `InvalidDataException` and `AppointmentNotFoundException` encode domain intents and make error handling more expressive than generic runtime exceptions.
- Error handling separation: Services can throw domain exceptions and the CLI (or higher layers) can map them to user‑friendly messages, preserving a clean separation between logic and presentation.
- Diagnostic value: Custom exception types enable precise catch blocks and can carry structured contextual information for logging and telemetry.

Design guidance: Custom exceptions are unchecked (subclass `RuntimeException`) when they represent programming or validation errors that are unrecoverable at the caller, and checked when callers must handle recoverable conditions; the project uses unchecked domain exceptions for concision in the prototype.

## 6. Why CSV Persistence Instead of a Database (Prototype Choice)

- Simplicity and minimal dependencies: CSV files provide an easily readable, inspectable representation of persisted data and avoid introducing external DB dependencies for a prototype.
- Developer ergonomics: CSV files require no installation or configuration and facilitate rapid iteration during development and demos.
- Portability and transparency: CSV is human‑readable and can be edited by testers or used as fixtures for demonstration scenarios.

Limitations and rationale for future migration: CSV persistence lacks transactional guarantees, concurrency control, and efficient querying. For production readiness, migrating to a relational or embedded database (e.g., SQLite, H2) or an external DBMS (Postgres) would be recommended to address consistency, scaling, and concurrent access.

## 7. Application of SOLID Principles

- Single Responsibility Principle (SRP): Classes are focused on a single responsibility — `Doctor`/`Patient` model entities, `DataStore` handles storage concerns, `CSVUtil` manages import/export, and services orchestrate domain operations.
- Open/Closed Principle (OCP): Components are designed to be extended without modification. For example, `DataStore<T>` can be substituted by a more advanced repository implementation without changing service code.
- Liskov Substitution Principle (LSP): The `Person` base class provides common behaviour for `Doctor` and `Patient` with compatible constructors and getters; subclasses maintain expected invariants and substitutability.
- Interface Segregation Principle (ISP): Interfaces (where used) remain small and focused (e.g., `Searchable`), preventing classes from depending on methods they do not use.
- Dependency Inversion Principle (DIP): High‑level services depend on abstractions (`DataStore` contract) rather than concrete implementations. The current lightweight abstraction enables easier inversion later by introducing DI frameworks.

## 8. Future Improvements

The following improvements are recommended when moving beyond the prototype phase:

1. Persistence Layer Upgrade
   - Replace CSV persistence with a transactional store (embedded H2, SQLite, or a remote RDBMS) and implement DAOs or a repository pattern with connection pooling.

2. Dependency Injection
   - Introduce a lightweight DI container (e.g., Dagger, Spring) to manage singletons, enable easier testing, and invert dependencies for services.

3. Robust Input Handling
   - Replace direct `Scanner.nextInt()` usage with safer parsing and input validation layers to improve CLI robustness and internationalization.

4. Concurrency and Scalability
   - If MediTrack serves concurrent users, replace in‑memory `DataStore` with a concurrent repository and consider REST APIs or a microservice architecture for horizontal scaling.

5. Automated Tests and CI
   - Add comprehensive unit tests for `Validator`, `DataStore`, services, and serialization, and integrate CI (GitHub Actions) to run builds and tests on each push.

6. Monitoring and Observability
   - Integrate JFR/metrics and structured logging; add health endpoints and basic telemetry for production observability.

7. Security and Input Sanitization
   - Secure persisted data storage and sanitize user input; apply role‑based access controls if multi‑user deployment is required.

8. API and UI
   - Provide an HTTP-based API and a lightweight web UI or desktop frontend to improve usability beyond the CLI.

---

File: [docs/Design_Decisions.md](docs/Design_Decisions.md)
