package com.alejandro.corutines.util

import kotlinx.coroutines.delay
import java.util.*

class MyUtils {

    companion object {
         fun taskLongTimeDurationBlock(message: String) {
             println("Start: $message")
             Thread.sleep(1000)
             println("Finish: $message")
         }

        suspend fun taskLongTimeDurationDelay(message: String) {
            println("Start: $message")
            delay(1000)
            println("Finish: $message")
        }

        suspend fun taskLongTimeDurationCapitalize(word: String): String{
            delay(1000)
            return word.lowercase(Locale.getDefault())
        }

        suspend fun repeatThousand() {
            repeat(1000) { i ->
                println("job: $i")
                kotlinx.coroutines.delay(200)
            }
        }
    }
}