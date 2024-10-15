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
fun RegisterScreen(viewModel: QuizViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val textFieldColor = TextFieldDefaults.colors(
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
        unfocusedLabelColor = Color.Gray,
        unfocusedIndicatorColor = Color.Gray,
        cursorColor = MaterialTheme.colorScheme.primary,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedContainerColor = MaterialTheme.colorScheme.background
    )

    if (viewModel.registerDialogue.value) {
        BasicAlertDialog(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(20.dp)
                )
                .border(
                    3.dp,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
                    RoundedCornerShape(20.dp)
                ),
            onDismissRequest = {
                viewModel.registerDialogue.value = false // Handle dismissing the dialog
            }, content = {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Register",
                        fontSize = 30.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(
                                1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                RoundedCornerShape(10.dp, 10.dp)
                            ),
                        colors = textFieldColor
                    )
                    TextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(
                                1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            ),
                        colors = textFieldColor
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
                        colors = textFieldColor
                    )
                    TextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .border(
                                1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            ),
                        colors = textFieldColor
                    )
                    Button(
                        modifier = Modifier
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.secondary,
                                RoundedCornerShape(20.dp)
                            )
                            .height(40.dp),
                        onClick = {  //TODO Handle the Register action
                            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && confirmPassword.isNotEmpty()) {
                                // TODO Perform Register action here
                                viewModel.registerDialogue.value =
                                    false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(text = "Register", fontSize = 14.sp)
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
                            text = "Already have account?",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.primary,
                        )
                        TextButton(
                            onClick = {
                                viewModel.registerDialogue.value = false
                                viewModel.loginDialogue.value = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                text = "Login",
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    QuizzappTheme {
        RegisterScreen(QuizViewModel())
    }
}