package net.aptivist.covidmappproject.data.api.implementations
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import net.aptivist.covidmappproject.data.api.interfaces.ICasesMexicoStatesService
import net.aptivist.covidmappproject.data.api.models.CasesMexicoState
import net.aptivist.covidmappproject.data.api.models.CasesMexicoStateList
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
class CasesMexicoStatesServiceImpl(private val client: OkHttpClient, private val gson: Gson, private val baseURL: String) : ICasesMexicoStatesService {

    override fun getCases(): CasesMexicoStateList? {

        val reqbody = RequestBody.create(null, ByteArray(0))
        val request = Request.Builder()
            .addHeader("Content-Type","application/json")
            .addHeader("charset","utf-8")
            .url(baseURL).post(reqbody).build()

        val call = runBlocking(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        val response = call.body?.string()
        Log.d("MX",response)

        var cases : CasesMexicoStateList? = null

        if (response != null){
            try {
                val listOfMexicoCases = mutableListOf<CasesMexicoState>()

                val jObject= JSONObject(response)
                val jsonString=jObject.getString("d")
                val jArray = JSONArray(jsonString)
                lateinit var date:String
                for (i in 0 until jArray.length()-1){
                    val state = jArray.getJSONArray(i)

                    var numberState=state.getString(0).toInt()
                    var nameState=state.getString(1)
                    var totalCases=state.getString(2).toInt()
                    var positive=state.getString(4).toInt()
                    var negative=state.getString(5).toInt()
                    var suspect=state.getString(6).toInt()
                    var death=state.getString(7).toInt()

                    var  casesMexicoState=CasesMexicoState(numberState,nameState,totalCases,positive,negative,suspect,death)

                    /*Log.d("MX",casesMexicoState.numberState.toString())
                    Log.d("MX",casesMexicoState.nameState)
                    Log.d("MX",casesMexicoState.totalCases.toString())
                    Log.d("MX",casesMexicoState.positive.toString())
                    Log.d("MX",casesMexicoState.negative.toString())
                    Log.d("MX",casesMexicoState.suspect.toString())
                    Log.d("MX",casesMexicoState.death.toString())*/
                    listOfMexicoCases.add(casesMexicoState)

                    if(i==0){
                        date=state.getString(8)
                    }
                }
                Log.d("MX",date)
                val casesMexicoStateList=CasesMexicoStateList(date,listOfMexicoCases)
            //{
            // "d":"[
            // [\"1\",\"Aguascalientes\",\"1434635\",\"01\",\"2071\",\"5002\",\"408\",\"122\", \"Cierre con corte a las 13:00hrs, 25 de Junio de 2020\",\"0\"],
            // [\"2\",\"Baja California\",\"3634868\",\"02\",\"8272\",\"4793\",\"1683\",\"1824\",\"\",\"0\"],
            // [\"3\",\"Baja California Sur\",\"804708\",\"03\",\"1324\",\"2738\",\"386\",\"69\",\"\",\"0\"],
            // [\"4\",\"Campeche\",\"1000617\",\"04\",\"1581\",\"1831\",\"492\",\"175\",\"\",\"0\"],
            // [\"5\",\"Coahuila\",\"3218720\",\"05\",\"3479\",\"12445\",\"3981\",\"190\",\"\",\"0\"],
            // [\"6\",\"Colima\",\"785153\",\"06\",\"459\",\"741\",\"117\",\"58\",\"\",\"0\"],
            // [\"7\",\"Chiapas\",\"5730367\",\"07\",\"4146\",\"2000\",\"798\",\"435\",\"\",\"0\"],
            // [\"8\",\"Chihuahua\",\"3801487\",\"08\",\"2839\",\"3326\",\"1197\",\"610\",\"\",\"0\"],
            // [\"9\",\"Ciudad de México\",\"9018645\",\"09\",\"45125\",\"53988\",\"10173\",\"6116\",\"\",\"0\"],
            // [\"10\",\"Durango\",\"1868996\",\"10\",\"1567\",\"4277\",\"838\",\"132\",\"\",\"0\"],
            // [\"11\",\"Guanajuato\",\"6228175\",\"11\",\"6126\",\"15766\",\"2407\",\"301\",\"\",\"0\"],
            // [\"12\",\"Guerrero\",\"3657048\",\"12\",\"4828\",\"3070\",\"1304\",\"820\",\"\",\"0\"],
            // [\"13\",\"Hidalgo\",\"3086414\",\"13\",\"3579\",\"2922\",\"287\",\"600\",\"\",\"0\"],
            // [\"14\",\"Jalisco\",\"8409693\",\"14\",\"5875\",\"15341\",\"2094\",\"563\",\"\",\"0\"],
            // [\"15\",\"México\",\"17427790\",\"15\",\"32017\",\"32396\",\"10375\",\"3873\",\"\",\"0\"],
            // [\"16\",\"Michoacán\",\"4825401\",\"16\",\"5291\",\"8695\",\"1998\",\"415\",\"\",\"0\"],
            // [\"17\",\"Morelos\",\"2044058\",\"17\",\"2683\",\"2263\",\"400\",\"640\",\"\",\"0\"],
            // [\"18\",\"Nayarit\",\"1288571\",\"18\",\"1487\",\"1687\",\"232\",\"168\",\"\",\"0\"],
            // [\"19\",\"Nuevo León\",\"5610153\",\"19\",\"4580\",\"19272\",\"3060\",\"277\",\"\",\"0\"],
            // [\"20\",\"Oaxaca\",\"4143593\",\"20\",\"4549\",\"2963\",\"1105\",\"513\",\"\",\"0\"],
            // [\"21\",\"Puebla\",\"6604451\",\"21\",\"9014\",\"9150\",\"3925\",\"1101\",\"\",\"0\"],
            // [\"22\",\"Queretaro\",\"2279637\",\"22\",\"1830\",\"2357\",\"200\",\"257\",\"\",\"0\"],
            // [\"23\",\"Quintana Roo\",\"1723259\",\"23\",\"3233\",\"2468\",\"461\",\"543\",\"\",\"0\"],
            // [\"24\",\"San Luis Potosí\",\"2866142\",\"24\",\"2511\",\"6142\",\"963\",\"123\",\"\",\"0\"],
            // [\"25\",\"Sinaloa\",\"3156674\",\"25\",\"7569\",\"5193\",\"2060\",\"1147\",\"\",\"0\"],
            // [\"26\",\"Sonora\",\"3074745\",\"26\",\"6491\",\"4496\",\"4826\",\"572\",\"\",\"0\"],
            // [\"27\",\"Tabasco\",\"2572287\",\"27\",\"9486\",\"9625\",\"2030\",\"970\",\"\",\"0\"],
            // [\"28\",\"Tamaulipas\",\"3650602\",\"28\",\"5074\",\"11354\",\"2544\",\"315\",\"\",\"0\"],
            // [\"29\",\"Tlaxcala\",\"1380011\",\"29\",\"2265\",\"3276\",\"557\",\"283\",\"\",\"0\"],
            // [\"30\",\"Veracruz\",\"8539862\",\"30\",\"9161\",\"5638\",\"2041\",\"1395\",\"\",\"0\"],
            // [\"31\",\"Yucatán\",\"2259098\",\"31\",\"3670\",\"4713\",\"491\",\"368\",\"\",\"0\"],
            // [\"32\",\"Zacatecas\",\"1666426\",\"32\",\"769\",\"2189\",\"150\",\"85\",\"\",\"0\"],
            // [\"33\",\"NACIONAL\",\"127792286\",\"33\",\"202951\",\"262117\",\"63583\",\"25060\",\"\",\"0\"]
            // ]"
            // }

            } catch (e: JSONException){
                e.printStackTrace()
            } catch (ex: Exception){
                ex.printStackTrace()
            }
        }

        return cases
    }
}