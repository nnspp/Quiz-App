package com.example.quiz_app

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.appcompat.app.AppCompatActivity
import com.example.quiz_app.databinding.ActivityMainBinding
import com.example.quiz_app.ui.theme.QuizAppTheme


class MainActivity : AppCompatActivity() {

    /*
    Tutorial: https://www.youtube.com/watch?v=pXZR0QiwvrU
this implements Viewbinding rather than @Composable? If thats even comparable..
ViewBinding had to be added in build gradle kts (s line 39)

     */

    private lateinit var binding: ActivityMainBinding

    //Qs ans As+
    private val questions = arrayOf("Beantworte diese Frage mit 1.", "Beantworte diese Frage mit Zwei.", "Beantworte diese Frage mit.. einer Zahl")
    private val options = arrayOf(arrayOf("Erstens", "1", "Eins"), arrayOf("Zwei", "2", "2."), arrayOf("Drei", "n", "3"))
    private val correctAnswers = arrayOf(1, 0, 2)

    //setting up the stats and stuff
    private var currentOptionIndex= 0
    private var currentQuestionIndex = 0
    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    displayQuestion()

                    binding.option1Button.setOnClickListener(){
                        checkAnswer(0)
                    }
                    binding.option2Button.setOnClickListener(){
                        checkAnswer(1)
                    }
                    binding.option3Button.setOnClickListener(){
                        checkAnswer(2)
                    }
                    binding.restartButton.setOnClickListener{
                        restartQuiz()
                    }

                }
            }
        }


    }
    //implementing the Index of correct and falseButton
    private fun correctButtonColor(buttonIndex: Int){
        when(buttonIndex){
            0 -> binding.option1Button.setBackgroundColor(Color.GREEN)
            1 -> binding.option2Button.setBackgroundColor(Color.GREEN)
            2 -> binding.option3Button.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongbuttonColor(buttonIndex: Int){
        when(buttonIndex){
            0 -> binding.option1Button.setBackgroundColor(Color.RED)
            1 -> binding.option2Button.setBackgroundColor(Color.RED)
            2 -> binding.option3Button.setBackgroundColor(Color.RED)
        }
    }

    //theres a reason rbg is being used here? something about xml
    private fun resetButtonColor(){
        binding.option1Button.setBackgroundColor(Color.rgb(50,59,96))
        binding.option2Button.setBackgroundColor(Color.rgb(50,59,96))
        binding.option3Button.setBackgroundColor(Color.rgb(50,59,96))
    }

    private fun showResult(){
        Toast.makeText(this, "Your score: $score out of ${questions.size}", Toast.LENGTH_LONG).show()
        binding.restartButton.isEnabled = true //??
    }

    private fun displayQuestion() { //instead of questions-> option[currentQuestInd oops
        binding.questionText.text = questions[currentQuestionIndex]
        binding.option1Button.text = questions[currentQuestionIndex][0]
        binding.option2Button.text = questions[currentQuestionIndex][1]
        binding.option3Button.text = questions[currentQuestionIndex][2]

        resetButtonColor()
    }

    // stors index value of answer selected by user; then; answer index
    private fun checkAnswer (selectedAnswerIndex: Int){
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex){
            score++
            correctButtonColor(selectedAnswerIndex)
        } else {
            wrongButtonColor(selectedAnswerIndex)
            correctButtonColor(selectedAnswerIndex)
        }
    if (currentQuestionIndex < questions.size -1){
        currentQuestionIndex++
        binding.questionText.postDelayed({displayQuestion()}, 1000)
    } else {
        showResult()
    }
    }
    private fun restartQuiz(){
        currentOptionIndex = 0
        score = 0
        displayQuestion()
        binding.restartButton.isEnabled = false //or either         false.also { binding.restartButton.isEnabled = it }
    }

}
