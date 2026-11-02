package com.example.whatsappclone.presentation.userregistration

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.presentation.common.whatsAppTextFieldColors
import com.example.whatsappclone.presentation.navigation.Routes
import com.example.whatsappclone.presentation.viewmodels.AuthState
import com.example.whatsappclone.presentation.viewmodels.PhoneAuthViewModel
import com.example.whatsappclone.ui.theme.DarkGreen
import com.example.whatsappclone.ui.theme.LightGreen

private data class Country(val name: String, val code: String)

private val countries = listOf(
    Country("India", "+91"),
    Country("USA", "+1"),
    Country("Canada", "+1"),
    Country("China", "+86"),
    Country("Japan", "+81"),
    Country("United Kingdom", "+44")
)

/**
 * One screen, two UIs:
 *  - verificationId == null  -> enter phone number  (state: Idle / Loading)
 *  - verificationId != null  -> enter the OTP        (state: CodeSent / Loading)
 */
@Composable
fun UserRegistrationScreen(
    navController: NavHostController,
    phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val authState by phoneAuthViewModel.authState.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf("India") }
    var countryCode by remember { mutableStateOf("+91") }
    var phoneNumber by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var verificationId by remember { mutableStateOf<String?>(null) }

    // React to state changes: save the id, show errors, or navigate on success
    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.CodeSent -> verificationId = state.verificationId
            is AuthState.Success -> {
                phoneAuthViewModel.resetAuthState()
                val destination: Routes =
                    if (state.user.name.isNotBlank()) Routes.HomeScreen else Routes.UserProfileSetScreen
                navController.navigate(destination) {
                    popUpTo(Routes.UserRegistrationScreen) { inclusive = true }
                }
            }

            is AuthState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                phoneAuthViewModel.resetAuthState()
            }

            else -> Unit
        }
    }

    val isLoading = authState is AuthState.Loading

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (verificationId == null) {
            // ---------- Phone number UI ----------
            Text(
                "Enter your phone number",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGreen
            )
            Spacer(Modifier.height(24.dp))
            Row {
                Text("WhatsApp will need to verify your phone number. ")
            }
            Text("What's my number?", color = DarkGreen)
            Spacer(Modifier.height(16.dp))

            // Country drop-down
            Box {
                TextButton(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.width(230.dp), contentAlignment = Alignment.Center) {
                        Text(selectedCountry, fontSize = 16.sp, color = Color.Black)
                    }
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = LightGreen)
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    countries.forEach { country ->
                        DropdownMenuItem(
                            text = { Text(country.name) },
                            onClick = {
                                selectedCountry = country.name
                                countryCode = country.code
                                expanded = false
                            }
                        )
                    }
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 60.dp),
                thickness = 2.dp,
                color = LightGreen
            )
            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = countryCode,
                    onValueChange = { countryCode = it },
                    singleLine = true,
                    textStyle = androidx.compose.ui.text.TextStyle(fontSize = 18.sp),
                    colors = whatsAppTextFieldColors(),
                    modifier = Modifier.width(80.dp)
                )
                Spacer(Modifier.width(6.dp))
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    singleLine = true,
                    placeholder = { Text("Phone number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    colors = whatsAppTextFieldColors(),
                    modifier = Modifier.width(200.dp)
                )
            }
            Spacer(Modifier.height(16.dp))
            Text("Carrier charges may apply", fontSize = 12.sp, color = Color.Gray)
            Spacer(Modifier.height(26.dp))

            Button(
                enabled = !isLoading,
                onClick = {
                    if (phoneNumber.isNotBlank()) {
                        val fullPhoneNumber = "$countryCode$phoneNumber"
                        phoneAuthViewModel.sendVerificationCode(
                            fullPhoneNumber,
                            activity
                        )
                    } else {
                        Toast.makeText(
                            context,
                            "Please enter a valid phone number",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(43.dp)
            ) {
                Text("Next", fontSize = 16.sp)
            }
        } else {
            // ---------- OTP UI ----------
            Spacer(Modifier.height(40.dp))
            Text("Enter OTP", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            Spacer(Modifier.height(8.dp))
            Text(
                "Enter the 6-digit code sent to $countryCode$phoneNumber",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = otp,
                onValueChange = { otp = it },
                singleLine = true,
                placeholder = { Text("OTP") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = whatsAppTextFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(32.dp))
            Button(
                enabled = !isLoading,
                onClick = {
                    if (otp.isNotBlank()) {
                        phoneAuthViewModel.verifyCode( otp, context)
                    } else {
                        Toast.makeText(context, "Please enter a valid OTP", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(43.dp)
            ) {
                Text("Verify OTP", fontSize = 16.sp)
            }
        }

        if (isLoading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator(color = LightGreen)
        }
    }
}
