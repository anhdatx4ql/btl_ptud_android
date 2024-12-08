package com.example.btl_ptud_android.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.btl_ptud_android.R
import com.example.btl_ptud_android.databinding.ActivityPlayQuizBinding
import com.example.btl_ptud_android.databinding.QuizRecycleRowBinding
import com.example.btl_ptud_android.databinding.ScoreDialogBinding
import com.example.btl_ptud_android.models.Questions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlayQuizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayQuizBinding
    private lateinit var questionsList: ArrayList<Questions>
    private lateinit var firebaseRef: DatabaseReference
    var currentQuestion = 0
    var categoryId :String = ""
    var userScore = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //lay du lieu id tu ben MainActivity

        categoryId = intent.getStringExtra("category_id").toString()

        // tao instant
        firebaseRef = FirebaseDatabase.getInstance().getReference("questions")
        questionsList = arrayListOf<Questions>()

        Log.d("LoadQuestionFromFirebase", "Category ID: $categoryId")

        LoadQuestionFromFirebase()
    }

    // xu ly khi chon dap an
    private fun handleAnswerSelection(question: Questions, selectedAnswer: String) {
        // Kiểm tra câu trả lời của người dùng
        binding.txtIsCorrect.visibility = View.GONE
        binding.txtIsNotCorrect.visibility = View.GONE


        var isCorrect = true; //question.answerTrue.name == selectedAnswer

        if (question.answerTrue != selectedAnswer){
            isCorrect = false
        }


        // Cập nhật giao diện theo kết quả
        if (isCorrect) {
            binding.txtIsCorrect.visibility = View.VISIBLE

            if (selectedAnswer == "A") {
                btnDisableAll()

            } else if (selectedAnswer == "B") {
                btnDisableAll()

            } else if (selectedAnswer == "C") {
                btnDisableAll()
            } else if (selectedAnswer == "D") {
                btnDisableAll()
            }

            Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show()
            // Tăng điểm nếu cần thiết
            userScore += 1
        } else {
            binding.txtIsNotCorrect.visibility = View.VISIBLE

            if (selectedAnswer == "A") {
                binding.btnAnswerA.setBackgroundColor(resources.getColor(R.color.red))
                btnDisableAll()
            } else if (selectedAnswer == "B") {
                binding.btnAnswerB.setBackgroundColor(resources.getColor(R.color.red))
                btnDisableAll()
            } else if (selectedAnswer == "C") {
                binding.btnAnswerC.setBackgroundColor(resources.getColor(R.color.red))
                btnDisableAll()
            } else if (selectedAnswer == "D") {
                binding.btnAnswerD.setBackgroundColor(resources.getColor(R.color.red))
                btnDisableAll()
            }
                Toast.makeText(
                    this,
                    "Wrong! Correct answer: ${question.answerTrue}",
                    Toast.LENGTH_SHORT
                ).show()


            }

            // ấn next để chuyển sang câu hỏi tiếp theo nếu còn
            binding.btnNext.setOnClickListener {
                binding.txtIsCorrect.visibility = View.GONE
                binding.txtIsNotCorrect.visibility = View.GONE
                if (currentQuestion < questionsList.size - 1) {
                    currentQuestion++
                    updateUIWithQuestions(questionsList) // Cập nhật UI với câu hỏi tiếp theo
                } else {
                    // Nếu hết câu hỏi, thông báo kết quả
                    // tao dialog thong bao kqua.
                    finishQuizActivity()
                }
            }
        }

    private fun finishQuizActivity() {
        val totalQuestions = questionsList.size
        val score = (userScore.toFloat() / totalQuestions.toFloat() * 100).toInt()

        val dialog = ScoreDialogBinding.inflate(layoutInflater)
        dialog.apply {
            scoreProgress.progress = score
            txtTotalScore.text = "$score%"
            txtTotalScore.setTextColor(resources.getColor(R.color.green))
            txtSoCauDung.text = "Bạn đã làm đúng $userScore/$totalQuestions câu hỏi!"
            btnFinish.setOnClickListener {
                finish()
            }
        }

        AlertDialog.Builder(this)
            .setView(dialog.root)
            .setCancelable(false)
            .show()
    }

    private fun btnDisableAll() {
        binding.apply {
            btnAnswerA.isClickable = false
            btnAnswerC.isClickable = false
            btnAnswerD.isClickable = false
            btnAnswerB.isClickable = false
        }
    }

    private fun btnEnableAll() {
        binding.apply {
            btnAnswerA.isClickable = true
            btnAnswerC.isClickable = true
            btnAnswerD.isClickable = true
            btnAnswerB.isClickable = true
        }
    }


    private fun updateUIWithQuestions(questionsList: ArrayList<Questions>) {
            if (questionsList.isNotEmpty()) {
                btnEnableAll()
                // tra lai dap an ve mau ban dau
                binding.btnAnswerA.setBackgroundColor(resources.getColor(R.color.light_green))
                binding.btnAnswerB.setBackgroundColor(resources.getColor(R.color.light_green))
                binding.btnAnswerC.setBackgroundColor(resources.getColor(R.color.light_green))
                binding.btnAnswerD.setBackgroundColor(resources.getColor(R.color.light_green))

                binding.apply {
                    txtNumberQuestion.text =
                        "Question ${currentQuestion + 1} / ${questionsList.size}"
                    questionProgress.progress = ((currentQuestion + 1) * 100) / questionsList.size
                    txtQuestionTitle.text = questionsList[currentQuestion].title.toString()
                    btnAnswerA.text = questionsList[currentQuestion].answerA.toString()
                    btnAnswerB.text = questionsList[currentQuestion].answerB.toString()
                    btnAnswerC.text = questionsList[currentQuestion].answerC.toString()
                    btnAnswerD.text = questionsList[currentQuestion].answerD.toString()
                    btnNext.setOnClickListener {

                        if(!binding.btnAnswerA.isActivated && !binding.btnAnswerB.isActivated && !binding.btnAnswerC.isActivated && !binding.btnAnswerD.isActivated){
                            Toast.makeText(this@PlayQuizActivity, "Please select an answer", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }else{
                            if (currentQuestion < questionsList.size - 1) {
                                currentQuestion++

                            }
                        }
                    }
                    //Xử lý sự kiện click cho các nút trả lời
                    btnAnswerA.setOnClickListener {
                        handleAnswerSelection(
                            questionsList[currentQuestion],
                            "A"
                        )
                    }
                    btnAnswerB.setOnClickListener {
                        handleAnswerSelection(
                            questionsList[currentQuestion],
                            "B"
                        )
                    }
                    btnAnswerC.setOnClickListener {
                        handleAnswerSelection(
                            questionsList[currentQuestion],
                            "C"
                        )
                    }
                    btnAnswerD.setOnClickListener {
                        handleAnswerSelection(
                            questionsList[currentQuestion],
                            "D"
                        )
                    }
                }
            } else {
                // Nếu danh sách câu hỏi trống, hiển thị thông báo
                Toast.makeText(this, "No questions available", Toast.LENGTH_SHORT).show()
                Log.e("updateUIWithQuestions", "Question list is empty.")
            }

        }


        private fun LoadQuestionFromFirebase() {
            firebaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (snapData in snapshot.children) {

                        val category_ID = snapData.child("category_ID").value.toString()
                        val id = snapData.child("id").value.toString()
                        val title = snapData.child("title").value.toString()
                        val answerA = snapData.child("answerA").value.toString()
                        val answerB = snapData.child("answerB").value.toString()
                        val answerC = snapData.child("answerC").value.toString()
                        val answerD = snapData.child("answerD").value.toString()
                        val sort_order = snapData.child("sort_order").value.toString().toInt()
                        Log.d("LoadQuestionFromFirebase", "Category ID 2: $categoryId")

                        // Lấy và chuyển đổi answerTrue
                        val answerTrueString = snapData.child("answerTrue").value.toString()
//                        val answerTrue = when (answerTrueString) {
//                            "A" -> 1
//                            "B" -> 2
//                            "C" -> 3
//                            "D" -> 4
//                            else -> {
//                                Log.e("LoadQuestion", "Invalid answerTrue value: $answerTrueString")
//                                -1
//                            }
//                        }

                        //add question vao list
                        if (category_ID == categoryId) {
                            val model = Questions(
                                category_ID, id, title, answerA,
                                answerB, answerC, answerD, answerTrueString, sort_order
                            )
                            Log.d("answer",answerTrueString)
                            questionsList.add(model)
                        }
                    }
                    if (questionsList.isNotEmpty()) {
                        updateUIWithQuestions(questionsList)
                        Log.d("updateUIWithQuestions", "Questions list size: ${questionsList.size}")
                    } else {
                        Toast.makeText(
                            this@PlayQuizActivity,
                            "No questions available",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@PlayQuizActivity, error.message, Toast.LENGTH_SHORT).show()
                }

            })
        }

}