# Android Project with GitHub Actions CI/CD

This Android project includes a comprehensive GitHub Actions CI/CD pipeline for automated building, testing, and deployment.

## 🚀 Features

- **Automated CI/CD**: Complete GitHub Actions workflow for Android apps
- **Multi-environment builds**: Debug builds for PRs, release builds for main branch
- **Automated testing**: Unit tests run on every push and pull request
- **Release management**: Automatic GitHub releases with APK and AAB files
- **Google Play Store deployment**: Optional automatic deployment to Play Store
- **Secure app signing**: Handles app signing with encrypted secrets

## 📁 Project Structure

```
.
├── .github/
│   └── workflows/
│       └── android-deploy.yml    # Main CI/CD workflow
├── app/
│   ├── build.gradle              # App-level build configuration
│   └── proguard-rules.pro        # ProGuard rules
├── gradle/
│   └── wrapper/                  # Gradle wrapper files
├── build.gradle                  # Root build configuration
├── settings.gradle               # Project settings
├── gradle.properties             # Gradle properties
├── gradlew                       # Gradle wrapper script (Unix)
└── DEPLOYMENT_SETUP.md           # Detailed setup instructions
```

## ⚙️ Quick Setup

1. **Configure secrets** in your GitHub repository (Settings → Secrets → Actions):
   - `KEYSTORE_BASE64`: Your keystore file encoded in base64
   - `KEYSTORE_PASSWORD`: Password for your keystore
   - `KEY_ALIAS`: Alias of your signing key
   - `KEY_PASSWORD`: Password for your signing key

2. **Update package name** in:
   - `app/build.gradle` (applicationId and namespace)
   - `.github/workflows/android-deploy.yml` (packageName for Play Store deployment)
   - `app/proguard-rules.pro` (keep rules)

3. **Push to main branch** or create a pull request to trigger the workflow

## 🔄 Workflow Triggers

- **Push to main/develop**: Runs tests and builds release APK/AAB
- **Pull Request to main**: Runs tests and creates debug build
- **Release published**: Runs full pipeline including Play Store deployment
- **Manual trigger**: Available in GitHub Actions tab

## 📦 Build Artifacts

Each workflow run produces downloadable artifacts:

- **test-results**: Unit test reports
- **debug-apk**: Debug APK for testing (PR builds)
- **release-apk**: Signed release APK (main branch)
- **release-aab**: Signed release AAB for Play Store (main branch)

## 🚀 Deployment

### GitHub Releases
- Automatic releases on main branch with `[release]` in commit message
- Manual releases through GitHub interface
- APK and AAB files automatically attached

### Google Play Store (Optional)
- Configure `GOOGLE_PLAY_SERVICE_ACCOUNT_JSON` secret
- Automatic deployment on published releases
- Deploys to production track by default

## 📚 Documentation

For detailed setup instructions, troubleshooting, and customization options, see [DEPLOYMENT_SETUP.md](DEPLOYMENT_SETUP.md).

## 🛠️ Local Development

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK (requires keystore configuration)
./gradlew assembleRelease

# Run unit tests
./gradlew testDebugUnitTest

# Clean project
./gradlew clean
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Submit a pull request
5. The CI pipeline will automatically build and test your changes

---

**Note**: Make sure to update the package name and other project-specific configurations before using this in production.