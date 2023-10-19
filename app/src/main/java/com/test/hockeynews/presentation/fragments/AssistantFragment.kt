package com.test.hockeynews.presentation.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.test.hockeynews.R

class AssistantFragment : Fragment() {

    private var team1Score = 0
    private var team2Score = 0
    private var remainingTime = 30L // 30 seconds for the timer
    private lateinit var countDownTimer: CountDownTimer
    private var isTimerRunning = false

    private lateinit var buttonTeam1Score: TextView
    private lateinit var buttonTeam2Score: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_assistant, container, false)

        // Find the necessary UI elements
        val editTextTeam1 = view.findViewById<EditText>(R.id.editTextTeam1)
        val editTextTeam2 = view.findViewById<EditText>(R.id.editTextTeam2)
        val team1TextView = view.findViewById<TextView>(R.id.team1)
        val team2TextView = view.findViewById<TextView>(R.id.team2)
        val buttonSaveTeam1 = view.findViewById<TextView>(R.id.buttonSaveTeam1)
        val buttonSaveTeam2 = view.findViewById<TextView>(R.id.buttonSaveTeam2)
        buttonTeam1Score = view.findViewById(R.id.buttonTeam1Score)
        buttonTeam2Score = view.findViewById(R.id.buttonTeam2Score)
        val textViewScore = view.findViewById<TextView>(R.id.textViewScore)
        val textViewTime = view.findViewById<TextView>(R.id.textViewTime)
        val rulesText = view.findViewById<TextView>(R.id.rules_assistant)

        rulesText.setOnClickListener {
            showRulesAlertDialog()
        }

        buttonSaveTeam1.setOnClickListener {
            val team1Name = editTextTeam1.text.toString().trim()
            if (team1Name.isNotBlank()) {
                team1TextView.text = team1Name
                editTextTeam1.isEnabled = false // Disable editing after saving
            } else {
                Toast.makeText(requireContext(), "Введите название Команды 1", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonSaveTeam2.setOnClickListener {
            val team2Name = editTextTeam2.text.toString().trim()
            if (team2Name.isNotBlank()) {
                team2TextView.text = team2Name
                editTextTeam2.isEnabled = false // Disable editing after saving
            } else {
                Toast.makeText(requireContext(), "Enter team name 2", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        buttonTeam1Score.setOnClickListener {
            if (isTimerRunning) {
                incrementTeam1Score()
                updateScore(textViewScore)
            }
        }

        buttonTeam2Score.setOnClickListener {
            if (isTimerRunning) {
                incrementTeam2Score()
                updateScore(textViewScore)
            }
        }

        updateScore(textViewScore)
        textViewTime.text = "Timer: ${remainingTime}s"

        countDownTimer = object : CountDownTimer(remainingTime * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished / 1000
                textViewTime.text = "Timer: ${remainingTime}s"
            }

            override fun onFinish() {
                textViewTime.text = "Time is over!"
                showResultsAlertDialog()
            }
        }

        if (!isTimerRunning) {
            countDownTimer.start()
            isTimerRunning = true
            buttonTeam1Score.isEnabled = true
            buttonTeam2Score.isEnabled = true
        }

        return view
    }

    override fun onPause() {
        super.onPause()
        stopTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseTimer()
    }

    private fun stopTimer() {
        if (isTimerRunning) {
            countDownTimer.cancel()
            isTimerRunning = false
        }
    }

    private fun releaseTimer() {
        stopTimer()
    }

    private fun incrementTeam1Score() {
        team1Score++
    }

    private fun incrementTeam2Score() {
        team2Score++
    }

    private fun updateScore(textViewScore: TextView) {
        val scoreText = "Score: $team1Score - $team2Score"
        textViewScore.text = scoreText
    }

    private fun showResultsAlertDialog() {
        val team1TextView = requireView().findViewById<TextView>(R.id.team1)
        val team2TextView = requireView().findViewById<TextView>(R.id.team2)
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Game over!")
            .setMessage("Results:\n${team1TextView.text}: $team1Score\n${team2TextView.text}: $team2Score")
            .setPositiveButton("Restart") { dialog, _ ->
                dialog.dismiss()
                restartGame()
            } // Dismiss the dialog and restart the game
            .setCancelable(false)
            .create()
        alertDialog.show()
    }

    private fun showRulesAlertDialog() {
        countDownTimer.cancel() // Pause the timer
        val rulesMessage =
            "Rules of the game:\nName the teams and give them goals in 30 seconds"
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Rules of the game")
            .setMessage(rulesMessage)
            .setPositiveButton("Restart") { _, _ ->
                restartGame()
            }
            .setCancelable(false)
            .create()
        alertDialog.show()
    }

    private fun restartGame() {
        team1Score = 0
        team2Score = 0
        remainingTime = 30L
        isTimerRunning = false
        val editTextTeam1 = requireView().findViewById<EditText>(R.id.editTextTeam1)
        val editTextTeam2 = requireView().findViewById<EditText>(R.id.editTextTeam2)
        editTextTeam1.isEnabled = true
        editTextTeam2.isEnabled = true

        val textViewScore = requireView().findViewById<TextView>(R.id.textViewScore)
        val textViewTime = requireView().findViewById<TextView>(R.id.textViewTime)
        updateScore(textViewScore)
        textViewTime.text = "Timer: ${remainingTime}s"

        if (!isTimerRunning) {
            countDownTimer.start()
            isTimerRunning = true
            buttonTeam1Score.isEnabled = true
            buttonTeam2Score.isEnabled = true
        }
    }
}