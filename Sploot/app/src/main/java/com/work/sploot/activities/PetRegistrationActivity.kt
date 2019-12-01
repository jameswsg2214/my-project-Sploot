package com.work.sploot.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

import kotlinx.android.synthetic.main.activity_pet_registration.*
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.content.Intent
import android.graphics.Bitmap
import android.os.AsyncTask
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import com.work.sploot.Entity.petMasterEntity
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.ConstantMethods
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_pet_detailsview.*
import kotlinx.android.synthetic.main.activity_pet_registration.profileimagae
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*


class PetRegistrationActivity : AppCompatActivity() {
    var cal = Calendar.getInstance()
    //var catagory: String? =nul
    private var splootDB: SplootAppDB? = null
    var textView_msg: TextView? = null
    val REQUEST_SELECT_IMAGE_IN_ALBUM = 0
    var icleck=true
    var catagory: String? =null
    var petname: String? =null
    var breedname: String? =null
    var gendername:String?=null
    var cyclename:String?=null
    var perodname:String?=null
    var agename:String?=null
    var dataname:String?=null
    var image: Bitmap? = null
    private val REQUEST_TAKE_PHOTO = 1
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.work.sploot.R.layout.activity_pet_registration)
        splootDB = SplootAppDB.getInstance(this)

        //var selectpet
        var selectpet = arrayOf("")
        val breed = arrayOf("Breed","Afghan Hound", "Bichon Frise","Cavalier King")
        val cycledata = arrayOf("Cycle","1 Months", "2 Months","3 Months","4 Months","5 Months")
        val perioddata = arrayOf("Period","1 Weeks", "2 Weeks","3 Weeks","4 Weeks")
        val spinner = findViewById<Spinner>(R.id.spinner_sample)
        val spinner_breed = findViewById<Spinner>(R.id.breed)
        dogselect.setOnClickListener {
            selectpet = arrayOf("Dog", "Cat")
            catagory="dog"
            val spinnerArrayAdapter = ArrayAdapter(
                this, R.layout.spin_item, selectpet
            )
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spin_item)
            val spinnerArrayAdaptertextview = ArrayAdapter(
                this, R.layout.spin_textview, breed
            )
            spinnerArrayAdaptertextview.setDropDownViewResource(R.layout.spin_textview)
            spinner_breed.adapter = spinnerArrayAdaptertextview

            spinner.adapter = spinnerArrayAdapter
            petselect.visibility=View.GONE
            registerlayout.visibility=View.VISIBLE

        }
        catselect.setOnClickListener {
            selectpet = arrayOf( "Cat","Dog")
            val spinnerArrayAdapter = ArrayAdapter(
                this, R.layout.spin_item, selectpet
            )
            spinnerArrayAdapter.setDropDownViewResource(R.layout.spin_item)

            val spinnerArrayAdaptertextview = ArrayAdapter(
                this, R.layout.spin_textview, breed
            )

            spinnerArrayAdaptertextview.setDropDownViewResource(R.layout.spin_textview)
            spinner_breed.adapter = spinnerArrayAdaptertextview


            spinner.adapter = spinnerArrayAdapter
            catagory="cat"
            petselect.visibility=View.GONE
            registerlayout.visibility=View.VISIBLE

        }

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        selectdob.setOnClickListener {
            DatePickerDialog(this@PetRegistrationActivity,
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()

        }
        // Initializing a String Array
        val genders = arrayOf("Gender","male", "female")
        val spinnerArrayAdaptergender = ArrayAdapter(this, R.layout.spin_textview, genders)
        spinnerArrayAdaptergender.setDropDownViewResource(R.layout.spin_textview)
        gender.adapter = spinnerArrayAdaptergender

        val spinnerArrayAdaptercycle = ArrayAdapter(this, R.layout.spin_textview, cycledata)
        spinnerArrayAdaptercycle.setDropDownViewResource(R.layout.spin_textview)
        cycle.adapter = spinnerArrayAdaptercycle
        val spinnerArrayAdapterperiod = ArrayAdapter(this, R.layout.spin_textview, perioddata)
        spinnerArrayAdapterperiod.setDropDownViewResource(R.layout.spin_textview)
        period.adapter = spinnerArrayAdapterperiod
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + selectpet[position])
                catagory=selectpet[position]
               // Toast.makeText(this@PetRegistrationActivity, " " + pettype[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected

            }
        }
        spinner_breed.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + breed[position])
                breedname= breed[position]
               // Toast.makeText(this@PetRegistrationActivity, " " + breed[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
                breedname= breed[1]
                Log.e("notselected ",""+catagory)
            }
        }
        gender.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + genders[position])
                gendername= genders[position]

                if(genders[position]=="female"){

                    cyclelayout.visibility=View.VISIBLE
                }else{
                    cyclelayout.visibility=View.GONE
                }

               // Toast.makeText(this@PetRegistrationActivity, " " + genders[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
        cycle.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + cycledata[position])
                cyclename=cycledata[position]
                // Toast.makeText(this@PetRegistrationActivity, " " + genders[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
        period.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Log.e("masdfdd", "" + perioddata[position])
                perodname=perioddata[position]
                // Toast.makeText(this@PetRegistrationActivity, " " + genders[position], Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }
        agedone.setOnClickListener {
                val age = findViewById<EditText>(R.id.age)
                val nametxt = findViewById<EditText>(R.id.petdob)
                val calendar = Calendar.getInstance()
                var refere=age.text.toString()
                calendar.add(Calendar.YEAR, -refere.toInt())
                val myFormat = "dd/MM/yyyy" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                var string = sdf.format(calendar.time)
                Log.e("MyApp", "90 days ago:" + calendar.time.toString()+"    "+string)
                nametxt.setText(string)
            agename=age.text.toString()
            dataname=string
        }
        imageupload.setOnClickListener {
            val intent1 = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (intent1.resolveActivity(packageManager) != null) {
                startActivityForResult(intent1, REQUEST_TAKE_PHOTO)
            }
        }
        gallaryupload.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, REQUEST_SELECT_IMAGE_IN_ALBUM)
            }
        }
        submitall.setOnClickListener {
            val ptname = findViewById<EditText>(R.id.getpetname)

            when {
                getpetname.text.isNullOrEmpty() -> getpetname?.error ="Pet name cannot be empty."
                age.text.isNullOrEmpty() -> age?.error ="DOB cannot be empty."
                petdob.text.isNullOrEmpty() -> petdob?.error ="DOB cannot be empty."
                else -> {
                    petname=ptname.text.toString()
                    Log.e("collectdata"," cat---$catagory petname----$petname breadname  $breedname gender $gendername cycle $cyclename perood $perodname age $agename date$dataname")
                    process(
                        catagory,
                        petname!!,
                        breedname,
                        gendername,
                        cyclename,
                        perodname,
                        agename,
                        dataname,
                        image
                    )

                }
            }



        }
    }

    private fun process(
        catagory: String?,
        petname: String,
        breedname: String?,
        gendername: String?,
        cyclename: String?,
        perodname: String?,
        agename: String?,
        dataname: String?,
        image: Bitmap?
    ) {

        Log.e("function called...","working")
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var user= userId?.toInt()
            try {
                //val callDetails = splootDB!!.petMasterDao().getAll()

                val callDetails = splootDB!!.petMasterDao()
                var pet =petMasterEntity(
                    userId= user,
                    petCategoryId=catagory,
                    petName=petname,
                    breedId=breedname,
                    sex=gendername,
                    age = agename,
                    monthlyCycle=cyclename,
                    period=perodname
                )
                var repocecreate=callDetails.insertAll(pet)
                Log.e("rsponceof create","pet id"+repocecreate)
                val viewdata = splootDB!!.petMasterDao().getparticulerpet()
                Log.e("INseted", "worked   ${viewdata.petId}")
                        startActivity(Intent(this,Petprofile::class.java))
        Toast.makeText(this,"Your pet $petname register successfully",Toast.LENGTH_LONG).show()
        finish()
            } catch (e: Exception) {
                val s = e.message;
                Log.e("Error",s)
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = current.format(formatter)

        Log.e("currentdate",formatted)
        var string = sdf.format(cal.getTime())
        val days = getDaysDif(
            getLocalDateFromString(string, "dd/MM/yyyy"),
            getLocalDateFromString(formatted, "dd/MM/yyyy"))
        val nametxt = findViewById<EditText>(R.id.petdob)
        val petage =findViewById<EditText>(R.id.age)
        nametxt.setText(string)
        var format: Long =days
        var years=format/365
        petage.setText("$years")
        Log.e("change convert"," overall days $days years $years ")
        agename=years.toString()
        dataname=nametxt.text.toString()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getLocalDateFromString(d: String, format: String): LocalDate {
        return LocalDate.parse(d, DateTimeFormatter.ofPattern(format))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDaysDif(fromDate: LocalDate, toDate: LocalDate): Long {
        return ChronoUnit.DAYS.between(fromDate, toDate)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_TAKE_PHOTO) {
                image = data?.extras?.get("data") as Bitmap
                profileimagae.setImageBitmap(image)
            }
            else if(requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM){
                //Toast.makeText(this,"selected",Toast.LENGTH_LONG).show()
                 image = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data?.getData())
                profileimagae.setImageBitmap(image)
            }
        }
    }
}
