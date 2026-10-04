package com.kkso.exerunner

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var status: TextView
    private lateinit var log: TextView
    private lateinit var runButton: Button
    private var selectedExe: Uri? = null

    private val picker = registerForActivityResult(
        androidx.activity.result.contract.ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri == null) return@registerForActivityResult

        val name = getDisplayName(uri)
        if (!name.lowercase().endsWith(".exe")) {
            Toast.makeText(this, "ไฟล์นี้ไม่ใช่ .exe", Toast.LENGTH_SHORT).show()
            return@registerForActivityResult
        }

        contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
        selectedExe = uri
        status.text = "เลือกแล้ว: $name"
        runButton.isEnabled = true
        log.text = "พร้อมรัน: $name\n\nหมายเหตุ: APK นี้มีตัวจัดการ EXE และ UI พร้อมแล้ว แต่ยังไม่มี Windows compatibility runtime (Wine/Box64) แบบฝังใน APK"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        status = findViewById(R.id.status)
        log = findViewById(R.id.log)
        runButton = findViewById(R.id.runButton)
        val selectButton: Button = findViewById(R.id.selectButton)

        selectButton.setOnClickListener {
            picker.launch(arrayOf("application/octet-stream", "application/x-msdownload", "*/*"))
        }

        runButton.setOnClickListener {
            val uri = selectedExe ?: return@setOnClickListener
            log.text = "RUN requested:\n$uri\n\nยังรันจริงไม่ได้จนกว่าจะติดตั้ง/เชื่อม Wine + Box64 runtime"
        }
    }

    private fun getDisplayName(uri: Uri): String {
        contentResolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)
            ?.use { cursor ->
                if (cursor.moveToFirst()) return cursor.getString(0)
            }
        return uri.lastPathSegment ?: "unknown.exe"
    }
}
