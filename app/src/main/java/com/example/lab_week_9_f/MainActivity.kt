package com.example.lab_week_9_f

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_9_f.ui.theme.LAB_WEEK_9_FTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_9_FTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val listData = remember {
                        mutableStateListOf(
                            Student("Android"),
                            Student("Jetpack Compose")
                        )
                    }
                    val inputField = remember { mutableStateOf(Student("")) }

                    HomeScreen(
                        listData = listData,
                        inputField = inputField
                    )
                }
            }
        }
    }
}

data class Student(
    val name: String
)

@Composable
fun HomeScreen(
    listData: MutableList<Student>,
    inputField: MutableState<Student>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        item {
            InputSection(
                inputField = inputField,
                onSubmit = {
                    if (inputField.value.name.isNotBlank()) {
                        listData.add(inputField.value)
                        inputField.value = Student("")
                    }
                },
                onFinish = {
                    listData.clear()
                }
            )
        }

        item {
            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Student List",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        items(listData) { student ->
            StudentCard(student)
        }
    }
}

@Composable
fun InputSection(
    inputField: MutableState<Student>,
    onSubmit: () -> Unit,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.enter_item),
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputField.value.name,
            onValueChange = {
                inputField.value = Student(it)
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(text = stringResource(id = R.string.enter_item))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔥 BUTTON SAMPINGAN (INI YANG DIMODUL)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onSubmit,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.button_click))
            }

            Button(
                onClick = onFinish,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(text = "Finish")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun StudentCard(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = student.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_9_FTheme {
        val listData = remember {
            mutableStateListOf(
                Student("Preview 1"),
                Student("Preview 2")
            )
        }
        val inputField = remember { mutableStateOf(Student("")) }

        HomeScreen(
            listData = listData,
            inputField = inputField
        )
    }
}
