package fr.isen.vallauri.mysecondapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity(), LocationListener {
    override fun onLocationChanged(location: Location?) {
        locationTV.text =
            getString(R.string.permission_location, location?.latitude, location?.longitude)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
        Log.d("PermissionActivity", "new status : $status")
    }

    override fun onProviderEnabled(provider: String?) {
        Toast.makeText(this, "Gps activé", Toast.LENGTH_SHORT).show()
    }

    override fun onProviderDisabled(provider: String?) {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
        Toast.makeText(this, "Gps désactivé", Toast.LENGTH_SHORT).show()
    }
    // initialisation not null
    private var locationManager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

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

        val permissionsNotGranted = getAllPermissionNotGranted()

        // if contacts not accepted
        if (!permissionsNotGranted.contains(Manifest.permission.READ_CONTACTS)) {
            displayContact()
        }
        //if location not accepted
        if (!permissionsNotGranted.contains(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            getCurrentLocation()
        }
        if (permissionsNotGranted.isNotEmpty()) {
            requestPermission()
        }

        if (permissionsNotGranted.isEmpty()) {
            displayContact()
            getCurrentLocation()
        } else {
            requestPermission()
        }
    }

    private fun displayCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun getAllPermissionNotGranted(): Array<String> {
        val listOfPermission = arrayListOf<String>()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            listOfPermission.add(Manifest.permission.READ_CONTACTS)
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            listOfPermission.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        return listOfPermission.toTypedArray()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_REQUEST_READ_CONTACT
        )
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

    private fun getCurrentLocation() {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            locationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                2000,
                1f,
                this
            )

            val location =
                locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            locationTV.text =
                getString(R.string.permission_location, location?.latitude, location?.longitude)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) || (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK)) {
            cameraIV.setImageURI(data?.data)
        }
    }

    //If we stop the app, the location should'nt continue running
    override fun onStop() {
        super.onStop()
        locationManager?.removeUpdates(this)

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
            Toast.makeText(
                this,
                "Permission refusée par l'utilisateur",
                Toast.LENGTH_LONG
            ).show()
    }

    companion object {
        private const val PERMISSION_REQUEST_READ_CONTACT = 12
        private const val REQUEST_IMAGE_CAPTURE = 13
        private const val REQUEST_IMAGE_GALLERY = 14
    }
}
