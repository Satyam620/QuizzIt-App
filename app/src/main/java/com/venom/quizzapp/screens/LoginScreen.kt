package com.venom.quizzapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venom.quizzapp.model.QuizViewModel
import com.venom.quizzapp.ui.theme.QuizzappTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: QuizViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    QuizzappTheme {
        if (viewModel.loginDialogue.value) {
            BasicAlertDialog(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .border(3.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.6f), RoundedCornerShape(20.dp)),
                onDismissRequest = {
                    viewModel.loginDialogue.value = false // Handle dismissing the dialog
                }, content = {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Login",
                            fontSize = 30.sp,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        TextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .border(
                                    1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                    RoundedCornerShape(10.dp, 10.dp)
                                ),
                            colors = TextFieldDefaults.colors(
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                                unfocusedLabelColor = Color.Gray,
                                unfocusedIndicatorColor = Color.Gray,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                focusedContainerColor = MaterialTheme.colorScheme.background
                            ),
                            shape = RoundedCornerShape(10.dp, 10.dp)
                        )
                        TextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .border(
                                    1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                ),
                            colors = TextFieldDefaults.colors(
                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                                unfocusedLabelColor = Color.Gray,
                                unfocusedIndicatorColor = Color.Gray,
                                cursorColor = MaterialTheme.colorScheme.primary,
                                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                focusedContainerColor = MaterialTheme.colorScheme.background
                            ),

                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            TextButton(
                                onClick = {
                                    //TODO Add Forgot password page.
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    text = "Forgot Password?",
                                    textAlign = TextAlign.Center,
                                )
                            }
                            Button(
                                modifier = Modifier
                                    .border(
                                        2.dp,
                                        MaterialTheme.colorScheme.secondary,
                                        RoundedCornerShape(20.dp)
                                    )
                                    .height(40.dp),
                                onClick = { // Handle the login action
                                    if (email.isNotEmpty() && password.isNotEmpty()) {
                                        // Perform login action here
                                        viewModel.loginDialogue.value =
                                            false // Dismiss the dialog after login
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Black,
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(text = "Login", fontSize = 20.sp)
                            }
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp)
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text =  "Don't have an account?",
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 14.sp
                            )
                            TextButton(
                                onClick = {
                                    viewModel.loginDialogue.value = false
                                    viewModel.registerDialogue.value = true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent,
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    text = "Register",
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }

                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(QuizViewModel())
}