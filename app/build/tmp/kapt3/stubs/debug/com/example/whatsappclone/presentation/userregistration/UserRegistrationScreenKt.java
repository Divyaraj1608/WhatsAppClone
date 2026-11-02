package com.example.whatsappclone.presentation.userregistration;

import android.app.Activity;
import android.widget.Toast;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.ButtonDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.navigation.NavHostController;
import com.example.whatsappclone.presentation.navigation.Routes;
import com.example.whatsappclone.presentation.viewmodels.AuthState;
import com.example.whatsappclone.presentation.viewmodels.PhoneAuthViewModel;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\"\u0014\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"countries", "", "Lcom/example/whatsappclone/presentation/userregistration/Country;", "UserRegistrationScreen", "", "navController", "Landroidx/navigation/NavHostController;", "phoneAuthViewModel", "Lcom/example/whatsappclone/presentation/viewmodels/PhoneAuthViewModel;", "app_debug"})
public final class UserRegistrationScreenKt {
    @org.jetbrains.annotations.NotNull()
    private static final java.util.List<com.example.whatsappclone.presentation.userregistration.Country> countries = null;
    
    /**
     * One screen, two UIs:
     * - verificationId == null  -> enter phone number  (state: Idle / Loading)
     * - verificationId != null  -> enter the OTP        (state: CodeSent / Loading)
     */
    @androidx.compose.runtime.Composable()
    public static final void UserRegistrationScreen(@org.jetbrains.annotations.NotNull()
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull()
    com.example.whatsappclone.presentation.viewmodels.PhoneAuthViewModel phoneAuthViewModel) {
    }
}