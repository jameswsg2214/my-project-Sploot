package com.work.sploot.activities

import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.facebook.login.LoginManager
import com.work.sploot.Entity.petMasterEntity
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.ConstantMethods
import com.work.sploot.data.PrefDelegate
import com.work.sploot.data.stringPref

import com.work.sploot.services.MyService
import com.work.sploot.services.Myservice
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.RowId
import androidx.fragment.app.Fragment as fragment

class MainActivity : AppCompatActivity() {

    private var splootDB: SplootAppDB? = null

    var frag: androidx.fragment.app.Fragment? =null

    private var mDelayHandler: Handler? = null

    private val SPLASH_DELAY: Long = 3000

    internal val mRunnable: Runnable = Runnable {

        if (!isFinishing) {

            var pref =  PrefDelegate.init(this@MainActivity)

            var userId by stringPref("userId", null)

            //userId="1"

            if(userId!=null){
                Log.e("Sharedpref","Worked")
                var username by stringPref("name", null)
                var email by stringPref("useremail", null)
                var logtime by stringPref("loginTime", null)


                var res= "Success\nuser Id $userId\nusername $username\nemail id $email\n"

                Log.e("working",res)
                //sample_process()
                result.text=res

              /*  val mIntent = Intent(this, MyService::class.java)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    startForegroundService(mIntent)
                }else
                {
                    startService(mIntent)
                }
*/

               /* val mIntent = Intent(this, Myservice::class.java)
                //mIntent.putExtra("maxCountValue", 1000)
                val myservice= Myservice()

                myservice.enqueueWork(this,mIntent)
*/

                startActivity(Intent(this, firstpage::class.java))

                finish()

            }
            else{


                val mIntent = Intent(this, MyService::class.java)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    startForegroundService(mIntent)
                }else
                {
                    startService(mIntent)
                }

                startActivity(Intent(this, LoginActivity::class.java))

                finish()


            }

        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        splootDB = SplootAppDB.getInstance(this)

        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)

       /* var pref =  PrefDelegate.init(this@MainActivity)

        var userId by stringPref("userId", null)

        userId="1"

        if(userId!=null){
            Log.e("Sharedpref","Worked")
            var username by stringPref("name", null)
            var email by stringPref("useremail", null)
            var logtime by stringPref("loginTime", null)
            var res= "Success\nuser Id $userId\nusername $username\nemail id $email\n"

            Log.e("working",res)
            //sample_process()
            result.text=res

            startService(Intent(this, MyService::class.java))

            startActivity(Intent(this, firstpage::class.java))

            finish()

        }
        else{
            sampleprocess()
        }*/
        dilogbox.setOnClickListener {

            startService(Intent(this, MyService::class.java))

            startActivity(Intent(this, firstpage::class.java))

            finish()

//
        }


    }
    private fun sampleprocess() {


    }

    private fun process(
    ) {

        Log.e("function called...","working")
        AsyncTask.execute {

            var userId by stringPref("userId", null)

            var petid by stringPref("petid", null)

          //  petid= 1.toString()

            var user= userId?.toInt()
            try {
                //val callDetails = splootDB!!.petMasterDao().getAll()

                val callDetails = splootDB!!.petMasterDao()
                var pet = petMasterEntity(
                    userId= user
                )
                var repocecreate=callDetails.insertAll(pet)
                Log.e("rsponceof create","pet id"+repocecreate)
                val viewdata = splootDB!!.petMasterDao().getparticulerpet()
                Log.e("INseted", "worked   ${viewdata.petId}")
                petid= viewdata.petId.toString()

            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
            }
        }
    }


    public override fun onDestroy() {

        if (mDelayHandler != null) {

            mDelayHandler!!.removeCallbacks(mRunnable)
        }

        super.onDestroy()
    }
}
