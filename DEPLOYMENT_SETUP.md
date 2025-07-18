# GitHub Actions Deployment Setup

This repository now includes a comprehensive GitHub Actions CI/CD pipeline for Android app deployment. This guide will help you configure and use it.

## 🚀 Features

- **Automated Testing**: Runs unit tests on every push and pull request
- **Multi-environment Builds**: Debug builds for PRs, release builds for main branch
- **Automated Releases**: Creates GitHub releases with APK and AAB files
- **Google Play Store Deployment**: Automatic deployment to Play Store (optional)
- **Gradle Caching**: Faster builds with dependency caching
- **Secure Signing**: Handles app signing with encrypted secrets

## 📁 Workflow Structure

The workflow consists of 4 main jobs:

1. **Test**: Runs unit tests and uploads results
2. **Build Debug**: Creates debug APK for pull requests
3. **Build Release**: Creates signed release APK and AAB for main branch
4. **Deploy Play Store**: Deploys to Google Play Store (on releases)

## ⚙️ Setup Instructions

### 1. Required Secrets

Add these secrets to your GitHub repository (Settings → Secrets → Actions):

#### For App Signing:
- `KEYSTORE_BASE64`: Your keystore file encoded in base64
- `KEYSTORE_PASSWORD`: Password for your keystore
- `KEY_ALIAS`: Alias of your signing key
- `KEY_PASSWORD`: Password for your signing key

#### For Google Play Store Deployment (Optional):
- `GOOGLE_PLAY_SERVICE_ACCOUNT_JSON`: Service account JSON for Play Console API

### 2. Generate and Add Keystore

If you don't have a keystore yet:

```bash
# Generate a new keystore
keytool -genkey -v -keystore my-app-keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-app-key

# Convert keystore to base64
base64 -i my-app-keystore.jks | tr -d '\n'
```

Add the base64 output to the `KEYSTORE_BASE64` secret.

### 3. Update Configuration

1. **Package Name**: Update the package name in `.github/workflows/android-deploy.yml` line 182:
   ```yaml
   packageName: com.yourpackage.name # Replace with your actual package name
   ```

2. **Gradle Wrapper**: Ensure you have `gradlew` in your repository root:
   ```bash
   # If you don't have gradlew, generate it:
   gradle wrapper
   ```

3. **Root build.gradle**: Make sure you have a root `build.gradle` file. If not, create one:
   ```gradle
   // Top-level build file where you can add configuration options common to all sub-projects/modules.
   buildscript {
       dependencies {
           classpath 'com.android.tools.build:gradle:8.0.0'
       }
   }
   
   allprojects {
       repositories {
           google()
           mavenCentral()
       }
   }
   ```

4. **settings.gradle**: Create if it doesn't exist:
   ```gradle
   include ':app'
   ```

### 4. Google Play Store Setup (Optional)

To enable automatic Play Store deployment:

1. **Enable Google Play Console API** in Google Cloud Console
2. **Create a Service Account** with Play Console API access
3. **Download the service account JSON** and add it to `GOOGLE_PLAY_SERVICE_ACCOUNT_JSON` secret
4. **Grant permissions** to the service account in Play Console

## 🔄 How It Works

### Triggers

- **Push to main/develop**: Runs tests and builds
- **Pull Request to main**: Runs tests and creates debug build
- **Release published**: Runs full pipeline including Play Store deployment
- **Manual trigger**: Available in Actions tab

### Build Types

- **Debug builds**: For pull requests and development
- **Release builds**: For main branch and releases
- **Signed builds**: When keystore secrets are configured
- **Unsigned builds**: Fallback when no keystore is available

### Release Process

1. **Automatic releases**: Push to main with `[release]` in commit message
2. **Manual releases**: Create a release through GitHub interface
3. **Release artifacts**: APK and AAB files are attached to releases

## 📦 Artifacts

Each build produces artifacts that can be downloaded:

- **test-results**: Unit test reports
- **debug-apk**: Debug APK for testing
- **release-apk**: Signed release APK
- **release-aab**: Signed release AAB (for Play Store)

## 🐛 Troubleshooting

### Common Issues

1. **Gradlew permission denied**:
   ```bash
   chmod +x gradlew
   git add gradlew
   git commit -m "Fix gradlew permissions"
   ```

2. **Missing keystore**:
   - Workflow will build unsigned APK
   - Add keystore secrets for signed builds

3. **Build failures**:
   - Check Java version compatibility (workflow uses JDK 17)
   - Ensure all dependencies are properly declared

4. **Play Store deployment fails**:
   - Verify service account permissions
   - Check package name matches registered app
   - Ensure AAB is properly signed

### Debug Commands

```bash
# Test local build
./gradlew assembleDebug

# Test release build (requires keystore)
./gradlew assembleRelease

# Run unit tests
./gradlew testDebugUnitTest

# Clean build
./gradlew clean
```

## 🔧 Customization

### Modify Build Triggers

Edit the `on` section in `.github/workflows/android-deploy.yml`:

```yaml
on:
  push:
    branches: [ main, develop, feature/* ]  # Add more branches
  schedule:
    - cron: '0 2 * * 1'  # Weekly builds
```

### Add More Build Variants

Add steps for different build variants:

```yaml
- name: Build Beta Release
  run: ./gradlew assembleBetaRelease
```

### Custom Deployment Targets

Add steps for other deployment platforms:

```yaml
- name: Deploy to Firebase App Distribution
  uses: wzieba/Firebase-Distribution-Github-Action@v1
  with:
    appId: ${{ secrets.FIREBASE_APP_ID }}
    token: ${{ secrets.FIREBASE_TOKEN }}
    groups: testers
    file: app/build/outputs/apk/release/app-release.apk
```

## 📚 Additional Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android Gradle Plugin](https://developer.android.com/studio/build)
- [Google Play Console API](https://developers.google.com/android-publisher)
- [App Signing Best Practices](https://developer.android.com/studio/publish/app-signing)

## 🆘 Support

If you encounter issues:

1. Check the Actions logs in GitHub
2. Verify all secrets are properly set
3. Ensure your Android project structure is correct
4. Test builds locally first

---

**Note**: Remember to update the package name and other project-specific configurations before using this workflow in production.