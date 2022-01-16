package com.example.galleryapplication

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.GridView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_READ_EXTERMAL_STORAGE = 1000
    var gridView:GridView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)

        //동적퍼미션
        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, permissions, 100)
        }

        getAllPhotos()
//
//// 권한 부여 확인
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // 권한 허용 안됨
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)
//            ) {
//                // 이전에 이미 권한이 거부되었을 때 설명
//                alert(
//                    "사진 정보를 얻으려면 외부 저장소 권한이 필수로 필요합니다",
//                    "권한이 필요한 이유"
//                ) {
//                    yesButton {
//                        // 권한 요청
//                        ActivityCompat.requestPermissions(
//                            this@MainActivity,
//                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                            REQUEST_READ_EXTERMAL_STORAGE
//                        )
//                    }
//                    noButton { }
//                }.show()
//            } else {
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
//                    REQUEST_READ_EXTERMAL_STORAGE
//                )
//            }
//        } else {
//            // 권한이 이미 허용됨
//            getAllPhotos()
//
//        }
    }

    // 사용자가 권한 요청 시 호출되는 메서드
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            REQUEST_READ_EXTERMAL_STORAGE -> {
//                if ((grantResults.isNotEmpty() && grantResults[0]
//                            == PackageManager.PERMISSION_GRANTED)
//                ) {
//                    getAllPhotos()
//                } else {
//                    Toast.makeText(this, "권한 거부 됨\"", Toast.LENGTH_SHORT).show()
//                }
//                return
//            }
//        }
//    }

    private fun getAllPhotos() {
        val cursor = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC"
        )
        val uriArr = ArrayList<String>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                // 사진 경로 Uri 가져오기
                val uri =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                uriArr.add(uri)
            }
            cursor.close()
        }
        val adapter = MyAdapter(this, uriArr)
        gridView?.numColumns = 3 // 한 줄에 3개씩
        gridView?.adapter = adapter
    }
}