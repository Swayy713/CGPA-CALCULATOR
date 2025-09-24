package com.example.myproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myproject.ui.theme.MyProjectTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MaterialTheme(colorScheme = lightColorScheme(background = Color(0xFF5E8D88)
                )){
                    Surface (modifier= Modifier.fillMaxSize(),
                        color= MaterialTheme.colorScheme.background){
                        ProfileApp() }
                }

            }
        }
    }
}

@Composable
fun ProfileApp() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loggedIn by remember { mutableStateOf(false) }

    if (!loggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Login Screen", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        loggedIn = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    } else {
        ProfileScreen(username) {
            loggedIn = false
            username = ""
            password = ""
        }
    }
}
@Composable
fun ProfileScreen(username: String, onLogOut: () -> Unit) {
    var courseUnit by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var courses by remember { mutableStateOf(mutableListOf<Pair<Int, Int>>()) }
    var cgpa by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Welcome, $username 👋",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = courseUnit,
            onValueChange = { courseUnit = it },
            label = { Text("Course Unit") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = grade,
            onValueChange = { grade = it },
            label = { Text("Grade Point (A=5, B=4...)") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val unit = courseUnit.toIntOrNull()
                val gradePoint = grade.toIntOrNull()
                if (unit != null && gradePoint != null) {
                    courses.add(Pair(unit, gradePoint))
                    courseUnit = ""
                    grade = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Course")
        }

        courses.forEachIndexed { index, (unit, gradePoint) ->
            Text("Course ${index + 1}: Unit = $unit, Grade Point = $gradePoint")
        }

        Button(
            onClick = {
                val totalUnits = courses.sumOf { it.first }
                val totalPoints = courses.sumOf { it.first * it.second }
                cgpa = if (totalUnits > 0) totalPoints.toDouble() / totalUnits else 0.0
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate CGPA")
        }

        cgpa?.let {
            Text("Your CGPA: %.2f".format(it), style = MaterialTheme.typography.headlineMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogOut,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun appPreview(){
    MaterialTheme(colorScheme = lightColorScheme(background = Color(0x3C388C86)
    )){
        Surface (modifier= Modifier.fillMaxSize(),
            color= MaterialTheme.colorScheme.background){
            ProfileApp() }
    }
}
