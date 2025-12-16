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

                    val listData = remember { mutableStateListOf<Student>() }
                    val inputField = remember { mutableStateOf(Student("")) }

                    Home(
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
fun Home(
    listData: MutableList<Student>,
    inputField: MutableState<Student>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // INPUT AREA
        item {
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

                Button(
                    onClick = {
                        if (inputField.value.name.isNotBlank()) {
                            listData.add(inputField.value)
                            inputField.value = Student("")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(id = R.string.button_click))
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // LIST DATA — CARD VERSION (COMMIT 3)
        items(listData) { student ->
            StudentItem(student)
        }
    }
}

@Composable
fun StudentItem(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
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
        val listData = remember { mutableStateListOf<Student>() }
        val inputField = remember { mutableStateOf(Student("")) }

        Home(
            listData = listData,
            inputField = inputField
        )
    }
}
