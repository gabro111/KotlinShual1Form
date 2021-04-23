package com.example.shualeduricardvalidation

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class MainActivity : AppCompatActivity() {
    lateinit var buyNowButton: Button

    private lateinit var cardType: EditText;
    private lateinit var cardTypeError: TextView;

    private lateinit var cardName: EditText;
    private lateinit var cardNameError: TextView;


    private lateinit var cardNumber: EditText;
    private lateinit var cardNumberError: TextView;
    private lateinit var cardCvvError: TextView;


    private lateinit var cardMonth: EditText;
    private lateinit var cardYear: EditText;
    private lateinit var cardCvv: EditText;
    private lateinit var theMainLayout: LinearLayout;


    var correctCreditCard:Boolean=false;
    var cardNumberLength:Int = 0;
    var ccvLength:Int=0;
    var correctCcvCard:Boolean=false;



     fun validateDate(inputMonth: EditText, inputYear: EditText):Boolean{
         if(inputMonth.text.isEmpty() || inputYear.text.isEmpty()){
             return false;
         }
        val currentDateTime = LocalDateTime.now()
        val splitDateArray = currentDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)).split(
            "/"
        )

         //1-dge 0-tve 2-weli
         return when {
             splitDateArray[2].toInt() > inputYear.text.toString().toInt() -> {
                 false
             }
             splitDateArray[2].toInt() < inputYear.text.toString().toInt() -> {
                 true
             }
             splitDateArray[2].toInt() == inputYear.text.toString().toInt() -> {
                 splitDateArray[0].toInt() < inputMonth.text.toString().toInt()
             }
             else -> false
         }

     }
    public fun validateCard ():Boolean{


        return false;
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        buyNowButton = findViewById(R.id.buyNowButton);

        cardType = findViewById(R.id.cardType);
        cardTypeError = findViewById(R.id.cardTypeError);
        theMainLayout = findViewById(R.id.linearLayout);

        cardName=findViewById(R.id.cardName);
        cardNameError = findViewById(R.id.cardNameError);

        cardNumber = findViewById(R.id.cardNumber);
        cardNumberError = findViewById(R.id.cardNumberError);
        cardCvvError = findViewById(R.id.cardCvvError);

        cardMonth = findViewById(R.id.cardMonth);
        cardYear = findViewById(R.id.cardYear);
        cardCvv = findViewById(R.id.cardCvv);


        class MinMaxFilter() : InputFilter {
            private var intMin: Int = 0
            private var intMax: Int = 0
            constructor(minValue: String, maxValue: String) : this() {
                this.intMin = Integer.parseInt(minValue)
                this.intMax = Integer.parseInt(maxValue)
            }
            override fun filter(
                source: CharSequence,
                start: Int,
                end: Int,
                dest: Spanned,
                dStart: Int,
                dEnd: Int
            ): CharSequence? {
                try {

                    val input = Integer.parseInt(dest.toString() + source.toString())
                    return if (isInRange(intMin, intMax, input)) {
                        null
                    }else{
                        ""
                    }
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
                return ""
            }
            private fun isInRange(a: Int, b: Int, c: Int): Boolean {
                return if (b > a) c in a..b else c in b..a
            }
        }




        cardType.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                when (s.toString().toLowerCase()) {
                    "visa" -> {
                        cardTypeError.text = ""
                        cardNumberLength = 16
                        ccvLength = 3;
                    }
                    "ajax" -> {
                        cardTypeError.text = ""
                        cardNumberLength = 15
                        ccvLength = 4;
                    }
                    "mastercard" -> {
                        cardTypeError.text = ""
                        cardNumberLength = 16
                        ccvLength = 3
                    }
                    "" -> {
                        cardTypeError.text = "input is empty";
                        cardNumberLength = 0
                        ccvLength = 0
                    }
                    else -> {
                        cardTypeError.text = "That Type Of Card Doesn't Exist"
                        cardNumberLength = 0
                        ccvLength = 0
                    }
                }

            }
        })

        cardName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.toString().isEmpty()) {
                    cardNameError.text = "Full Name Is Empty"
                } else {
                    cardNameError.text = ""
                }

            }
        })

        cardNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (cardNumberLength == 0) {
                    cardNumberError.text = "Card Type Not Specified"
                    correctCreditCard = false
                } else {
                    if (s.toString().length == cardNumberLength) {
                        cardNumberError.text = ""
                        correctCreditCard = true
                    } else {
                        cardNumberError.text = "Pls Fix Input Of The Card"
                        correctCreditCard = false
                    }

                }


            }
        })
        cardCvv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (ccvLength == 0) {
                    cardCvvError.text = "Card Type Not Specified"
                    correctCcvCard = false

                } else {
                    if (s.toString().length == ccvLength) {
                        cardCvvError.text = ""
                        correctCcvCard = true

                    } else {
                        cardCvvError.text = "Pls Fix Input Of The CCV"
                        correctCcvCard = false
                    }

                }


            }
        })






        cardMonth.filters = arrayOf<InputFilter>(MinMaxFilter("1", "12"))
        cardYear.filters = arrayOf<InputFilter>(MinMaxFilter("1", "99"))


        buyNowButton.setOnClickListener{
            //2389434//
            //correctCreditCard icneba false sanam ar chaiwereba (visa,mastercard,ajax) da credit card numberi
            //cardCvv.text.toString().
            if(validateDate(cardMonth, cardYear) && correctCreditCard && correctCcvCard){
                Toast.makeText(this, "Form Was Correct", Toast.LENGTH_LONG).show();
                theMainLayout.forEach { view ->  if(view is EditText){
                    view.text.clear()
                }  }
                cardYear.text.clear();
                cardMonth.text.clear();
                cardCvv.text.clear();
            }else{

                Toast.makeText(this, "Please Correct Mistakes In form", Toast.LENGTH_SHORT).show();

            }
        }
    }



}