package fr.isen.vallauri.mysecondapp

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.json.JSONObject
import java.io.File
import java.util.*

class StorageActivity : AppCompatActivity() {
    lateinit var mySave: Button // Save button
    lateinit var myShow: Button  // Read button
    lateinit var myLastName: EditText
    lateinit var myFirstName: EditText
    lateinit var myDate: TextView // Date de naissance
    lateinit var myDateTitle: TextView // Titre

    companion object {
        private const val JSON_FILE = "data_user_toolbox.json"
        /*private const val LAST_NAME_KEY = "LastName"
        private const val FIRST_NAME_KEY = "fisrtname"
        private const val DATE_KEY = "date"*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        mySave = findViewById(R.id.save)
        myShow = findViewById(R.id.show)
        myLastName = findViewById(R.id.lastName)
        myFirstName = findViewById(R.id.firstName)
        myDate = findViewById(R.id.date) // format --> ../../..
        myDateTitle = findViewById(R.id.dateTitle) // date

        val cal: Calendar = Calendar.getInstance()
        // If pb replace the "_" by "view"
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            var mois:Int = month+1
            val newDate = "$year/$mois/$dayOfMonth"
            myDate.text=newDate
        }

        mySave.setOnClickListener {
            saveDataToFile(
                myLastName.text.toString(),
                myFirstName.text.toString(),
                myDate.text.toString()
            )
        }

        myShow.setOnClickListener {
            showDataFromFile()
        }

        myDate.setOnClickListener {
            showDatePicker(cal, dateSetListener)
        }
    }

    // Show the calendar
    private fun showDatePicker(cal: Calendar, dateSetListener: DatePickerDialog.OnDateSetListener) {
        DatePickerDialog(
            this@StorageActivity,
            dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // Saving function in the JSON file
    private fun saveDataToFile(lastName: String, firstname: String, date: String) {
        if (firstname.isNotEmpty() && lastName.isNotEmpty()) {
            val data = "{'lastName':'$lastName','firstName':'$firstname','date':'$date'}"
            //val dataJson: JSONObject = JSONObject().put("lastName", lastName)
            File(cacheDir.absolutePath + JSON_FILE).writeText(data)
            Toast.makeText(
                this@StorageActivity,
                "Sauvegarde des informations de l'utilisateur",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Display the JSON data
    private fun showDataFromFile() {
        val dataJson: String = File(cacheDir.absolutePath + JSON_FILE).readText()
        if (dataJson.isNotEmpty()) {
            val jsonObject = JSONObject(dataJson)
            val strDate: String = jsonObject.optString("date")
            val strLastName: String = jsonObject.optString("lastName")
            val strFirstName: String = jsonObject.optString("firstName")

            // Diplay a dialog window
            AlertDialog.Builder(this@StorageActivity).setTitle("Lecture du fichier")
                .setMessage("Nom:$strLastName\n Prenom:$strFirstName\n Date:$strDate\nAge:0")
                .create().show()

        } else
            Toast.makeText(this@StorageActivity, "Aucune information fournie", Toast.LENGTH_LONG).show()

    }
}