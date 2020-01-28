package fr.isen.vallauri.mysecondapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSION_REQUEST_READ_CONTACT
            )
        } else {
            displayContact()
        }

        /* CAMERA + GALLERY POPUP */
        cameraIV.setOnClickListener {
            AlertDialog.Builder(this@InformationActivity)
                .setTitle("Que voulez vous faire ?")
                /* CAMERA */
                .setPositiveButton("Caméra") { _, _ ->
                    displayCamera()
                }
                /* GALLERY */
                .setNegativeButton("Galerie") { _, _ ->
                    displayGallery()
                }
                .create().show()
        }
    }

    private fun displayCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun displayGallery() {
        val goGalleryIntent = Intent(Intent.ACTION_PICK)
        goGalleryIntent.type = "image/*"
        startActivityForResult(goGalleryIntent, REQUEST_IMAGE_GALLERY)
    }

    private fun displayContact() {
        val contacts = loadContact()
        informationRV.apply {
            layoutManager = LinearLayoutManager(this@InformationActivity)
            adapter = ContactAdapter(contacts)
            DividerItemDecoration(
                this@InformationActivity,
                LinearLayoutManager.VERTICAL
            )
        }
    }

    private fun loadContact(): List<String> {
        val contactNameList = arrayListOf<String>()
        val phoneCursor =
            contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        phoneCursor?.let { cursor ->
            while (cursor.moveToNext()) {
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                contactNameList.add(name)
            }
            cursor.close()
        }
        return contactNameList
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) || (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK)) {
            cameraIV.setImageURI(data?.data)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //Contact access
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == PERMISSION_REQUEST_READ_CONTACT)
            displayContact()
        else
            Toast.makeText(this, "Permission refusée par l'utilisateur", Toast.LENGTH_LONG).show()
    }

    companion object {
        private const val PERMISSION_REQUEST_READ_CONTACT = 12
        private const val REQUEST_IMAGE_CAPTURE = 13
        private const val REQUEST_IMAGE_GALLERY = 14
    }
}
