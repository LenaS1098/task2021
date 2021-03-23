package de.task.screens


import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.Color
import com.google.accompanist.coil.CoilImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException




enum class LoginState {
    LOGGED, LOGGING, NONE
}

@Composable
fun settingScreen(mGoogleSignInClient: GoogleSignInClient, context: Context) {

    //work around -> rememberSaveable speichert nicht bei neuaufrufen des Composable
    var currentState: LoginState = LoginState.NONE
    if(GoogleSignIn.getLastSignedInAccount(context) != null){
        currentState = LoginState.LOGGED
    }


    //muss für jede einstellug einzeln sein; am besten extra klasse (when? Viele Variablen?)
    val checkedState = remember { mutableStateOf(false) }



    var loginState by rememberSaveable{ mutableStateOf(currentState)}

    var currentAccount: GoogleSignInAccount? = null

    val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    currentAccount = task.getResult(ApiException::class.java)
                    loginState = LoginState.LOGGED
                } catch (e: ApiException) {
                    // The ApiException status code indicates the detailed failure reason.
                    // Please refer to the GoogleSignInStatusCodes class reference for more information.
                    Log.w("signInResult", "signInResult:failed code=" + e.statusCode)

                }
            }
        }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()

    ) {
        Text("Settings", fontSize = 20.sp)

        when (loginState) {
            LoginState.NONE -> Button(
                onClick = { startForResult.launch(mGoogleSignInClient.signInIntent) },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .background(color = Color.Magenta)
            ) {
                Text(text = "Einloggen")
            }
            LoginState.LOGGING -> Text(text = "Logging", Modifier.padding(top = 8.dp))
            LoginState.LOGGED -> {
                currentAccount = GoogleSignIn.getLastSignedInAccount(context)
                if (currentAccount!= null) {
                    
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)) {
                        Text(
                            text = "User : " + currentAccount!!.displayName,
                            Modifier.padding(top = 8.dp)
                        )


                        CoilImage(currentAccount!!.photoUrl.toString(),"eins beschreibung", loading = {
                            Box(Modifier.matchParentSize()) {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }
                        })
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Button(
                        onClick = {
                            mGoogleSignInClient.signOut().addOnCompleteListener {
                                loginState = LoginState.NONE
                            }
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .background(color = Color.Magenta)

                    ) {
                        Text(text = "User Ausloggen")
                    }

                    //Funktioniert noch net so ganz
                    Button(
                        onClick = {
                            mGoogleSignInClient.revokeAccess().addOnCompleteListener {
                                loginState = LoginState.NONE
                            }
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .background(color = Color.Magenta)

                    ) {
                        Text(text = "User entfernen")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    text = "Einstellung mit einer langen Beschreibung",
                    fontStyle = FontStyle.Italic
                )

            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {

                Checkbox(
                    checked = checkedState.value,
                    modifier = Modifier.padding(start = 30.dp),
                    onCheckedChange = { checkedState.value = it })
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(text = "Kurze Einstellung", fontStyle = FontStyle.Italic)

            }
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {

                Checkbox(
                    checked = checkedState.value,
                    modifier = Modifier.padding(start = 30.dp),
                    onCheckedChange = { checkedState.value = it })
            }
        }
    }
}

