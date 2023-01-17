package com.ps.translatepro.translate.data.local

import com.ps.translatepro.database.TranslateDatabase
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory() {
    actual fun create() : SqlDriver{
        return NativeSqliteDriver(schema = TranslateDatabase.Schema, "translate.db")
    }
}