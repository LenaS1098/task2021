package de.task.screens



import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.activity.compose.registerForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import com.google.accompanist.coil.CoilImage
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import de.task.R


enum class LoginState {
    LOGGED, LOGGING, NONE
}


@Composable
fun DefaultProfilIcon(){
    Image(
        contentDescription = "profileIcon",
        modifier = Modifier
            .width(70.dp)
            .height(70.dp)
            .border(
                border = BorderStroke(1.dp, color = MaterialTheme.colors.onPrimary),
                shape = CircleShape
            )
            .clip(shape = CircleShape)
            .padding(8.dp),
        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onPrimary),
        painter = painterResource(id = R.drawable.ic_profile)
    )
}


@Composable
fun LoginHeader(mGoogleSignInClient: GoogleSignInClient, context: Context){

    //login

    var userName = "Logge dich bitte ein"
    var userEmail = "Hier könnte deine Werbung stehen"

    var currentState: LoginState = LoginState.NONE
    if(GoogleSignIn.getLastSignedInAccount(context) != null){
        currentState = LoginState.LOGGED
    }

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
    Surface(color = MaterialTheme.colors.primary) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(loginState){
                LoginState.NONE -> {
                    userName = "Logge dich bitte ein"
                    userEmail = "Hier könnte Ihre Werbung stehen"
                }
                LoginState.LOGGED -> {
                    currentAccount = GoogleSignIn.getLastSignedInAccount(context)
                    userName = currentAccount!!.displayName!!
                    userEmail = currentAccount!!.email!!

                }
                else ->{
                    Text("Ein Fehler ist aufgetreten")
                }
            }
            if(loginState == LoginState.LOGGED){
                if(currentAccount!!.photoUrl != null) {
                    CoilImage(
                        currentAccount!!.photoUrl.toString(),
                        "user Profile Picture",
                        loading = {
                            Box(
                                Modifier
                                    .width(70.dp)
                                    .height(70.dp)
                                    .padding(8.dp)
                            ) {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }
                        }, modifier = Modifier.width(70.dp)
                            .height(70.dp)
                            .padding(8.dp))
                }else{
                    DefaultProfilIcon()
                }
            }else {
                DefaultProfilIcon()
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text= userName, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onPrimary)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text= userEmail, style = MaterialTheme.typography.caption, color = MaterialTheme.colors.onPrimary)
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                if(loginState == LoginState.LOGGED){
                    Icon(painter = painterResource(id = R.drawable.ic_logout),"icon", Modifier.clickable {
                        mGoogleSignInClient.signOut().addOnCompleteListener {
                            loginState = LoginState.NONE
                        }
                    })
                }else{
                    Icon(painter = painterResource(id = R.drawable.ic_login),"icon", Modifier.clickable {
                        startForResult.launch(mGoogleSignInClient.signInIntent)
                    })
                }

            }


        }

    }
}
/*

@Composable
fun loginScreen(mGoogleSignInClient: GoogleSignInClient, context: Context) {

    //work around -> rememberSaveable speichert nicht bei neuaufrufen des Composable
    var currentState: LoginState = LoginState.NONE
    if(GoogleSignIn.getLastSignedInAccount(context) != null){
        currentState = LoginState.LOGGED
    }



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

    }
}

*/
