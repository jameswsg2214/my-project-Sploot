package com.work.sploot.activities

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.work.sploot.Entity.petMasterEntity
import com.work.sploot.R
import com.work.sploot.SplootAppDB
import com.work.sploot.data.stringPref
import kotlinx.android.synthetic.main.getbreadtype.view.*

class Getbreadtype : Fragment() {
    private var splootDB: SplootAppDB? = null


    companion object {
        var petType=0
        fun newInstance(petTypedata:Int): Getbreadtype {
            petType=petTypedata
            return Getbreadtype()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val views = inflater.inflate(R.layout.getbreadtype, container, false)

        splootDB = SplootAppDB.getInstance(views.context)

        val breed = arrayOf("Breed", "Afghan Hound", "Bichon Frise", "Cavalier King")

        val breedspinner=views.findViewById<Spinner>(R.id.petbreed)
        var datarray= arrayOf("")
        Log.e("pet type","//////////////// $petType")
        if(petType==1){

            datarray = arrayOf(
                "Affenpinscher",
                "Afghan Hound",
                "Airedale Terrier",
                "Alaskan Malamute",
                "American Bull Terrier",
                "American Eskimo Dog",
                "American Staffordshire Terror",
                "Australian Cattle Dog",
                "Austrilaian Shepherd",
                "Basenji",
                "Basset Hound",
                "Beagle",
                "Bearded Collie",
                "Bernese Mountain Dog",
                "Bichon Frise",
                "Blood Hound",
                "Border Collie",
                "Bosten Terrier",
                "Boxer",
                "Bull Terrier",
                "Bulldog",
                "Cavalier King Charles Spaniel",
                "Chihuahua",
                "Chinese Crested Dog",
                "Chow Chow",
                "Cocker Spaniel",
                "Dachshund",
                "Dalmatian Dog",
                "Doberman",
                "English Springer Spaniel",
                "French Bull Dog",
                "German Shepherd Dog",
                "Germanshephered",
                "Golden Retrier",
                "Gordon Setter",
                "Great Dane",
                "Grey Hound",
                "Havenese Dog",
                "Irish Setter",
                "Irish Wolfhound",
                "Italian Grey Hound",
                "Labrador Retriever",
                "Lowchen",
                "Maltese Dog",
                "Pembroke Welesh Corgi",
                "Pointer",
                "Pomeranian",
                "Poodle",
                "Pug",
                "Rottwailer",
                "Rottweiler",
                "Sheltie",
                "Shih Tzu",
                "Siberin Husky",
                "Staffordshire Bull Terrier",
                "Tibetan Mastiff",
                "Yorkshire Terrier"

            )
        }
        else if(petType==2){
            datarray = arrayOf(
                "Abyssinian",
                "Abyssinian Cat",
                "Aegean",
                "African Golden Cat",
                "African Wildcat",
                "American Bobtail",
                "American Bobtail",
                "American Bobtail Cat Breed",
                "American Curl",
                "American Curl Cat Breed",
                "American Shorthair",
                "American Shorthair",
                "American Shorthair Cat",
                "American Wireair",
                "American Wirehair",
                "Andean Mountain",
                "Andean Mountain",
                "Arabian Mau",
                "Asian",
                "Asian Golden Cat",
                "Asian Semi-Longhair",
                "Australian Mist",
                "Balinese",
                "Balinise Cat",
                "Bambino",
                "Bay Cat",
                "Bengal",
                "Bengal Cat",
                "Birman",
                "Birman",
                "Black-Footed",
                "Bobcat",
                "Bombay",
                "Bombay Cat",
                "Bombay Cat",
                "Brazilian Shorthair",
                "British Longhair",
                "British Semi-Longhair",
                "British Shorthair",
                "Burmese",
                "Burmese Cat",
                "Burmilla",
                "Burmilla",
                "California Spangled",
                "Canada Lynx",
                "Caracal",
                "Celkrix Rex",
                "Chantilly-Tiffany",
                "Chartreux",
                "Chausie",
                "Cheetoh",
                "Chinese Mountain",
                "Colorpoint Shorthair",
                "Cornish Rex",
                "Cornish Rex",
                "Cymric",
                "Cyprus",
                "Devon Rex",
                "Devon Rex",
                "Domestic",
                "Donskoy",
                "Dwelf",
                "Egyptian Mau",
                "Egyptian Mau",
                "Eurasian Lynx",
                "European Shorthair",
                "European Wildcat",
                "Exoctic Shorthair",
                "Exotic Shorthair",
                "Fishing Cat",
                "Flat-Headed",
                "Foldex",
                "Geoffroys Cat",
                "German Rex",
                "Havana Brown",
                "Havana Brown",
                "Highlander",
                "Himalayan Or Colorpoint Persian",
                "Himaliyan Cat",
                "Iberian Lynx",
                "Japanese Bobtail",
                "Japanise Bobtail",
                "Javanase Cat",
                "Javanese Or Colorpoint Longhair",
                "Jungle",
                "relian Bobtail",
                "Khao Manee",
                "Kodkod",
                "Korat",
                "Korean Bobtail",
                "Korn Ja",
                "Kurilian Bobtail Or Kuril Islands Bobtail",
                "Laperm",
                "Leopard",
                "Lykoi",
                "Maine Coon",
                "Maine Coon",
                "Maine Coon Cat",
                "Manax  Cat",
                "Manx",
                "Marbled",
                "Margay",
                "Mekong Bobtail",
                "Minskin",
                "Munchikan Cat",
                "Nabelung",
                "Napoleon",
                "Nebelung",
                "Norwegain Forest Cat",
                "Norwegian Forest Cat",
                "Ocelot",
                "Ocicat",
                "Ojos Azules",
                "Oregon Rex",
                "Oriental Bicolor",
                "Oriental Longhair Or Foreign Longhair",
                "Oriental Shorthair",
                "Oriental Shorthair",
                "Pallas's Cat",
                "Pampas",
                "Persian (modern)",
                "Persian (traditional)",
                "Persian Cat",
                "Peter Bald",
                "Peterbald",
                "Pixie Bob",
                "Pixie-Bob",
                "Raas",
                "Rag Doll",
                "Ragamuffin Cat",
                "Ragamuffin, Or Liebling (obsolete)",
                "Ragdoll",
                "Russian Blue",
                "Russian White, Black, And Tabby",
                "Rusty-Spotted Cat",
                "Sam Sawet",
                "Sand Cat",
                "Savannah",
                "Scottish Fold",
                "Selkirk Rex",
                "Serengeti",
                "Serrade Petit",
                "Serval",
                "Siamese (modern)",
                "Siamese Cat",
                "Siamese Cat",
                "Siberian Or Siberian Forest Cat; Neva Masquerade",
                "Sibirien Cat",
                "Singapura",
                "Singapura Cat",
                "Snowshoe",
                "Snowshoe Cat",
                "Sokoke",
                "Somali",
                "Somali Cat",
                "Sphynx",
                "Sphynx Cat",
                "Sunda Leopard",
                "Suphalak",
                "Thai Lilac",
                "Thai Or Traditional",
                "Classic, Or Old-Style Siamese",
                "Tonkinese",
                "Tonkinese Cat",
                "Toyger",
                "Toyger",
                "Turkish Angora",
                "Turkish Angora",
                "Turkish Van",
                "Ukrainian Levkoy",
                "Wila Krungthep",
                "York Chocolate"
            )
        }
       val adapter = ArrayAdapter<String>(
            views.context,
            android.R.layout.simple_dropdown_item_1line,
           datarray
        )
        views.auto_complete_text_view.setAdapter(adapter)
        views.auto_complete_text_view.threshold = 1
        views.auto_complete_text_view.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            process(selectedItem)
            Toast.makeText(views.context,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
        }
        views.auto_complete_text_view.setOnDismissListener {
          //  Toast.makeText(views.context,"Suggestion closed.", Toast.LENGTH_SHORT).show()
           // process(views.auto_complete_text_view.text.toString())
        }
        views.auto_complete_text_view.onFocusChangeListener = View.OnFocusChangeListener{
                view, b ->
            if(b){
                views.auto_complete_text_view.showDropDown()
            }
        }
        return views
    }
    private fun process(breed:String) {
        Log.e("function called...","working")
        AsyncTask.execute {
            var userId by stringPref("userId", null)
            var petid by stringPref("petid", null)
            var user= userId?.toInt()
            var petId= petid?.toLong()
            try {
                //val callDetails = splootDB!!.petMasterDao().getAll()
                val viewdata = splootDB!!.petMasterDao().getSelectdata(petid!!,userId!!)
                var pet = petMasterEntity(
                    petId = petId,
                    userId= user,
                    petName = viewdata.petName,
                    age = viewdata.age,
                    sex = viewdata.sex,
                    petCategoryId = viewdata.petCategoryId,
                    breedId= breed
                )
                val callDetails = splootDB!!.petMasterDao().update(pet)

                val get = splootDB!!.petMasterDao().getSelect(petid!!)

                Log.e("tabledata",""+get)

            } catch (e: Exception) {
                val s = e.message
                Log.e("Error in breed",s)
            }
        }
    }
}