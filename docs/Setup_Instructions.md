# MediTrack — Setup Instructions

These instructions explain how to set up, build, and run the MediTrack Java application on a development machine.

## 1. Required Software

- Java Development Kit (JDK) 17 or later — download from AdoptOpenJDK / Oracle / Temurin.
- Git — to clone the repository.
- An IDE (recommended): Visual Studio Code or IntelliJ IDEA Community/Ultimate.
- Optional: Java Mission Control (JMC) if you plan to inspect Flight Recorder (JFR) recordings.

Verify Java installation:

```powershell
java -version
javac -version
```

Expected output shows Java 17+.

## 2. Clone the repository

Open a terminal and run:

```powershell
git clone <repository-url>
cd MediTrack
```

Replace `<repository-url>` with the remote URL (HTTPS or SSH).

## 3. How to run the project (overview)

There are two common approaches:

- Use an IDE (VS Code / IntelliJ): import the project as a Java project, ensure the project SDK is set to JDK 17+, then run `Main.java` from the IDE.
- Use the command line: compile sources to an output directory and run the `Main` class.

### Build and run from command line

From the repository root run (Windows PowerShell examples):

```powershell
# compile all Java files into the 'out' directory
Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { '"' + $_.FullName + '"' } > files.txt
javac -d out @files.txt

# run the application (Main class)
java -cp out com.airtribe.meditrack.Main
```

Note: the repository already uses a simple `out/` compile target in development; adjust commands if you prefer a build tool (Maven/Gradle).

## 4. How to generate a Personal Access Token (PAT) (if needed)

If you push to a remote (e.g., GitHub) and your account requires a PAT for HTTPS authentication, generate it as follows (GitHub example):

1. Sign in to GitHub and go to **Settings > Developer settings > Personal access tokens**.
2. Click **Generate new token**.
3. Select scopes required (e.g., `repo` for repository access) and give it a descriptive name.
4. Click **Generate token**, then copy and securely store the token — you will not be able to see it again.

Use the token as the password when `git` prompts for credentials, or configure it in your credential manager.

## 5. How to run `Main.java` (IDE and CLI)

- From IntelliJ: open the `src` folder as a project, set project SDK to Java 17+, open `src/com/airtribe/meditrack/Main.java`, right-click the `main` method and run.
- From VS Code: install the Java Extension Pack, open the folder, set `java.home` to JDK 17 in settings, then use the Run CodeLens or debug configuration to start `Main`.
- From CLI: see the command above — compile with `javac` into `out/` then run `java -cp out com.airtribe.meditrack.Main`.

## 6. Sample console output

Below is an illustrative snippet of the interactive CLI that appears when running `Main` (user input lines prefixed with `>`):

```
Welcome to MediTrack
1. Add Doctor
2. Add Patient
3. Book Appointment
4. Cancel Appointment
5. Display Doctors
6. Display Patients
7. Display Appointments
8. Generate Bill
9. Exit
Enter your choice: > 1
Assigned Doctor ID: 1
Enter Name: > Dr. Swati
Enter Age: > 38
Enter Specialization (e.g., CARDIOLOGY): > CARDIOLOGY
Enter Consultation Fee: > 150.0
Doctor added successfully!

Enter your choice: > 2
Assigned Patient ID: 1
Enter Name: > John Doe
Enter Age: > 29
Enter Disease: > Flu
Patient added successfully!

Enter your choice: > 3
Enter Doctor ID: > 1
Enter Patient ID: > 1
Enter Appointment Date (yyyy-mm-dd): > 2026-02-14
Appointment booked with ID: 1

Enter your choice: > 8
Enter Appointment ID: > 1
Bill for appointment 1:
Patient: John Doe
Doctor: Dr. Swati
Total (incl. tax): 165.00

Enter your choice: > 9
Saving data and exiting. Goodbye.
```

Note: Actual text, field prompts and flow may vary slightly based on local edits and the current branch state.

## 7. Screenshots (placeholders)

- Screenshot: `screenshots/main_menu.png` (placeholder)
- Screenshot: `screenshots/add_doctor.png` (placeholder)
- Screenshot: `screenshots/generate_bill.png` (placeholder)

Replace these placeholders with actual screenshots produced on your machine.

## 8. Troubleshooting

- Problem: `java -version` shows older Java (e.g., Java 8).
	- Solution: Install JDK 17+ and set `JAVA_HOME` and IDE project SDK to the new JDK.

- Problem: `javac` cannot find sources or classes; compilation fails.
	- Solution: Ensure you run `javac` from the repository root and provide the correct `src` paths; use the provided compile commands above or use your IDE build tools.

- Problem: `Exception in thread "main" java.util.InputMismatchException` when entering numbers.
	- Cause: interactive `Scanner` calls such as `nextInt()` can throw when the input stream contains unexpected tokens or newlines.
	- Solution: Enter inputs exactly as prompted, or edit `Main.java` to use `nextLine()` and parse integers with `Integer.parseInt()` to avoid scanner token mismatch.

- Problem: CSV load/save file permission errors.
	- Solution: Verify the `doctors.csv` and `patients.csv` file paths (see `Constants`), create the files or ensure the application has write permission to the directory.

- Problem: OutOfMemoryError during large imports.
	- Solution: Increase heap size with `-Xmx` (development flag examples below) or use streaming parsers and persist intermediate data to disk to reduce in-memory footprint.

### Quick development JVM flags

```powershell
java -Xms512m -Xmx2g -XX:+UseG1GC -XX:+PrintGCDetails -cp out com.airtribe.meditrack.Main
```

Use these flags while debugging memory or GC behaviour in development.

---

File: [docs/Setup_Instructions.md](docs/Setup_Instructions.md)

