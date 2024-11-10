package com.bruno13palhano

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform