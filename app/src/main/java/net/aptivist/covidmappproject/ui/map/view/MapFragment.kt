package net.aptivist.covidmappproject.ui.map.view
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.cardview_cases_map.*
import net.aptivist.covidmappproject.R
import net.aptivist.covidmappproject.data.repository.persistance.PreferenceHelper
import net.aptivist.covidmappproject.databinding.FragmentMapBindingImpl
import net.aptivist.covidmappproject.helpers.PREF_LATITUDE
import net.aptivist.covidmappproject.helpers.PREF_LONGITUDE
import net.aptivist.covidmappproject.helpers.USPostalAbbreviationStates
import net.aptivist.covidmappproject.ui.advancedsearch.viewmodel.AdvSearchViewModel
import net.aptivist.covidmappproject.ui.map.viewmodel.MapViewModel
import org.koin.android.ext.android.inject
import java.time.LocalTime

class MapFragment : Fragment(), OnMapReadyCallback , GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener, View.OnClickListener{
    companion object {
        val MULTIPLE_PERMISSIONS = 10
        private val REQUEST_CHECK_SETTINGS:Int=50
        private val DEFAULT_ZOOM: Float = 14f
        private val TAG:String="MapFragment"
        private val COVID:String="COVID:"
    }
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var locationCallback:LocationCallback
    private  var lastKnownLocation: Location? =null
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var marker: Marker? = null
    private var locationPermissionGranted: Boolean = false
    private lateinit var pHelper : PreferenceHelper

   //private lateinit var mapViewModel: MapViewModel

