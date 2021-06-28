package sahoo.projects.fin.util

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

class SecurityUtil {
    companion object {
        private lateinit var executor: Executor
        private lateinit var biometricPrompt: BiometricPrompt
        private lateinit var promptInfo: BiometricPrompt.PromptInfo

        fun authenticate(context: Context, activity: FragmentActivity, onSuccess: () -> Unit) {
            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for Fin!")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build()

            executor = ContextCompat.getMainExecutor(context)
            biometricPrompt = BiometricPrompt(activity, executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int,
                                                       errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        activity.finish()
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        onSuccess()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(activity, "Authentication failed",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                })

            biometricPrompt.authenticate(promptInfo)
        }
    }
}