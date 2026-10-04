# EXE Runner

Android project designed for phone-only development and GitHub Actions builds.

Current scope:
- Select an `.exe` through Android's document picker.
- Persist read access to the selected file.
- Show selected filename.
- Provide a RUN entry point.
- Build a debug APK through GitHub Actions.

Important:
This project does NOT bundle Wine, Box64, or a Windows runtime. Therefore it is not yet a real Windows/EXE compatibility layer. The RUN button is intentionally a runtime hook so the compatibility engine can be added later.

GitHub Actions:
1. Push this repository.
2. Open Actions.
3. Run "Build APK" manually, or push to main.
4. Download the `EXE-Runner-debug` artifact.
