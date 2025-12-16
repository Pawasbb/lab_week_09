package com.example.lab_week_9_f

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val json = intent.getStringExtra("data") ?: "[]"

        setContent {
            ResultScreen(json)
        }
    }
}

@Composable
fun ResultScreen(json: String) {
    val moshi = Moshi.Builder().build()
    val type = Types.newParameterizedType(List::class.java, Student::class.java)
    val adapter = moshi.adapter<List<Student>>(type)

    val studentList = remember {
        adapter.fromJson(json) ?: emptyList()
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(studentList) {
            Text(
                text = it.name,
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
