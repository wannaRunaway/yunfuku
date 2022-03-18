package com.aifubook.book.activity.main.shopdetail

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.aifubook.book.R
import com.aifubook.book.databinding.DialogGradeBinding
import com.aifubook.book.databinding.DialogIdentifyBinding

fun gradeclick(activity: Activity, onclicklistener: View.OnClickListener): AlertDialog {
    var gradeDialog: AlertDialog
//    if (gradeDialog == null) {
    var dialogbuilder = AlertDialog.Builder(activity)
    var view = activity.layoutInflater.inflate(R.layout.dialog_grade, null)
    var dialogBinding = DialogGradeBinding.bind(view)
    var arrayDialogTextView = arrayOf(
        dialogBinding.textviewGrade1,
        dialogBinding.textviewGrade2,
        dialogBinding.textviewGrade3,
        dialogBinding.textviewGrade4,
        dialogBinding.textviewGrade5,
        dialogBinding.textviewGrade6,
        dialogBinding.textviewGrade7,
        dialogBinding.textviewGrade8,
        dialogBinding.textviewGrade9,
        dialogBinding.textviewClose
    )
    for (textview in arrayDialogTextView) {
        textview.setOnClickListener(onclicklistener)
    }
    gradeDialog = dialogbuilder.setView(view).create()
//    }
    gradeDialog!!.show()
    var window = gradeDialog!!.window
    window?.setGravity(Gravity.BOTTOM)
    window?.setWindowAnimations(R.style.grade_dialog_style)
    window?.decorView?.setPadding(0, 0, 0, 0)
    window?.decorView?.setBackgroundColor(ContextCompat.getColor(activity, R.color.white))
    var layoutParams: WindowManager.LayoutParams? = window?.attributes
    if (layoutParams != null) {
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
    }
    window?.attributes = layoutParams
    return gradeDialog
}

fun dialogIdentify(activity: Activity, onclicklistener: View.OnClickListener): AlertDialog {
    var dialogIdentify: AlertDialog
//    if (gradeDialog == null) {
    var dialogbuilder = AlertDialog.Builder(activity)
    var view = activity.layoutInflater.inflate(R.layout.dialog_identify, null)
    var dialogBinding = DialogIdentifyBinding.bind(view)
    var arrayDialogTextView = arrayOf(
        dialogBinding.textviewTeacher,
        dialogBinding.textviewStudent,
        dialogBinding.textviewCloseIdentity
    )
    for (textview in arrayDialogTextView) {
        textview.setOnClickListener(onclicklistener)
    }
    dialogIdentify = dialogbuilder.setView(view).create()
//    }
    dialogIdentify!!.show()
    var window = dialogIdentify!!.window
    window?.setGravity(Gravity.BOTTOM)
    window?.setWindowAnimations(R.style.grade_dialog_style)
    window?.decorView?.setPadding(0, 0, 0, 0)
    window?.decorView?.setBackgroundColor(ContextCompat.getColor(activity, R.color.white))
    var layoutParams: WindowManager.LayoutParams? = window?.attributes
    if (layoutParams != null) {
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
    }
    window?.attributes = layoutParams
    return dialogIdentify
}