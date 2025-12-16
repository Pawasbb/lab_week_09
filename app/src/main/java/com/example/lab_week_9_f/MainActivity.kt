package com.example.lab_week_9_f

import android.content.Intent
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val inputField = remember { mutableStateOf(Student("")) }
    val studentList = remember { mutableStateListOf<Student>() }

    fun listToJson(list: List<Student>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, Student::class.java)
        val adapter = moshi.adapter<List<Student>>(type)
        return adapter.toJson(list)
    }

    Column {
        InputSection(
            inputField = inputField,
            onSubmit = {
                studentList.add(inputField.value)
                inputField.value = Student("")
            },
            onFinish = {
                val intent = Intent(context, ResultActivity::class.java)
                intent.putExtra("data", listToJson(studentList))
                context.startActivity(intent)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        StudentList(studentList)
    }
}

@Composable
fun InputSection(
    inputField: MutableState<Student>,
    onSubmit: () -> Unit,
    onFinish: () -> Unit
) {
    val isValid = inputField.value.name.isNotBlank()

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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = inputField.value.name,
            onValueChange = { inputField.value = Student(it) },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(id = R.string.enter_item)) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                onClick = onSubmit,
                enabled = isValid,
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
    }
}

@Composable
fun StudentList(list: List<Student>) {
    LazyColumn {
        items(list) { student ->
            Text(
                text = student.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
