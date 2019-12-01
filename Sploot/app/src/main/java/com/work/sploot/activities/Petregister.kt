package com.work.sploot.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.work.sploot.Entity.petMasterEntity
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import com.work.sploot.services.MyService
import kotlinx.android.synthetic.main.activity_petregister.*
import kotlinx.android.synthetic.main.getbreadtype.*
import kotlinx.android.synthetic.main.getdob.*
import kotlinx.android.synthetic.main.getpetname.*

class Petregister : AppCompatActivity() {
    public lateinit var mPager: ViewPager
    private var splootDB: SplootAppDB? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_petregister)

        splootDB = SplootAppDB.getInstance(this)

        var petid by stringPref("petid", null)

        var userid by stringPref("userId", null)

        Log.e("petid","=======>>>>$petid")

        Log.e("userid","=======>>>>$userid")

        mPager = findViewById(R.id.pager)

        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager)

        mPager.adapter = pagerAdapter

        mPager.setPageTransformer(true, ZoomOutPageTransformer())

        savebtn.setOnClickListener {



            getdataproces(3)
//            if (iswork) {
//                var text = petname?.text.toString()
//                var petname = petname?.text.toString()
//                Log.e("scolledname====>", "$petname")
//                var dob = petage?.text.toString()
//                Log.e("scolledname====>", "$dob")
//                var breed = auto_complete_text_view?.text.toString()
//                Log.e("scolledname====>", "$breed")
//                datafatch(petname, dob, breed)
//                Log.e("petnnames", "$text")
//                startActivity(Intent(this, firstpage::class.java))
//            }
//            else{
//                Toast.makeText(this,"Please Add pet photo",Toast.LENGTH_LONG).show()
//            }
        }
        mPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
               // Log.d("psaha","$state")
            }
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                Log.d("psaha","$position")
            }

            override fun onPageSelected(position: Int) {
                Log.e("onPageSelected","$position")
                when (position) {
                    0 -> {

                        savebtn.visibility=View.GONE

                    }
                    1 -> {
                        getdataproces(1)
                        savebtn.visibility=View.GONE
                    }
                    2 -> {
                       // Log.e("petnemedata","${petname?.text.toString().trim()}")
                        if (petname?.text.toString().trim().isNullOrEmpty()){
                            petname.error="Pet name cannot be empty"
                            mPager.currentItem = 1
                        }
                        else{
                            Log.e("petnamedata","${petname?.text.toString().trim()}")
                        }
                        savebtn.visibility=View.GONE
                    }
                    3 -> {
                        if (auto_complete_text_view?.text.toString().trim().isNullOrEmpty()){
                            auto_complete_text_view.error="Pet Type cannot be empty"
                            mPager.currentItem = 2
                        }
                        savebtn.visibility=View.GONE
                        Log.e("Error check",".......................................${auto_complete_text_view?.text.toString().trim()}//////////////////////////////////")
                }

                    4 -> {
                        if (petage?.text.toString().trim().isNullOrEmpty()){
                        //    petage.error="Date of Birth cann't be empty"

                            Toast.makeText(this@Petregister,"Date of Birth can't be empty",Toast.LENGTH_SHORT).show()

                            mPager.currentItem = 3


                        }
                        savebtn.visibility=View.GONE
                    }
                    5 -> {
                        getdataproces(2)
                        pro()
                    }
                    else -> {
                        savebtn.visibility=View.GONE

                        if(iswork) {
                            //nextinhome.visibility=View.VISIBLE
                            var petname = petname?.text.toString()
                            Log.e("scolledname====>", "$petname")
                            var dob = petage?.text.toString()
                            Log.e("scolledname====>", "$dob")
                            var breed = auto_complete_text_view?.text.toString()
                            Log.e("scolledname====>", "$breed")
                            datafatch(petname, dob, breed)
                        }

                    }
                }
            }
        })
    }


    private fun datafatch(petname: String, dob: String, breed: String) {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var username by stringPref("name", null)
            var petimage by stringPref("petimage", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()
            try {
                //val callDetails = splootDB!!.petMasterDao().getAll()
                val viewdata = splootDB!!.petMasterDao().getSelectdata(petid!!,userId!!)

                var pet = petMasterEntity(
                    petId = petId,
                    userId= user,
                    sex = viewdata.sex,
                    petName = petname,
                    age = dob,
                    petCategoryId = viewdata.petCategoryId,
                    breedId= breed,
                    parenOwnerName = username,
                    photo = viewdata.photo,
                    active=1
                )

                val callDetails = splootDB!!.petMasterDao().update(pet)
                val get = splootDB!!.petMasterDao().getSelect(petid!!)
                petimage=viewdata.photo
                //Toast.makeText(this,"Your Pet registered successfully",Toast.LENGTH_LONG).show()

                Log.e("tabledata",""+get)

            } catch (e: Exception) {
                val s = e.message
                Log.e("Error",s)
            }
        }
    }
    private fun getdataproces(i: Int):String {
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var username by stringPref("name", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()
            try {
                //val callDetails = splootDB!!.petMasterDao().getAll()
                val viewdata = splootDB!!.petMasterDao().getSelectdata(petid!!,userId!!)
                Log.e("tabledata",""+viewdata+"   123456789    "  + viewdata.petCategoryId)
                if(i==1){
                    when {
                        viewdata.petCategoryId=="Cat" -> petType=2
                        viewdata.petCategoryId=="Dog" -> petType=1
                        viewdata.petCategoryId==null -> {
                            Log.e("process","1234567890-")
                          //  Toast.makeText(applicationContext,"Select pet Type ",Toast.LENGTH_LONG).show()
                            mPager.currentItem = 0
                        }
                    }
                }
                else  if (i==2){
                    when {
                        viewdata.sex==null -> {
                            Log.e("process","1234567890-")
//                            Toast.makeText(getApplicationContext(),"Select pet Type ",Toast.LENGTH_LONG).show()
                            mPager.currentItem = 4
                        }
                    }

                }
                else  if (i==3){
                    when {
                        viewdata.photo!=null -> {
                            Log.e("process","Enter")
                            iswork=false
                            var text = petname?.text.toString()
                            var petname = petname?.text.toString()
                            Log.e("scolledname====>", "$petname")
                            var dob = petage?.text.toString()
                            Log.e("scolledname====>", "$dob")
                            var breed = auto_complete_text_view?.text.toString()
                            Log.e("scolledname====>", "$breed")
                            datafatch(petname, dob, breed)
                            Log.e("petnnames", "$text")


                            startActivity(Intent(this, firstpage::class.java))

                            runOnUiThread(Runnable {

                                Toast.makeText(this,"Pet Profile registered successfully",Toast.LENGTH_LONG).show()

                            })



                        }
                        else ->
                            toastfun()
                    }

                }


            } catch (e: Exception) {
                val s = e.message
//                Log.e("Error12345",s)
            }
        }
        return ""
    }

    private fun toastfun() {
        Toast.makeText(applicationContext,"Select pet Type ",Toast.LENGTH_LONG).show()
    }

    private fun pro() {
        savebtn.visibility=View.VISIBLE

    }
    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mPager.currentItem = mPager.currentItem - 1
        }
    }
    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = Companion.NUM_PAGES
        override fun getItem(position: Int): Fragment? {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = selectpet()
                1 -> fragment = getpetname()
                2 -> fragment = Getbreadtype.newInstance(petType)
                3 -> fragment = Getdob()
                4 -> fragment = selectsex()
                5 -> fragment = profilepicfragment()
            }
            return fragment
            //return ScreenSlidePageFragment()
        }
    }
    companion object {
        const val NUM_PAGES = 6
        var petType=0
        var iswork:Boolean=true
    }
}