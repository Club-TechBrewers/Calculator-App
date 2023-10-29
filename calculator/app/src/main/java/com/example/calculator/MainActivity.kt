package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.calculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false
    var lastEqual: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        tvInput = binding.tvInput

        setContentView(view)
    }

    //add digit in the input from button as view
    fun onDigit(view: View){
        //Toast.makeText(this, "button Clicked", Toast.LENGTH_LONG).show()
        if(lastEqual) {
            onClear(view)
        }
        tvInput?.append((view as Button).text)
        lastNumeric = true
    }

    //clear all input
    fun onClear(view: View){
        tvInput?.text = ""
        lastNumeric=false
        lastDot=false
        lastEqual=false
    }

    //add decimal point
    fun onDecimalPoint(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric = false
            lastDot = true
            lastEqual = false
        }
    }

    //add operator if it is not added after a number
    fun onOperator(view: View){

        //tvInput is a nullable operator. Meaning it can return null value
        //here we are converting tvInput to string type. Now, null cant be converted to string
        //hence we use let{} --> if tvInput.text value exists and is not null value then only
        // we can convert it into string and perform other opertaions
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
                lastEqual= false
            }
        }
    }

    //return false if first value input is - operator and
    //return true if any operator is added further for calculations as input
    private fun isOperatorAdded(value : String): Boolean{
        return if(value.startsWith("-")){
            Log.v("starts with value :","-")
            Toast.makeText(this,"-ve number involved. Clear to continue",Toast.LENGTH_LONG).show()
            true
        }else{
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }

    fun onEquals(view: View){
        if(lastNumeric){
            var tvValue = tvInput?.text.toString()
            var prefix = ""
            lastEqual = true

            try{
                if(tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                if(tvValue.contains("-")){
                    //split string value based on - operator in left number and right number separately
                    val splitValue = tvValue.split("-")

                    // stringvalue.split() creates an array
                    var one = splitValue[0]
                    var two = splitValue[1]


                    if(prefix.isNotEmpty()){
                        one += prefix
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }

                else if(tvValue.contains("+")){
                    //split string value based on + operator in left number and right number separately
                    val splitValue = tvValue.split("+")

                    // stringvalue.split() creates an array
                    var one = splitValue[0]
                    var two = splitValue[1]


                    if(prefix.isNotEmpty()){
                        one += prefix
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }

                else if(tvValue.contains("*")){
                    //split string value based on * operator in left number and right number separately
                    val splitValue = tvValue.split("*")

                    // stringvalue.split() creates an array
                    var one = splitValue[0]
                    var two = splitValue[1]


                    if(prefix.isNotEmpty()){
                        one += prefix
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

                else if(tvValue.contains("/")){
                    //split string value based on / operator in left number and right number separately
                    val splitValue = tvValue.split("/")

                    // stringvalue.split() creates an array
                    var one = splitValue[0]
                    var two = splitValue[1]


                    if(prefix.isNotEmpty()){
                        one += prefix
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String):String{
        var value = result
        if(result.contains(".0")){
            value = value.substring(0, result.length -2)
        }

        return value
    }



}