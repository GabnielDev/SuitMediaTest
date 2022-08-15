package com.example.suitmediatest.screens.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediatest.databinding.ActivityMainBinding
import com.example.suitmediatest.model.User
import com.example.suitmediatest.room.UserDB
import com.example.suitmediatest.screens.home.HomeActivity
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val db by lazy { UserDB(this) }

    private lateinit var addImage: CircleImageView
    var resultUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListener()
        addImage = binding.imgPhoto

    }

    private fun setOnClickListener() {
        binding.run {
            btnNext.setOnClickListener {
                saveUser()
            }
            btnCheck.setOnClickListener {
                val text = binding.edtPalindrome.text.toString()
                if (isPalindrome(text)) {
                    tilPalindrome.error = "Palindrome"
                } else {
                    tilPalindrome.error = "Bukan Palindrome"
                }
            }

            imgPhoto.setOnClickListener {
                CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(this@MainActivity)
            }
        }
    }


    private fun saveUser() {
        CoroutineScope(Dispatchers.IO).launch {
            db.userDao().addUser(
                User(
                    0,
                    binding.edtName.text.toString(),
                    resultUri.toString()
                )
            )
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }
    }

    private fun isPalindrome(text: String): Boolean {
        val reverseText = text.reversed()
        return text.equals(reverseText, ignoreCase = true)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                resultUri = result.uri
                binding.imgPhoto.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

}