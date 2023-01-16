package com.projecthit.cricketscorecounter

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.firebase.analytics.FirebaseAnalytics
import com.projecthit.cricketscorecounter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView

    private var runs = 0
    private var wicket = 0
    private var balls = 0
    private var extras = 0
    private var noBallRun = true
    private var wideRun = true
    private var otherrun = false
    private var selected: Int? = null
    private lateinit var binding: ActivityMainBinding
    private var fbAnalytics: FirebaseAnalytics? = null

    class Batsman{
        var name: String? = "Batsman"
        var balls: Int = 0
        var runs: Int = 0
        var fours: Int = 0
        var sixes: Int = 0

        var strikeRate: Int = 0

        var tvName: TextView? = null
        var tvBalls: TextView? = null
        var tvRuns: TextView? = null
        var tvBoundries: TextView? = null
        var tvStrikeRate: TextView? = null

        fun setData() {
            val boundries: String = fours.toString() + "/" + sixes.toString()
            tvBalls?.text = balls.toString()
            tvRuns?.text = runs.toString()
            tvBoundries?.text = boundries
            strikeRate = (runs.toDouble()*100/balls).toInt()
            tvStrikeRate?.text = strikeRate.toString()
        }

    }

    private var batsman1 = Batsman()
    private var batsman2 = Batsman()
    private var batsman3 = Batsman()
    private var batsman4 = Batsman()
    private var batsman5 = Batsman()
    private var batsman6 = Batsman()
    private var batsman7 = Batsman()
    private var batsman8 = Batsman()
    private var batsman9 = Batsman()
    private var batsman10 = Batsman()
    private var batsman11 = Batsman()

    private val batsmen = arrayOf(batsman1, batsman2, batsman3, batsman4, batsman5, batsman6, batsman7, batsman8, batsman9,
        batsman10, batsman11)
    private var a = 1
    private var b = 2
    private var strike = a


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.batsmanName1.setTextColor(Color.parseColor("#00FF00"))
        fbAnalytics = FirebaseAnalytics.getInstance(this)
        MobileAds.initialize(this)
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        batsman1.tvName = binding.batsmanName1
        batsman1.tvBalls = binding.batsmanBalls1
        batsman1.tvRuns = binding.batsmanRuns1
        batsman1.tvBoundries = binding.batsmanBoundary1
        batsman1.tvStrikeRate = binding.batsmanStrikeRate1

        batsman2.tvName = binding.batsmanName2
        batsman2.tvBalls = binding.batsmanBalls2
        batsman2.tvRuns = binding.batsmanRuns2
        batsman2.tvBoundries = binding.batsmanBoundary2
        batsman2.tvStrikeRate = binding.batsmanStrikeRate2

        highlight()



        binding.batsmanName1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);


        binding.runsRow.visibility = View.INVISIBLE
        binding.eventRow.visibility = View.INVISIBLE
        binding.eventText.visibility = View.INVISIBLE

        binding.startBtn.setOnClickListener {
            binding.runsRow.visibility = View.VISIBLE
            binding.eventRow.visibility = View.VISIBLE
            binding.eventText.visibility = View.VISIBLE
            binding.startBtn.visibility = View.GONE
            binding.editBtnll.visibility = View.VISIBLE
            binding.editBtn.visibility = View.VISIBLE
            binding.menuBtnll.visibility = View.VISIBLE
            binding.menuBtn.visibility = View.VISIBLE

            binding.menuBtn.setOnClickListener {
                if (binding.extraMenu.isVisible){
                    binding.extraMenu.visibility = View.GONE
                }else{
                    binding.extraMenu.visibility = View.VISIBLE
                }

                binding.noBallRuns.setOnCheckedChangeListener { compoundButton, changed ->
                    noBallRun = changed
                }

                binding.wideRuns.setOnCheckedChangeListener { compoundButton, changed ->
                    wideRun = changed
                }

                binding.changeStrike.setOnClickListener {
                    if(wicket<10){
                        if (strike==a){
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            strike=b
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                        }else{
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                            strike=a
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                        }
                        updateScore()
                    }

                }

            }

            binding.teamName.setOnClickListener {
                binding.menuBtn.setImageResource(R.drawable.tick)
                binding.editBtn.visibility = View.GONE
                binding.textInputLayout.visibility = View.VISIBLE
                binding.editText.visibility = View.VISIBLE
                binding.menuBtn.setOnClickListener {
                    it.hideKeyboard()
                    binding.teamName.text = binding.editText.text
                    binding.editText.text = null
                    binding.textInputLayout.visibility = View.GONE
                    binding.editText.visibility = View.GONE
                    binding.editBtn.visibility = View.VISIBLE
                    binding.menuBtn.setImageResource(R.drawable.menu)
                    binding.menuBtn.setOnClickListener {
                        if (binding.extraMenu.isVisible){
                            binding.extraMenu.visibility = View.GONE
                        }else{
                            binding.extraMenu.visibility = View.VISIBLE
                        }

                        binding.noBallRuns.setOnCheckedChangeListener { compoundButton, changed ->
                            noBallRun = changed
                        }

                        binding.wideRuns.setOnCheckedChangeListener { compoundButton, changed ->
                            wideRun = changed
                        }

                        binding.changeStrike.setOnClickListener {
                            if(wicket<10){
                                if (strike==a){
                                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    strike=b
                                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                                }else{
                                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                    strike=a
                                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                                }
                                updateScore()
                            }
                        }

                    }
                }
            }


            binding.editBtn.setOnClickListener {
                binding.extraMenu.visibility = View.GONE
                if (selected!=null){
                    binding.menuBtn.setImageResource(R.drawable.tick)
                    binding.editBtn.visibility = View.GONE
                    binding.textInputLayout.visibility = View.VISIBLE
                    binding.editText.visibility = View.VISIBLE

                    binding.menuBtn.setOnClickListener {
                        batsmen[selected!!-1].name = binding.editText.text.toString()
                        batsmen[selected!!-1].tvName?.text = batsmen[selected!!-1].name
                        binding.editText.text = null
                        binding.textInputLayout.visibility = View.GONE
                        binding.editText.visibility = View.GONE
                        binding.editText.visibility = View.GONE
                        binding.menuBtn.setImageResource(R.drawable.menu)
                        binding.editBtn.visibility = View.VISIBLE
                        editEverything(selected!!)
                        updateScore()
                        it.hideKeyboard()
                    }

                }else{
                    Toast.makeText(this, "Select Batsman", Toast.LENGTH_SHORT).show()
                }
            }

        }




        batsman1.tvName?.setOnClickListener {
            editEverything(1)
        }
        batsman2.tvName?.setOnClickListener {
            editEverything(2)
        }




        runsBinding()




        binding.noBall.setOnClickListener {
            if (noBallRun){
                extras++
                runs++
                binding.eventRow.visibility = View.INVISIBLE
                binding.eventText.visibility = View.INVISIBLE

                balls--
            }else{
                runs += 0
                binding.eventRow.visibility = View.INVISIBLE
                binding.eventText.visibility = View.INVISIBLE
                balls--

            }



        }

        binding.wide.setOnClickListener {
            otherrun = true
            if (wideRun){
                extras++
                runs++
                binding.eventRow.visibility = View.INVISIBLE
                binding.eventText.visibility = View.INVISIBLE

                balls--

            }else{
                runs += 0
                binding.eventRow.visibility = View.INVISIBLE
                binding.eventText.visibility = View.INVISIBLE
                balls--

            }

        }

        binding.legBye.setOnClickListener {
            otherrun = true
            binding.eventRow.visibility = View.INVISIBLE
            binding.eventText.visibility = View.INVISIBLE


        }

        binding.overthrow.setOnClickListener {
            otherrun=true
            binding.eventRow.visibility = View.INVISIBLE
            binding.eventText.visibility = View.INVISIBLE
            balls--

        }


        binding.out.setOnClickListener {
            if(wicket<10){
                wicket++
                balls++


                when(wicket){
                    1 -> {
                        binding.batter3.visibility = View.VISIBLE
                        batsman3.tvName = binding.batsmanName3
                        batsman3.tvBalls = binding.batsmanBalls3
                        batsman3.tvRuns = binding.batsmanRuns3
                        batsman3.tvBoundries = binding.batsmanBoundary3
                        batsman3.tvStrikeRate = binding.batsmanStrikeRate3
                        batsman3.tvName?.setOnClickListener {
                            editEverything(3)
                        }
                    }
                    2 -> {
                        binding.batter4.visibility = View.VISIBLE
                        batsman4.tvName = binding.batsmanName4
                        batsman4.tvBalls = binding.batsmanBalls4
                        batsman4.tvRuns = binding.batsmanRuns4
                        batsman4.tvBoundries = binding.batsmanBoundary4
                        batsman4.tvStrikeRate = binding.batsmanStrikeRate4
                        batsman4.tvName?.setOnClickListener {
                            editEverything(4)
                        }
                    }
                    3 -> {
                        binding.batter5.visibility = View.VISIBLE
                        batsman5.tvName = binding.batsmanName5
                        batsman5.tvBalls = binding.batsmanBalls5
                        batsman5.tvRuns = binding.batsmanRuns5
                        batsman5.tvBoundries = binding.batsmanBoundary5
                        batsman5.tvStrikeRate = binding.batsmanStrikeRate5
                        batsman5.tvName?.setOnClickListener {
                            editEverything(5)
                        }
                    }
                    4 -> {
                        binding.batter6.visibility = View.VISIBLE
                        batsman6.tvName = binding.batsmanName6
                        batsman6.tvBalls = binding.batsmanBalls6
                        batsman6.tvRuns = binding.batsmanRuns6
                        batsman6.tvBoundries = binding.batsmanBoundary6
                        batsman6.tvStrikeRate = binding.batsmanStrikeRate6
                        batsman6.tvName?.setOnClickListener {
                            editEverything(6)
                        }
                    }
                    5 -> {
                        binding.batter7.visibility = View.VISIBLE
                        batsman7.tvName = binding.batsmanName7
                        batsman7.tvBalls = binding.batsmanBalls7
                        batsman7.tvRuns = binding.batsmanRuns7
                        batsman7.tvBoundries = binding.batsmanBoundary7
                        batsman7.tvStrikeRate = binding.batsmanStrikeRate7
                        batsman7.tvName?.setOnClickListener {
                            editEverything(7)
                        }
                    }
                    6 -> {
                        binding.batter8.visibility = View.VISIBLE
                        batsman8.tvName = binding.batsmanName8
                        batsman8.tvBalls = binding.batsmanBalls8
                        batsman8.tvRuns = binding.batsmanRuns8
                        batsman8.tvBoundries = binding.batsmanBoundary8
                        batsman8.tvStrikeRate = binding.batsmanStrikeRate8
                        batsman8.tvName?.setOnClickListener {
                            editEverything(8)
                        }
                    }
                    7 -> {
                        binding.batter9.visibility = View.VISIBLE
                        batsman9.tvName = binding.batsmanName9
                        batsman9.tvBalls = binding.batsmanBalls9
                        batsman9.tvRuns = binding.batsmanRuns9
                        batsman9.tvBoundries = binding.batsmanBoundary9
                        batsman9.tvStrikeRate = binding.batsmanStrikeRate9
                        batsman9.tvName?.setOnClickListener {
                            editEverything(9)
                        }
                    }
                    8 -> {
                        binding.batter10.visibility = View.VISIBLE
                        batsman10.tvName = binding.batsmanName10
                        batsman10.tvBalls = binding.batsmanBalls10
                        batsman10.tvRuns = binding.batsmanRuns10
                        batsman10.tvBoundries = binding.batsmanBoundary10
                        batsman10.tvStrikeRate = binding.batsmanStrikeRate10
                        batsman10.tvName?.setOnClickListener {
                            editEverything(10)
                        }
                    }
                    9 -> {
                        binding.batter11.visibility = View.VISIBLE
                        batsman11.tvName = binding.batsmanName11
                        batsman11.tvBalls = binding.batsmanBalls11
                        batsman11.tvRuns = binding.batsmanRuns11
                        batsman11.tvBoundries = binding.batsmanBoundary11
                        batsman11.tvStrikeRate = binding.batsmanStrikeRate11
                        batsman11.tvName?.setOnClickListener {
                            editEverything(11)
                        }
                    }
                    else -> {
                        Toast.makeText(this, "All Out", Toast.LENGTH_SHORT).show()
                    }
                }

                if (wicket<10){
                    if (strike==a){
                        unhighlight(a)
                        a=wicket+2
                        if (balls%6==0){
                            strike=b
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                        }else{
                            strike=a
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                        }
                    }else{
                        unhighlight(b)
                        b=wicket+2
                        if (balls%6==0){
                            strike=a
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                        }else{
                            strike=b
                            batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                        }
                    }

                    updateScore()
                    highlight()
                }else{
                    if (strike==a){
                        unhighlight(a)
                        batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        binding.scBat1.text = ""
                    }
                    else{
                        unhighlight(b)
                        batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                        binding.scBat2.text = ""
                    }
                    binding.score.text = runs.toString()
                    binding.runsRow.visibility = View.INVISIBLE
                    binding.eventRow.visibility = View.INVISIBLE
                    binding.eventText.visibility = View.INVISIBLE

                }

            }else{
                Toast.makeText(this, "All Out", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun highlight(){

        if (wicket<10){
            batsmen[a-1].tvName?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[a-1].tvStrikeRate?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[a-1].tvBoundries?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[a-1].tvRuns?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[a-1].tvBalls?.setTextColor(Color.parseColor("#0fa619"))

            batsmen[b-1].tvName?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[b-1].tvStrikeRate?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[b-1].tvBoundries?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[b-1].tvRuns?.setTextColor(Color.parseColor("#0fa619"))
            batsmen[b-1].tvBalls?.setTextColor(Color.parseColor("#0fa619"))
        }else{
            Toast.makeText(this, "All Out", Toast.LENGTH_SHORT).show()
        }

    }

    private fun unhighlight(no: Int){
        val textColor = binding.nameField.currentTextColor
        batsmen[no-1].tvName?.setTextColor(textColor)
        batsmen[no-1].tvStrikeRate?.setTextColor(textColor)
        batsmen[no-1].tvBoundries?.setTextColor(textColor)
        batsmen[no-1].tvRuns?.setTextColor(textColor)
        batsmen[no-1].tvBalls?.setTextColor(textColor)
        batsmen[no-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    private fun updateScore(){
        if (wicket<10){
            binding.score.text = runs.toString() + "/" + wicket.toString()
            val over = balls/6
            val rem = balls%6
            val ov = over.toDouble() + rem.toDouble()/6
            val rr = runs.toDouble()/ov
            val rr2 = String.format("%.2f", rr).toDouble()
            binding.runRate.text = "RR:  " + rr2.toString()
            binding.overs.text = over.toString() + "." + rem
            if (strike==a){
                binding.scBat1.text = batsmen[a-1].name + "  " + batsmen[a-1].runs + "(" + batsmen[a-1].balls + ")" + " *"
                binding.scBat2.text = batsmen[b-1].name + "  " + batsmen[b-1].runs + "(" + batsmen[b-1].balls + ")" + "  "
            }else{
                binding.scBat1.text = batsmen[a-1].name + "  " + batsmen[a-1].runs + "(" + batsmen[a-1].balls + ")" + "  "
                binding.scBat2.text = batsmen[b-1].name + "  " + batsmen[b-1].runs + "(" + batsmen[b-1].balls + ")" + " *"
            }
        }else{
            if (strike==a){
                binding.scBat2.text = batsmen[b-1].name + "  " + batsmen[b-1].runs + "(" + batsmen[b-1].balls + ")" + "  "
            }else{
                binding.scBat1.text = batsmen[a-1].name + "  " + batsmen[a-1].runs + "(" + batsmen[a-1].balls + ")" + "  "
            }
        }



    }

    private fun runsBinding(){
        binding.oneRun.setOnClickListener {
            runs++
            balls++
            binding.eventRow.visibility = View.VISIBLE
            binding.eventText.visibility = View.VISIBLE
            if(!otherrun){
                batsmen[strike-1].runs++
                batsmen[strike-1].balls++
                batsmen[strike-1].setData()
            }
            otherrun = false

            if (balls%6!=0 || balls==0){
                if (strike==a){
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=b
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }else{
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=a
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }
            }

            updateScore()
        }

        binding.twoRun.setOnClickListener {
            runs += 2
            balls++
            binding.eventRow.visibility = View.VISIBLE
            binding.eventText.visibility = View.VISIBLE

            if(!otherrun){
                batsmen[strike-1].runs += 2
                batsmen[strike-1].balls++
                batsmen[strike-1].setData()
            }
            otherrun = false


            if (balls%6==0){
                if (strike==a){
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=b
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }else{
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=a
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }
            }

            updateScore()
        }

        binding.threeRun.setOnClickListener {
            runs += 3
            balls++
            binding.eventRow.visibility = View.VISIBLE
            binding.eventText.visibility = View.VISIBLE

            if(!otherrun){
                batsmen[strike-1].runs += 3
                batsmen[strike-1].balls++
                batsmen[strike-1].setData()
            }
            otherrun = false



            if (balls%6!=0){
                if (strike==a){
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=b
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }else{
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=a
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }
            }


            updateScore()
        }



        binding.fourRun.setOnClickListener {
            runs += 4
            balls++
            binding.eventRow.visibility = View.VISIBLE
            binding.eventText.visibility = View.VISIBLE

            if(!otherrun){
                batsmen[strike-1].runs += 4
                batsmen[strike-1].fours += 1
                batsmen[strike-1].balls++
                batsmen[strike-1].setData()
            }
            otherrun = false



            if (balls%6==0){
                if (strike==a){
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=b
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }else{
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=a
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }
            }

            updateScore()

        }

        binding.sixRun.setOnClickListener {
            runs += 6
            balls++
            binding.eventRow.visibility = View.VISIBLE
            binding.eventText.visibility = View.VISIBLE


            if(!otherrun){
                batsmen[strike-1].runs += 6
                batsmen[strike-1].sixes += 1
                batsmen[strike-1].balls++
                batsmen[strike-1].setData()
            }
            otherrun = false


            if (balls%6==0){
                if (strike==a){
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=b
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }else{
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=a
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }
            }

            updateScore()

        }

        binding.zeroRun.setOnClickListener {
            runs += 0
            balls++
            binding.eventRow.visibility = View.VISIBLE
            binding.eventText.visibility = View.VISIBLE

            if(!otherrun){
                batsmen[strike-1].runs += 0
                batsmen[strike-1].balls++
                batsmen[strike-1].setData()
            }
            otherrun = false



            if (balls%6==0){
                if (strike==a){
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=b
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }else{
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    strike=a
                    batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                }
            }

            updateScore()

        }
    }

    private fun editEverything(i: Int){
        val original = batsman1.tvBalls!!.background
        if (selected==i){
                batsmen[i-1].tvName?.background = original
                binding.menuBtn.setOnClickListener {
                    if (binding.extraMenu.isVisible){
                        binding.extraMenu.visibility = View.GONE
                    }else{
                        binding.extraMenu.visibility = View.VISIBLE
                    }

                    binding.noBallRuns.setOnCheckedChangeListener { compoundButton, changed ->
                        noBallRun = changed
                    }

                    binding.wideRuns.setOnCheckedChangeListener { compoundButton, changed ->
                        wideRun = changed
                    }

                    binding.changeStrike.setOnClickListener {
                        if(wicket<10){
                            if (strike==a){
                                batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                strike=b
                                batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                            }else{
                                batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                                strike=a
                                batsmen[strike-1].tvName?.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bat, 0, 0, 0);
                            }
                            updateScore()
                        }


                }
            }

            selected=null
        }else{
            batsmen[i-1].tvName!!.setBackgroundColor(Color.parseColor("#919bab"))
            if (selected!=null || selected==i){
                batsmen[selected!!-1].tvName!!.background = original
                selected=null
            }
            selected=i
        }


    }
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }



}