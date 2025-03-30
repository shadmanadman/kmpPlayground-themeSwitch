package org.kmp.playground.theme.toggle

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform