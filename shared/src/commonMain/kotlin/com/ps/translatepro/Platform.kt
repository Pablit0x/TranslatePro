package com.ps.translatepro

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform