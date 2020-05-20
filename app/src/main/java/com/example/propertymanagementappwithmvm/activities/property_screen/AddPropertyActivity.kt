package com.example.propertymanagementappwithmvm.activities.property_screen

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.propertymanagementappwithmvm.R
import com.example.propertymanagementappwithmvm.app.Config
import com.example.propertymanagementappwithmvm.databinding.ActivityPropertyAddressBinding
import com.example.propertymanagementappwithmvm.helpers.SessionManager
import com.example.propertymanagementappwithmvm.models.PropertyAddResponse
import com.example.propertymanagementappwithmvm.models.PropertyDatabase
import com.example.propertymanagementappwithmvm.viewmodels.PropertyAddViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.ByteArrayOutputStream
import java.io.IOException

class AddPropertyActivity : AppCompatActivity(), PropertyAddViewModel.PropertyViewModelInterface {

    //Request Code Constant
    private val REQUEST_CODE_CAMERA = 101
    private val REQUEST_CODE_GALLERY = 102

    lateinit var addPropertyBinding : ActivityPropertyAddressBinding

    lateinit var propertyAddViewModel : PropertyAddViewModel

    lateinit var databaseReference : DatabaseReference

    //lateinit var databaseReference : DatabaseR
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_property_address)

        //set up data binding
        addPropertyBinding = DataBindingUtil.setContentView(this, R.layout.activity_property_address)

        //initialize the view model for this activity
        propertyAddViewModel = ViewModelProviders.of(this).get(PropertyAddViewModel::class.java)
        addPropertyBinding.propertyAddViewModel = propertyAddViewModel

        //initialize the database
        databaseReference = FirebaseDatabase.getInstance().getReference(Config.FIREBASE_DATABASE_REALTIME)



        propertyAddViewModel.setupListener(this)



    }

    override fun onError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess(propertyAddReposnse: LiveData<PropertyAddResponse>) {
        propertyAddReposnse.observe(this, object : Observer<PropertyAddResponse> {
            override fun onChanged(t: PropertyAddResponse?) {
                Toast.makeText(this@AddPropertyActivity, t!!.msg[0], Toast.LENGTH_SHORT).show()

                /*save property + image into the database*//*
                var propertyDatabase = PropertyDatabase("", propertyAddViewModel.address,
                    propertyAddViewModel.city, propertyAddViewModel.state_or_province, propertyAddViewModel.country,
                    propertyAddViewModel.zip_or_postal_code, propertyAddViewModel.status, propertyAddViewModel.multiple_units,
                    propertyAddViewModel.purchase_price,propertyAddViewModel.exclude_from_dashboard, propertyAddViewModel.mortgage,
                    propertyAddViewModel.image_path, propertyAddViewModel.user_id, propertyAddViewModel.user_type, propertyAddViewModel.latitude,
                    propertyAddViewModel.longitude)

                var key = databaseReference.push().key
                propertyDatabase.key = key

                databaseReference.child(key!!).setValue(propertyDatabase)*/

                finish()
                startActivity(Intent(this@AddPropertyActivity, ListPropertyActivity::class.java))

            }

        })
    }

    /*
        Since the layout is binded, we need to access the isChecked() from the addPropertyBinding.
        So, let's just handle it from the activity and save the result to the view model
     */
    override fun handleCheckBox(view: View) {
        when(view.id){
            R.id.checkbox_multiple_units ->{
                if(addPropertyBinding.checkboxMultipleUnits.isChecked){
                    Toast.makeText(this@AddPropertyActivity, "check box multiple unit is enabled ", Toast.LENGTH_SHORT).show()
                    propertyAddViewModel.multiple_units = "yes"
                }
                else{
                    Toast.makeText(this@AddPropertyActivity, "check box multiple unit is disabled ", Toast.LENGTH_SHORT).show()
                    propertyAddViewModel.multiple_units = "no"
                }
            }
            R.id.checkbox_exclude_from_dashboard -> {
                if(addPropertyBinding.checkboxExcludeFromDashboard.isChecked) propertyAddViewModel.exclude_from_dashboard = "yes"
                else propertyAddViewModel.exclude_from_dashboard= "no"
            }
            else -> {
                if(addPropertyBinding.checkboxMortgage.isChecked) propertyAddViewModel.mortgage = "yes"
                else propertyAddViewModel.mortgage = "no"
            }
        }
    }

    override fun setUser() {
        //set some fields in the property view model
        var sessionManager = SessionManager(this)
        var user = sessionManager.getUser()

        Log.d("aaaaa", "inside set user")
        propertyAddViewModel.user_id = user.userid
        propertyAddViewModel.user_type = user.usertype
    }

    override fun handleOpenCamera() {
        //request camera permission
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {

                    //check if all permissions granted
                    if(report!!.areAllPermissionsGranted()){
                        takePhotoFromCamera()
                        Toast.makeText(applicationContext, "Permissions Granted", Toast.LENGTH_SHORT).show()
                    }

                    //check for permanent denial of any permission
                    if(report.isAnyPermissionPermanentlyDenied){
                        // permission is denied permanently then navigate user to setting
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }

            })
            .onSameThread()
            .check()

    }

    private fun takePhotoFromCamera(){
        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE_GALLERY){
            if(data != null){
                var contentUri = data.data

                try{
                    //save the image in the database
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentUri)
                    var imagePath = getImageString(bitmap)

                    Toast.makeText(applicationContext, "Gallery", Toast.LENGTH_SHORT).show()

                }catch (e : IOException){
                    e.printStackTrace()
                    Toast.makeText(applicationContext, e.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
        else if(requestCode == REQUEST_CODE_CAMERA){
            val bitmap = data!!.extras!!.get("data") as Bitmap
            var imagePath = getImageString(bitmap)

            propertyAddViewModel.image_path = imagePath

            var uri = Uri.parse(imagePath)
            addPropertyBinding.imageView.visibility = View.VISIBLE
            addPropertyBinding.imageView.setImageURI(uri)


            Toast.makeText(applicationContext, "Camera", Toast.LENGTH_SHORT).show()

        }
    }

    fun getImageString(imgBitmap: Bitmap): String {
        val bytes = ByteArrayOutputStream()
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

        //insert the image to the gallery and create a thumbnail for it
        val path = MediaStore.Images.Media.insertImage(this.getContentResolver(), imgBitmap, "title", null)
        return path
    }
}
