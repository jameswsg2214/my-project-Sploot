package com.work.sploot.activities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.work.sploot.R
import com.work.sploot.api.ApiProduction
import com.work.sploot.api.request.Cmsdata
import com.work.sploot.api.service.CommonServices
import com.work.sploot.data.ConstantMethods
import com.work.sploot.rx.RxAPICallHelper
import com.work.sploot.rx.RxAPICallback
import io.reactivex.Observable
import android.graphics.BitmapFactory
import android.os.AsyncTask

import android.util.Base64
import androidx.fragment.app.FragmentActivity
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.cmsrecycler.view.*


class cms_fragment:Fragment() {






    private var splootDB: SplootAppDB? = null

    companion object {

        var managers: FragmentActivity?=null

        fun newInstance(manager: FragmentActivity): cms_fragment {

            managers=manager

            return cms_fragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val views=inflater.inflate(R.layout.cmslayout, container, false)


        val recyclerView=views.findViewById<RecyclerView>(R.id.cms_recycler)

        recyclerView.layoutManager = GridLayoutManager(views.context,1)

        splootDB = SplootAppDB.getInstance(views.context)

        var type:String?="Pet"

        AsyncTask.execute {

            var userId by stringPref("userId", null)

            var petid by stringPref("petid", null)

            try {

                val callDetails = splootDB!!.petMasterDao()

                val viewdata=callDetails.getAll(userId!!)

                Log.e("all_data","$viewdata")

                for(i in 0 ..viewdata.size-1) {

                    if(viewdata[i].petCategoryId=="Dog"){

                        type += " Dog"

                        Log.e("pet_in","dog")

                    }
                    else if (viewdata[i].petCategoryId=="Cat"){


                        type += " Cat"

                        Log.e("pet_in","cat")

                    }

                }




/*
                    holder.itemView.post(Runnable {
                        notifyDataSetChanged()

                    })
*/

            } catch (e: Exception) {
                val s = e.message;
                Log.e("Viewer_error",s)
            }
        }

        Log.e("Type","$type")



        var alertDialog = ConstantMethods().setProgressDialog(views.context)
        alertDialog.show()

        var commService: CommonServices = ApiProduction(views!!.context).provideService(CommonServices::class.java)
        var apiCall: Observable<Cmsdata> = commService.getcms()

        RxAPICallHelper().call(apiCall, object : RxAPICallback<Cmsdata> {

            override fun onSuccess(Response: Cmsdata) {

                var datalist=ArrayList<Cmsdata.cmsviewdata>()


                if(Response.status!!) {

                  //  recyclerView.layoutManager = LinearLayoutManager(views?.context)

                    for(i in 0 ..Response.cms_data!!.size-1)
                    {

                        if (type!!.contains(Response.cms_data!![i].category!!, ignoreCase = true)){

                            datalist.add(Response.cms_data!![i])

                        }

                    }

                    val adapter = CustomAdapter(datalist)

                    recyclerView.adapter = adapter

                    alertDialog.dismiss()

                }

                else
                {

                    alertDialog.dismiss()
                }
            }
            override fun onFailed(throwable: Throwable) {

                Log.e("addjourney_dategrid", " clicked Throwable:$throwable")

                alertDialog.dismiss()


            }
        })







        return views
    }


    class CustomAdapter(val userList: ArrayList<Cmsdata.cmsviewdata>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

        var view_data:View?=null


        //this method is returning the view for each item in the list

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {

            val v = LayoutInflater.from(parent.context).inflate(R.layout.cmsrecycler, parent, false)

            view_data=v


            return ViewHolder(v)
        }

        //this method is binding the data on the list
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            var type:String?="Pet"

            holder.bindItems(userList[position],type)


            Log.e("Type","$type")

            holder.itemView.card_click.setOnClickListener {

                Log.e("Click","Fragment Click")

                val fm = managers!!.supportFragmentManager

                val transaction = fm?.beginTransaction()

                transaction?.replace(R.id.cms_frame, cms_page_viewer_frag.newInstance(userList[position]!!, managers!!))

                transaction?.addToBackStack(null)

                transaction?.commit()

            }
        }

        //this method is giving the size of the list
        override fun getItemCount(): Int {
            return userList.size
        }

        //the class is hodling the list view
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bindItems(
                user: Cmsdata.cmsviewdata,
                type: String?
            ) {

                val nametxt = itemView.findViewById(R.id.headertxt) as TextView

                val realationtxt = itemView.findViewById(R.id.html_text) as WebView

                val sub_heading=itemView.findViewById(R.id.sub_txt) as TextView

                val author=itemView.findViewById(R.id.cms_author) as TextView

                val cms_image_heading=itemView.findViewById<ImageView>(R.id.card_image_view)

                val imagedata="data:image/jpeg;base64,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"


                val image_data=user.titleImage

               /* val base64String =
                    "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAACACAYAAADDPmHLAA..."*/




                    Log.e("Typeview","$type")

                    val base64Image = image_data!!.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

                    val decodedString = Base64.decode(base64Image, Base64.DEFAULT)
                    val decodedByte =
                        BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                    cms_image_heading.setImageBitmap(decodedByte)

                    nametxt.text=""+user.heading

                    sub_heading.text=user.subheading

                    author.text="Posted date "+ user.schedule+"\nPosted by ${user.authordetails}"


               /* realationtxt.getSettings().setLoadsImagesAutomatically(true);

                realationtxt.getSettings().setJavaScriptEnabled(true);

                realationtxt.loadData(user.content.toString(),"text/html","UTF-8")*/

            }
        }
    }



}
