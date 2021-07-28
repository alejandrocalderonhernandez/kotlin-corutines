package com.alejandro.corutines

import com.alejandro.corutines.util.MyUtils
import kotlinx.coroutines.*

fun main(args: Array<String>) {
    cancel()
}

//block the thread
fun blocking() {
    println("Task 1: ${Thread.currentThread().name}")
    MyUtils.taskLongTimeDurationBlock("DELAY TASK")
    println("Task 3 ${Thread.currentThread().name}")
}
//is structured step by step not execute function in the background
fun suspendFunction() {
    println("Task 1: ${Thread.currentThread().name}")
    runBlocking {
        MyUtils.taskLongTimeDurationDelay("DELAY TASK")
    }
    println("Task 3 ${Thread.currentThread().name}")
}

fun dispatcher() {
    //using when don't care the thread where the fun will be executed
    runBlocking(Dispatchers.Unconfined) { println("Thread unconfined ${Thread.currentThread().name}") }
    //using when we need intensive cpu process
    runBlocking(Dispatchers.Default) { println("Thread default ${Thread.currentThread().name}") }
    //output - output data
    runBlocking(Dispatchers.IO) { println("Thread IO ${Thread.currentThread().name}") }
    //when we need create a new thread
    runBlocking(newSingleThreadContext("thread_name")) { println("Thread ${Thread.currentThread().name}") }
    //only when working with android
    //runBlocking(Dispatchers.Main) { println("Thread ${Thread.currentThread().name}") }
}

//runs in the background the suspend function
//global scope -> all cycle of life our application
fun scopes() {
    println("Task 1: ${Thread.currentThread().name}")
    GlobalScope.launch {
        MyUtils.taskLongTimeDurationBlock("DELAY TASK")
    }
    println("Task 3 ${Thread.currentThread().name}")
}

//allows you to do operations with the job for example cancel
fun job() {
    println("Task 1: ${Thread.currentThread().name}")
    val job =  GlobalScope.launch {
        MyUtils.taskLongTimeDurationBlock("DELAY TASK")
    }
    println("Task 3 ${Thread.currentThread().name}")
    job.cancel()
}

//wait to resolve async function
fun asyncAwait() {
    runBlocking {
        println("1: ${System.currentTimeMillis()}")
        val hello: String = async {
            MyUtils.taskLongTimeDurationCapitalize("HELLO")
        }.await()
        println("2: ${System.currentTimeMillis()}")
        val world: String = async {
            MyUtils.taskLongTimeDurationCapitalize("WORLD")
        }.await()
        println("3: ${System.currentTimeMillis()}")
        println("$hello $world")
    }
}

fun deferred() {
    runBlocking {
        println("1: ${System.currentTimeMillis()}")
        val hello: Deferred<String> = async {
            MyUtils.taskLongTimeDurationCapitalize("HELLO")
        }
        println("2: ${System.currentTimeMillis()}")
        val world: Deferred<String> = async {
            MyUtils.taskLongTimeDurationCapitalize("WORLD")
        }
        println("3: ${System.currentTimeMillis()}")
        println("${hello.await()} ${world.await()}")
    }
}

fun cancel() {
    runBlocking {
        val job: Job = launch {
            MyUtils.repeatThousand()
        }
        delay(1500)
        println("wait to long!!!")
        job.cancel()
        println("job cancel")
    }
}
