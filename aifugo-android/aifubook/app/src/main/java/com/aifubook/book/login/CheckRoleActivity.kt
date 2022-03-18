package com.aifubook.book.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.aifubook.book.R
import com.aifubook.book.activity.main.MainActivity
import com.aifubook.book.base.IntentKey
import com.aifubook.book.base.ShareKey
import com.aifubook.book.databinding.ActivityCheckBinding
import com.aifubook.book.databinding.ActivityCheckRoleBinding
import com.aifubook.book.utils.SharedPreferencesUtil
import com.aifubook.book.utils.StatusBarCompat
import java.lang.StringBuilder

class CheckRoleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckRoleBinding
    private lateinit var grade: String
    private lateinit var teacherorstudent: String
    private lateinit var arrayDialogTextView: Array<TextView>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckRoleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        StatusBarCompat.translucentStatusBar(this)
        binding.textviewParent.setOnClickListener {
            teacherorstudent = "家长"
            binding.textviewParent.setTextColor(ContextCompat.getColor(this, R.color.red_F64A33))
            binding.textviewParent.background = ContextCompat.getDrawable(this ,R.drawable.grade_dialog_red)
            binding.textviewTeacher.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.textviewTeacher.background = ContextCompat.getDrawable(this ,R.drawable.grade_dialog)
        }
        binding.textviewTeacher.setOnClickListener {
            teacherorstudent = "教师"
            binding.textviewTeacher.setTextColor(ContextCompat.getColor(this, R.color.red_F64A33))
            binding.textviewTeacher.background = ContextCompat.getDrawable(this ,R.drawable.grade_dialog_red)
            binding.textviewParent.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.textviewParent.background = ContextCompat.getDrawable(this ,R.drawable.grade_dialog)
        }
        arrayDialogTextView = arrayOf(
            binding.textviewGrade1,
            binding.textviewGrade2,
            binding.textviewGrade3,
            binding.textviewGrade4,
            binding.textviewGrade5,
            binding.textviewGrade6,
            binding.textviewGrade7,
            binding.textviewGrade8,
            binding.textviewGrade9
        )
        for (textview in arrayDialogTextView) {
            textview.setOnClickListener(gradeclick)
        }
        grade = "一年级"
        teacherorstudent = "家长"
        binding.textviewConfirm.setOnClickListener {
            SharedPreferencesUtil.put(this, ShareKey.GRADE, grade)
            SharedPreferencesUtil.put(this, ShareKey.TEACHERORSTUDENT, teacherorstudent)
            var intent = Intent()
            intent.putExtra(IntentKey.GRADE, grade)
            intent.putExtra(ShareKey.TEACHERORSTUDENT, teacherorstudent)
            intent.setClass(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    var gradeclick:View.OnClickListener = View.OnClickListener {
        grade = (it as TextView).text.toString()
        var position = 0
        for (index in arrayDialogTextView.indices){
            if (it.id == arrayDialogTextView[index].id){
                position = index
            }
        }
        for (textview in arrayDialogTextView){
            textview.setTextColor(ContextCompat.getColor(this, R.color.black))
            textview.background = ContextCompat.getDrawable(this ,R.drawable.grade_dialog)
        }
        arrayDialogTextView[position].setTextColor(ContextCompat.getColor(this, R.color.red_F64A33))
        arrayDialogTextView[position].background = ContextCompat.getDrawable(this ,R.drawable.grade_dialog_red)
    }

    override fun onBackPressed() {

    }
}