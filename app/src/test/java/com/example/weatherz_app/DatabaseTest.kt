
package com.example.weatherz_app

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class DatabaseTest {

    @Test
    fun databaseInsertAndRetrieveTest() {

        // 1. Get application context
        val context = ApplicationProvider.getApplicationContext<Context>()

        // 2. Create temporary Room database
        val database = Room.inMemoryDatabaseBuilder(
            context,
            weatherDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()

        try {
            // 3. Get DAO
            val dao = database.searchHistory()

            // 4. Create sample data
            val city = SearchHistory(
                cityName = "Delhi",
                location = Location(latitude = "28.6139")
            )

            // 5. Insert data
            dao.insertHistory(city)

            // 6. Retrieve data
            val result = dao.getHistory()

            // 7. Verify result
            assertEquals(1, result.size)
            assertEquals("Delhi", result[0].cityName)
            assertEquals("28.6139", result[0].location.latitude)

        } finally {
            // 8. Close database
            database.close()
        }
    }
}
