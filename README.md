# Android App Project

A basic Android application built with Kotlin and modern Android development practices.

## Project Structure

This is a standard Android project with the following structure:
- `app/` - Main application module
- `app/src/main/java/` - Kotlin source code
- `app/src/main/res/` - Android resources (layouts, values, etc.)
- `.github/workflows/` - GitHub Actions CI/CD workflows

## Features

- Modern Android architecture using Kotlin
- Material Design components
- Automated builds with GitHub Actions
- Release APK generation and deployment

## Building the Project

### Prerequisites
- JDK 17 or later
- Android SDK

### Local Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

## CI/CD

The project uses GitHub Actions for automated building and releasing:
- Builds are triggered on pushes to main branch
- Release APKs are automatically generated and uploaded to GitHub Releases
- Gradle caching is enabled for faster builds

## Configuration

For release builds, you'll need to set up the following environment variables:
- `KEYSTORE_PASSWORD` - Password for the keystore
- `KEY_ALIAS` - Alias for the signing key
- `KEY_PASSWORD` - Password for the signing key

You'll also need to add your `keystore.jks` file to the `app/` directory.

## Development

The app currently includes:
- A main activity with a simple "Hello World" layout
- Material Design theme configuration
- ProGuard rules for release optimization
- Proper resource organization