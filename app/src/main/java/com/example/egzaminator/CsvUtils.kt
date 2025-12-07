package com.example.egzaminator

import android.content.Context
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.InputStreamReader

object CsvUtils {
    fun readCSVFromRaw(context: Context, resourceId: Int): List<Question> {
        val result = mutableListOf<Question>()
        context.resources.openRawResource(resourceId).use { inputStream ->
            CSVReader(BufferedReader(InputStreamReader(inputStream))).use { reader ->
                var line: Array<String>?
                var id = 0;
                while (true) {
                    id++
                    line = reader.readNext() ?: break
                    if (line[0].equals("question", ignoreCase = true)) continue

                    result.add(Question(
                        id,
                        line[0],
                        line.slice(1..4).toTypedArray(),
                        line[5].toIntOrNull() ?: 0,
                        line[6].toIntOrNull() ?: 0,
                        ))
                }
            }
        }
        return result
    }
}