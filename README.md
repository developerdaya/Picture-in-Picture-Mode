# Picture-in-Picture-Mode
**Picture-in-Picture (PiP) Mode** is a feature in Android that allows users to continue watching a video or using an app in a small, resizable window while interacting with other apps. This feature is particularly useful for video playback applications, video conferencing apps, and similar use cases.

### Enabling Picture-in-Picture Mode in Android

Here’s how to implement PiP mode in an Android application using Kotlin:

### 1. Update Your Manifest

First, you need to declare that your activity supports Picture-in-Picture mode by adding the `android:supportsPictureInPicture` attribute to your activity in the `AndroidManifest.xml` file:

```xml
<activity
    android:name=".YourActivity"
    android:supportsPictureInPicture="true">
</activity>
```

### 2. Entering PiP Mode

To enter Picture-in-Picture mode, call the `enterPictureInPictureMode()` method in your activity when you want to enter PiP. You can usually do this when the user clicks a button or when the video playback starts.

Here's an example of how to do this:

```kotlin
import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Bundle
import android.util.Rational
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class YourActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your)

        // Example button to enter PiP
        findViewById<View>(R.id.enter_pip_button).setOnClickListener {
            enterPiPMode()
        }
    }

    private fun enterPiPMode() {
        val aspectRatio = Rational(16, 9) // Set the desired aspect ratio
        val pipParams = PictureInPictureParams.Builder()
            .setAspectRatio(aspectRatio)
            .build()
        enterPictureInPictureMode(pipParams)
    }
}
```

### 3. Handling PiP Mode

When your activity enters PiP mode, you may want to handle specific UI changes or interactions. Override `onUserLeaveHint()` to respond when the user leaves the activity to enter PiP mode:

```kotlin
override fun onUserLeaveHint() {
    super.onUserLeaveHint()
    // Handle the UI changes here if needed
}
```

### 4. Exiting PiP Mode

To exit PiP mode, the user can simply tap on the PiP window, or you can programmatically exit PiP mode by calling `finish()` when the user stops video playback or navigates away from the app:

```kotlin
override fun onStop() {
    super.onStop()
    if (isInPictureInPictureMode) {
        // Exit PiP mode (optional, as it will exit automatically on stop)
        finish()
    }
}
```

### 5. Responding to Configuration Changes

You might want to handle configuration changes (like screen rotations) while in PiP mode. Override the `onConfigurationChanged()` method:

```kotlin
override fun onConfigurationChanged(newConfig: Configuration) {
    super.onConfigurationChanged(newConfig)
    // Handle configuration changes if needed
}
```

### 6. Customize the PiP Menu Actions (Optional)

If you want to add actions to the PiP menu (like play, pause, or close), you can create actions using `RemoteAction`:

```kotlin
import android.app.RemoteAction
import android.content.Intent
import android.graphics.drawable.Icon

private fun createRemoteAction(): RemoteAction {
    val intent = Intent(this, YourActivity::class.java).apply {
        action = Intent.ACTION_VIEW
    }
    val icon = Icon.createWithResource(this, R.drawable.ic_action) // Your action icon
    return RemoteAction(icon, "Action Title", "Description", PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
}
```

Then, include this action in the `PictureInPictureParams.Builder`:

```kotlin
val pipParams = PictureInPictureParams.Builder()
    .setAspectRatio(aspectRatio)
    .addAction(createRemoteAction())
    .build()
```
