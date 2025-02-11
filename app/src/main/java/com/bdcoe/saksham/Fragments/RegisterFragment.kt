package com.bdcoe.saksham.Fragments


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.bdcoe.saksham.Network.Clients.BdcoeClient
import com.bdcoe.saksham.Network.ServiceGenerator
import com.bdcoe.saksham.POJOs.RegisterResult
import com.bdcoe.saksham.R
import kotlinx.android.synthetic.main.fragment_register.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : Fragment() {

//    private lateinit var client: SiClient
    private lateinit var client: BdcoeClient

    private val MALE = 0
    private val FEMALE = 1
    private var currentSex = MALE

    private lateinit var c1: CheckBox
    private lateinit var c2: CheckBox
    private lateinit var c3: CheckBox
    private lateinit var c4: CheckBox
    private lateinit var c5: CheckBox
    private lateinit var c6: CheckBox
    private lateinit var c7: CheckBox
    private lateinit var c8: CheckBox
    private lateinit var c9: CheckBox
    private lateinit var c10: CheckBox
    private lateinit var c11: CheckBox
    private lateinit var c12: CheckBox
    private lateinit var c13: CheckBox
    private lateinit var c14: CheckBox
    private lateinit var c15: CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        client = ServiceGenerator.createBdcoeService(BdcoeClient::class.java)
        c1 = view.findViewById(R.id.athletics)
        c2 = view.findViewById(R.id.chess)
        c3 = view.findViewById(R.id.badminton)
        c4 = view.findViewById(R.id.volleyball)
        c5 = view.findViewById(R.id.carrom)
        c6 = view.findViewById(R.id.table_tennis)
        c7 = view.findViewById(R.id.basketball)
        c8 = view.findViewById(R.id.tug_of_war)
        c9 = view.findViewById(R.id.kho_kho)
        c10 = view.findViewById(R.id.cricket)
        c11 = view.findViewById(R.id.kabaddi)
        c12 = view.findViewById(R.id.power_lifting)
        c13 = view.findViewById(R.id.football)
        c14 = view.findViewById(R.id.pool)
        c15 = view.findViewById(R.id.obstacle_race)

        register_gender_radio_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.register_male -> {
                    changeGames(MALE)
                    currentSex = MALE
                }
                R.id.register_female -> {
                    changeGames(FEMALE)
                    currentSex = FEMALE
                }
            }
        }

        register_button.setOnClickListener {

            val name = register_name.text.toString()
            val studentNo = register_student_no.text.toString()
            val contactNumber = register_conatct_no.text.toString()
            val branch = register_branch.selectedItem.toString()
            val year = register_year.selectedItem.toString()
            val genderId = register_gender_radio_group.checkedRadioButtonId
            var sex: String = ""
            when (genderId) {
                R.id.register_male -> sex = "1"
                R.id.register_female -> sex = "0"
            }
            val hostlerId = register_hostler_days_radio_group.checkedRadioButtonId
            var hostler: String = ""
            when (hostlerId) {
                R.id.register_hostler -> hostler = "1"
                R.id.register_day_scholar -> hostler = "0"
            }

            if (name.isNotEmpty()) {
                if (studentNo.length == 7) {
                    if (contactNumber.length == 10) {
                        if (isSportSelected()) {
                            var interest = ""

                            if (c1.isChecked) interest = c1.text.toString() + ","
                            if (c2.isChecked) interest = interest + c2.text.toString() + ","
                            if (c3.isChecked) interest = interest + c3.text.toString() + ","
                            if (c4.isChecked) interest = interest + c4.text.toString() + ","
                            if (c5.isChecked) interest = interest + c5.text.toString() + ","
                            if (c6.isChecked) interest = interest + c6.text.toString() + ","
                            if (c7.isChecked) interest = interest + c7.text.toString() + ","
                            if (c8.isChecked) interest = interest + c8.text.toString() + ","
                            if (c9.isChecked) interest = interest + c9.text.toString() + ","
                            if (c10.isEnabled && c10.isChecked) interest = interest + c10.text.toString() + ","
                            if (c11.isEnabled && c11.isChecked) interest = interest + c11.text.toString() + ","
                            if (c12.isEnabled && c12.isChecked) interest = interest + c12.text.toString() + ","
                            if (c13.isEnabled && c13.isChecked) interest = interest + c13.text.toString() + ","
                            if (c14.isEnabled && c14.isChecked) interest = interest + c14.text.toString() + ","
                            if (c15.isEnabled && c15.isChecked) interest = interest + c15.text.toString() + ","

                            interest = interest.substring(0, interest.length - 1)

                            try {
                                val waitSnackbar = Snackbar.make(register_root, "Registering...", Snackbar.LENGTH_INDEFINITE)
                                waitSnackbar.show()
                                val NAME = "Name"
                                val ST_NO = "StudentNo"
                                val NUMBER = "ContactNumber"
                                val MALE = "Gender"
                                val HOSTLER = "Hosteler"
                                val BRANCH = "Branch"
                                val YEAR = "Year"
                                val INTEREST = "SportsInterested"

                                val json = JSONObject()
                                json.put(NAME, name)
                                json.put(ST_NO, studentNo)
                                json.put(NUMBER, contactNumber)
                                json.put(BRANCH, branch)
                                json.put(YEAR, year)
                                json.put(MALE, sex)
                                json.put(HOSTLER, hostler)
                                json.put(INTEREST, interest)

                                val JSON = MediaType.parse("application/json; charset=utf-8")
                                val body = RequestBody.create(JSON, json.toString())


                                val call = callRegisterUser("1",name,studentNo,sex,contactNumber,branch,year,hostler,interest)
                                try {
                                    register_button.isEnabled = false
                                    call.enqueue(object : Callback<RegisterResult> {
                                        override fun onFailure(call: Call<RegisterResult>?, t: Throwable?) {
                                            try {
                                                register_button.isEnabled = true
                                                waitSnackbar.dismiss()
                                                Snackbar.make(register_root, "Registration Failed", Snackbar.LENGTH_LONG).show()
                                            } catch (ex: Exception) {
                                            }
                                        }

                                        override fun onResponse(call: Call<RegisterResult>?, response: Response<RegisterResult>?) {
                                            register_button.isEnabled = true
                                            val result = response?.body()?.result
                                            if (result==1L) {
                                                try {
                                                    waitSnackbar.dismiss()
                                                    Snackbar.make(register_root, "Registered", Snackbar.LENGTH_LONG).show()
                                                } catch (ex: Exception) {
                                                }
                                            } else {
                                                try {
                                                    waitSnackbar.dismiss()
                                                    Snackbar.make(register_root, "Already Registered", Snackbar.LENGTH_LONG).show()
                                                } catch (ex: Exception) {
                                                }
                                            }
                                        }
                                    })
                                } catch (ex: Exception) {
                                    register_button.isEnabled = true
                                    waitSnackbar.dismiss()
                                    Snackbar.make(register_root, "Registration Failed", Snackbar.LENGTH_LONG).show()
                                    Log.d("Registration", "Failed", ex)
                                }
                            } catch (e: Exception) {
                                Snackbar.make(register_root, "Registration Failed", Snackbar.LENGTH_LONG).show()
                                Log.d("Registration", "Failed", e)
                            }

                        } else {
                            Snackbar.make(register_root, "No sport is selected", Snackbar.LENGTH_LONG).show()
                        }
                    } else {
                        Snackbar.make(register_root, "Wrong Phone Number.Do not add +91", Snackbar.LENGTH_LONG).show()
                    }
                } else {
                    Snackbar.make(register_root, "Wrong Student Number", Snackbar.LENGTH_LONG).show()
                }
            } else {
                Snackbar.make(register_root, "Fill your name", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private fun isSportSelected(): Boolean {
        var selected: Boolean = false
        when (currentSex) {
            MALE -> {
                if (c1.isChecked || c2.isChecked || c3.isChecked || c4.isChecked || c5.isChecked || c6.isChecked
                        || c7.isChecked || c8.isChecked || c9.isChecked || c10.isChecked || c11.isChecked || c12.isChecked
                        || c13.isChecked || c14.isChecked || c15.isChecked)
                    selected = true
            }
            FEMALE -> {
                if (c1.isChecked || c2.isChecked || c3.isChecked || c4.isChecked || c5.isChecked || c6.isChecked
                        || c7.isChecked || c8.isChecked || c9.isChecked)
                    selected = true
            }
        }
        return selected
    }

    private fun changeGames(gender: Int) {
        if (gender == MALE) {
            cricket.isEnabled = true
            football.isEnabled = true
            obstacle_race.isEnabled = true
            kabaddi.isEnabled = true
            pool.isEnabled = true
            power_lifting.isEnabled = true
        } else {
            cricket.isEnabled = false
            football.isEnabled = false
            obstacle_race.isEnabled = false
            kabaddi.isEnabled = false
            pool.isEnabled = false
            power_lifting.isEnabled = false
        }
        Log.d("changeGames", gender.toString())
    }

    private fun callRegisterUser(dataflow:String,name:String,studentNo:String,sex:String,contactNumber:String,branch:String,year:String,hostler:String,interest:String): Call<RegisterResult> = client.postRegister(dataflow,name,studentNo,sex,contactNumber,branch,year,hostler,interest)

}