    var listPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION)

    private val mapViewModel : MapViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        pHelper = PreferenceHelper.newInstance(requireContext())!!
        val binding = DataBindingUtil.inflate<FragmentMapBindingImpl>(inflater, R.layout.fragment_map,container,false)

        //mapViewModel= ViewModelProvider(this).get(MapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_map, container, false)

        binding.mapViewModel=mapViewModel



        initializeMap(binding.root,savedInstanceState)

        mapViewModel.place.observe(viewLifecycleOwner, Observer {
            tvPlace.text = it
        })

        mapViewModel.date.observe(viewLifecycleOwner, Observer {
            tvDate.text = it
        })

        mapViewModel.casesNumber.observe(viewLifecycleOwner, Observer {
            tvCasesNumber.text = it
        })

        downloadCovidCases()

        return binding.root
    }

    private fun downloadCovidCases(){
        mapViewModel.getCasesMexicoStates()
        var  MexicoState = ""
        mapViewModel.casesMexicoStates.observe(requireActivity(), Observer {
            var casesStates = it
            casesStates?.results?.forEach{
                MexicoState += "Results Mexico States: ${it.nameState} \nTotal Cases: ${it.totalCases}\n Total Positivo: ${it.positive}\n" +
                        "Total Sospechosos: ${it.suspect} \n Total Deaths: ${it.death} \n"
            }
            Log.d(TAG,MexicoState )
        })
        var state = ""
        mapViewModel.getCasesUSAStates()
        mapViewModel.casesUSAStates.observe(requireActivity(), Observer {
            val casesStatesList = it
            casesStatesList?.forEach {
                when (it.state) {
                    "AL"-> it.state = USPostalAbbreviationStates.AL.abbreviation
                    "AK" -> it.state = USPostalAbbreviationStates.AK.abbreviation
                    "AS" -> it.state = USPostalAbbreviationStates.AS.abbreviation
                    "AZ"-> it.state = USPostalAbbreviationStates.AZ.abbreviation
                    "AR"-> it.state = USPostalAbbreviationStates.AR.abbreviation
                    "CA"-> it.state = USPostalAbbreviationStates.CA.abbreviation
                    "CO"-> it.state = USPostalAbbreviationStates.CO.abbreviation
                    "CT"-> it.state = USPostalAbbreviationStates.CT.abbreviation
                    "DE"-> it.state = USPostalAbbreviationStates.DE.abbreviation
                    "FL"-> it.state = USPostalAbbreviationStates.FL.abbreviation
                    "GA"-> it.state = USPostalAbbreviationStates.GA.abbreviation
                    "GU"-> it.state = USPostalAbbreviationStates.GU.abbreviation
                    "HI"-> it.state = USPostalAbbreviationStates.HI.abbreviation
                    "ID"-> it.state = USPostalAbbreviationStates.ID.abbreviation
                    "IL"-> it.state = USPostalAbbreviationStates.IL.abbreviation
                    "IN"-> it.state = USPostalAbbreviationStates.IN.abbreviation
                    "IA"-> it.state = USPostalAbbreviationStates.IA.abbreviation
                    "KS"-> it.state = USPostalAbbreviationStates.KS.abbreviation
                    "KY"-> it.state = USPostalAbbreviationStates.KY.abbreviation
                    "LA"-> it.state = USPostalAbbreviationStates.LA.abbreviation
                    "ME"-> it.state = USPostalAbbreviationStates.ME.abbreviation
                    "MD"-> it.state = USPostalAbbreviationStates.MD.abbreviation
                    "MA"-> it.state = USPostalAbbreviationStates.MA.abbreviation
                    "MI"-> it.state = USPostalAbbreviationStates.MI.abbreviation
                    "MN"-> it.state = USPostalAbbreviationStates.MN.abbreviation
                    "MS"-> it.state = USPostalAbbreviationStates.MS.abbreviation
                    "MO"-> it.state = USPostalAbbreviationStates.MO.abbreviation
                    "MT"-> it.state = USPostalAbbreviationStates.MT.abbreviation
                    "NE"-> it.state = USPostalAbbreviationStates.NE.abbreviation
                    "NV"-> it.state = USPostalAbbreviationStates.NV.abbreviation
                    "NH"-> it.state = USPostalAbbreviationStates.NH.abbreviation
                    "NJ"-> it.state = USPostalAbbreviationStates.NJ.abbreviation
                    "NM"-> it.state = USPostalAbbreviationStates.NM.abbreviation
                    "NY"-> it.state = USPostalAbbreviationStates.NY.abbreviation
                    "NC"-> it.state = USPostalAbbreviationStates.NC.abbreviation
                    "ND"-> it.state = USPostalAbbreviationStates.ND.abbreviation
                    "OH"-> it.state = USPostalAbbreviationStates.OH.abbreviation
                    "OK"-> it.state = USPostalAbbreviationStates.OK.abbreviation
                    "OR"-> it.state = USPostalAbbreviationStates.OR.abbreviation
                    "PA"-> it.state = USPostalAbbreviationStates.PA.abbreviation
                    "PR"-> it.state = USPostalAbbreviationStates.PR.abbreviation
                    "RI"-> it.state = USPostalAbbreviationStates.RI.abbreviation
                    "SC"-> it.state = USPostalAbbreviationStates.SC.abbreviation
                    "SD"-> it.state = USPostalAbbreviationStates.SD.abbreviation
                    "TN"-> it.state = USPostalAbbreviationStates.TN.abbreviation
                    "TX"-> it.state = USPostalAbbreviationStates.TX.abbreviation
                    "UT"-> it.state = USPostalAbbreviationStates.UT.abbreviation
                    "VT"-> it.state = USPostalAbbreviationStates.VT.abbreviation
                    "VA"-> it.state = USPostalAbbreviationStates.VA.abbreviation
                    "VI"-> it.state = USPostalAbbreviationStates.VI.abbreviation
                    "WA"-> it.state = USPostalAbbreviationStates.WA.abbreviation
                    "WV"-> it.state = USPostalAbbreviationStates.WV.abbreviation
                    "WI"-> it.state = USPostalAbbreviationStates.WI.abbreviation
                    "WY"-> it.state = USPostalAbbreviationStates.WY.abbreviation
                }
                state += "Results USA States: ${it.state}\n Positive: ${it.positive} \n Negative: ${it.negative} \n" +
                        "Recovered: ${it.recovered} \n Total: ${it.totalTestResults} \n Date: ${it.dateChecked}\n"
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.d( COVID+LocalTime.now().toString() , state)
            }
        })
        mapViewModel.getGlobalStatus()
        var global = ""
        mapViewModel.casesGlobalStatus.observe(requireActivity(), Observer{
            val globalStatus = it
            globalStatus?.results?.forEach{
                global += "Results Global Status Total Cases: ${it.total_cases}\n Total Recovered: ${it.total_recovered}\n" +
                        "Total Unresolved: ${it.total_unresolved} \n Total Deaths: ${it.total_deaths} \n"
            }
            Log.d(COVID,global )
        })

        mapViewModel.getCasesCountries()
        var  countries = ""
        mapViewModel.casesCountries.observe(requireActivity(), Observer {
            var casesCountries = it
            casesCountries?.countryitems?.forEach{
                countries += "Results Countries: ${it.title} \nTotal Cases: ${it.total_cases}\n Total Recovered: ${it.total_recovered}\n" +
                        "Total Unresolved: ${it.total_unresolved} \n Total Deaths: ${it.total_deaths} \n"
            }
            Log.d(COVID,countries )
        })
    }

    private fun initializeMap(root:View,savedInstanceState: Bundle?){

        mapView = root.findViewById(R.id.mapView) as MapView
        mapView.onCreate(savedInstanceState)
        try {
            MapsInitializer.initialize(requireActivity().applicationContext)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        mapView.getMapAsync(this)

        mapView.onResume() // needed to get the map to display immediately
    }
    private fun initializetCurrentLocation(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                if (locationResult == null) {
                    return
                }
                for (location in locationResult.locations) {
                    if (location != null) {
                        lastKnownLocation = location

                        if (fusedLocationProviderClient != null) {
                            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                        }
                    }
                }
            }
        }
    }
    override fun onMapReady(_googleMap: GoogleMap) {
       googleMap=_googleMap
        try {
            if(AppCompatDelegate.getDefaultNightMode()!=AppCompatDelegate.MODE_NIGHT_YES){
                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.map_style))
            }else{
                googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(requireActivity(), R.raw.map_style_night))
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(TAG, "Can't find style. Error: ", e)
        }
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMapClickListener(this)

        updateLocationUI()
    }
    private fun updateLocationUI() {
        try {
            if(!locationPermissionGranted) {
                checkPermissions()
            }else{
               setMapUISettings()
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Exception: " + e.message)
        }
    }
    private fun checkPermissions() {
        var result: Int
        val listPermissionsNeeded: MutableList<String> = ArrayList()
        for (permission in listPermissions) {
            result = ActivityCompat.checkSelfPermission(activity?.applicationContext!!, permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toTypedArray(),
                MULTIPLE_PERMISSIONS
            )
        }else {
            locationPermissionGranted = true
            setMapUISettings()
        }
    }
    @SuppressLint("MissingPermission")
    private fun setMapUISettings(){
        initializetCurrentLocation()
        googleMap.isMyLocationEnabled = true
        googleMap.getUiSettings().setMyLocationButtonEnabled(true)
        googleMap.getUiSettings().isZoomControlsEnabled=true
        googleMap.getUiSettings().isCompassEnabled=true
        googleMap.setPadding(150,0,0,500 )//left, top, right, bottom
        createLocationRequest()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissionsList: Array<String>, grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty()) {
                    var permissionsDenied = ""
                    val listPermissionsDenied: MutableList<String> = ArrayList()
                    for (permission in permissionsList) {
                        if (grantResults[permissionsList.indexOf(permission)] != PackageManager.PERMISSION_GRANTED) {
                            listPermissionsDenied.add(permission)
                            permissionsDenied += "\n" + permission
                            Log.d(TAG, "Permissions denied: "+permission)
                        }
                    }
                    if (listPermissionsDenied.isEmpty()) {
                        locationPermissionGranted = true
                        setMapUISettings()
                    }
                }
            }
        }
    }
    private fun getDeviceLocation() {
        try {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    lastKnownLocation=location
                    val position = LatLng(
                        location.latitude,
                        location.longitude)
                    googleMap.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(position,
                            DEFAULT_ZOOM
                        ))

                    marker = googleMap.addMarker(
                        MarkerOptions()
                            .position(position)
                            .title("Posicion actual")
                            .draggable(true)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location))
                            .snippet("Estoy aqui"))
                }else{
                    if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
                    ) {

                    }
                    else {
                        val locationManager = requireActivity().getApplicationContext()
                            .getSystemService(Context.LOCATION_SERVICE) as LocationManager
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f,
                            object : LocationListener {
                                override fun onLocationChanged(_location: Location?) {
                                    if(lastKnownLocation==null){
                                        if (_location != null) {
                                            lastKnownLocation = _location
                                            val _position = LatLng(lastKnownLocation!!.latitude, lastKnownLocation!!.longitude)
                                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(_position,
                                                DEFAULT_ZOOM
                                            ))
                                            marker = googleMap.addMarker(
                                                MarkerOptions()
                                                    .position(_position)
                                                    .title("Posicion actual")
                                                    .draggable(true)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.current_location))
                                                    .snippet("Estoy aqui"))
                                            locationManager.removeUpdates(this)
                                        }
                                    }
                                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                                }
                                override fun onStatusChanged(
                                    provider: String?,
                                    status: Int,
                                    extras: Bundle?
                                ) {
                                    fusedLocationProviderClient.removeLocationUpdates(
                                        locationCallback)
                                }
                                override fun onProviderEnabled(provider: String?) {
                                }
                                override fun onProviderDisabled(provider: String?) {
                                    createLocationRequest()
                                }
                            })
                    }
                }
                saveLastLocation(lastKnownLocation?.latitude.toString(),lastKnownLocation?.longitude.toString())
            }
        } catch (e: Exception) {
            Log.e(TAG,"Exception:  "+ e.message)
        }
    }
    protected fun createLocationRequest() {
        if (locationPermissionGranted) {
            locationRequest = LocationRequest.create()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 5000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .setAlwaysShow(true)

            val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnSuccessListener { locationSettingsResponse ->
                val states = locationSettingsResponse.locationSettingsStates
                if (states.isLocationPresent) {
                    getDeviceLocation()
                }
            }
            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    try {
                        startIntentSenderForResult(exception.resolution.intentSender,
                            REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        Log.e(TAG, "Exception createLocationRequest IntentSender.SendIntentException: "+ sendEx)
                    }
                }
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                createLocationRequest()
            }
        }
    }
    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMapClick(p0: LatLng?) {
    }

    override fun onClick(chip: View?) {
        when (chip?.id) {
            R.id.chip_confirmed -> {
            }
            R.id.chip_recovered -> {
            }
            R.id.chip_deaths -> {

            }
        }
    }
    private fun saveLastLocation(latitude: String, longitude :String){
        pHelper.setString(PREF_LATITUDE, latitude)
        pHelper.setString(PREF_LONGITUDE, longitude)
        //Log.d("Location: ", "$PREF_LATITUDE  $PREF_LONGITUDE")
        //Toast.makeText(requireContext(), "Latitude: ${pHelper.getString(PREF_LATITUDE) ?:""} Longitude: ${pHelper.getString(PREF_LONGITUDE) ?:""}", Toast.LENGTH_SHORT).show()
    }
}
