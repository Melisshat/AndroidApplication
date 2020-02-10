package fr.isen.vallauri.toolboxVallauri

import android.app.AlertDialog
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import org.json.JSONObject
import java.io.File
import java.util.*

class StorageActivity : AppCompatActivity() {
    private lateinit var mySave: Button // Save button
    private lateinit var myShow: Button  // Read button
    private lateinit var myLastName: EditText
    private lateinit var myFirstName: EditText
    private lateinit var myDate: TextView // Date of birth
    private var myAge: Int = 0

    companion object {
        private const val JSON_FILE = "data_user_toolbox.json"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_storage)
        mySave = findViewById(R.id.save)
        myShow = findViewById(R.id.show)
        myLastName = findViewById(R.id.lastName)
        myFirstName = findViewById(R.id.firstName)
        myDate = findViewById(R.id.date) // format --> ../../..

        val cal: Calendar = Calendar.getInstance()
        // If pb replace the "_" by "view"
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val mth:Int = month+1
            val newDate = "$year/$mth/$day"
            myDate.text=newDate
            getAge(year, month, day)
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

    // Saving in the JSON file
    private fun saveDataToFile(lastName: String, firstname: String, date: String) {
        if (firstname.isNotEmpty() && lastName.isNotEmpty()) {
            val data = "{'lastName':'$lastName','firstName':'$firstname','date':'$date', 'age':'$myAge'}"
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
            val strAge: String = jsonObject.optString("age")

            // Diplay a dialog window
            AlertDialog.Builder(this@StorageActivity).setTitle("Lecture du fichier")
                .setMessage("Nom: $strLastName\nPrenom: $strFirstName\nDate: $strDate\nAge: $strAge")
                .create().show()

        } else
            Toast.makeText(this@StorageActivity, "Aucune information fournie", Toast.LENGTH_LONG).show()
    }

    private fun getAge(year: Int, month: Int, day: Int){
        val today: Calendar = Calendar.getInstance()

        val birth = Calendar.getInstance()
        birth.set(year, month, day)

        myAge = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR)

        if(today.get(Calendar.DAY_OF_YEAR) < birth.get(Calendar.DAY_OF_YEAR))
            myAge--
    }
}