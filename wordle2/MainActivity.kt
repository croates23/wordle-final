package com.example.wordleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import android.widget.TextView
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.wordle2.FourLetterWordList
import com.example.wordle2.R
import com.example.wordle2.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)


        val guessesField = findViewById<TextView>(R.id.guesses_view)
        val editText = findViewById<EditText>(R.id.user_guess)
        val showAnswer = findViewById<TextView>(R.id.show_answer)
        val failView = findViewById<TextView>(R.id.info_view)
        val debugView = findViewById<TextView>(R.id.guess_word)
        val wordToGuess = FourLetterWordList.getRandomFourLetterWord()

        val button = findViewById<Button>(R.id.button)
        var guessLimit=3
        var checkGuessDisplay=""
        var userInputDisplay=""

        button.setOnClickListener{
            if (guessLimit==0){
                finish()
                overridePendingTransition(0,0)
                startActivity(intent)
                overridePendingTransition(0,0)
            }
            counter++
            if (counter>=3){
                Toast.makeText(
                    it.context,
                    "your out of chances!",
                    Toast.LENGTH_SHORT
                ).show()

            }
            if (guessLimit!=0){
                var isCorrect=false
                val editTextString = editText.text.toString().uppercase(Locale.getDefault())
                if(editTextString.length==4){
                    failView.text=""
                    var result= checkGuess(editTextString,wordToGuess)
                    checkGuess(editTextString,wordToGuess)

                    if(checkGuessDisplay==""){
                        checkGuessDisplay=result
                        userInputDisplay = editTextString
                    }
                    else{
                        checkGuessDisplay = checkGuessDisplay + result
                        userInputDisplay = userInputDisplay + editTextString
                    }
                    guessesField.text = checkGuessDisplay
                    debugView.text = userInputDisplay

                    if (result == "OOOO"){
                        failView.text = "You got  it!! \nAnswer $wordToGuess "
                        button.text="start over"
                        isCorrect = true
                        guessLimit=0
                    }
                    else{
                        guessLimit--

                        if (guessLimit==0 && !isCorrect){
                            failView.text = "All out of chances!"
                            button.text = "Reset"
                        }
                    }
                }
                else
                    failView.text= "please enter four letters...or else"
            }



        }
    }


    private fun checkGuess(guess: String, wordToGuess: String) : String {
        var result = ""
        for (i in 0..3) {
            if (guess[i] == wordToGuess[i]) {
                result += "O"
            }
            else if (guess[i] in wordToGuess) {
                result += "+"
            }
            else {
                result += "X"
            }
        }
        return result
    }


}