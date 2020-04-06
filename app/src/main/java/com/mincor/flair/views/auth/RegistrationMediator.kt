package com.mincor.flair.views.auth

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import com.mincor.flair.R
import com.mincor.flair.utils.Consts.FONT_SIZE_12
import com.mincor.flair.utils.Consts.FONT_SIZE_14
import com.mincor.flair.utils.Consts.ROUND_CORNERS_16
import com.mincor.flair.utils.dip8
import com.rasalexman.flaircore.animation.LinearAnimator
import com.rasalexman.flaircore.ext.*
import com.rasalexman.flaircore.patterns.mediator.Mediator
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * Created by Alex on 22.01.2017.
 */

class RegistrationMediator : Mediator() {
    override fun createLayout(context: Context): View = RegisterUI().createView(AnkoContext.create(context, this))

    private var nameET: EditText? = null
    private var emailET: EditText? = null
    private var passwordET: EditText? = null
    private var repeatePasswordET: EditText? = null

    internal fun registerUser() {
        log { "START LOGIN IN " }

        // Reset errors.
        nameET!!.error = null
        emailET!!.error = null
        passwordET!!.error = null
        repeatePasswordET!!.error = null

        val nameStr = nameET!!.text.toString()
        val emailStr = emailET!!.text.toString()
        val passStr = passwordET!!.text.toString()
        val repeatePassStr = repeatePasswordET!!.text.toString()

        var cancel = false
        var focusView: View? = null

        if (nameStr.isEmpty() || nameStr.length == 1) {
            nameET!!.error = string(R.string.notSetNameTF)
            focusView = nameET
            cancel = true
        } else if (emailStr.isEmpty()) {
            emailET!!.error = string(R.string.notSetEmailTF)
            focusView = emailET
            cancel = true
        } else if (passStr.isEmpty()) {       // Check for a valid password, if the user entered one.
            passwordET!!.error = string(R.string.notSetPassTF)
            focusView = passwordET
            cancel = true
        } else if (passStr.length < 5) {       // Check for a valid password, if the user entered one.
            passwordET!!.error = "${string(R.string.minPassErrorTF)} 5"
            focusView = passwordET
            cancel = true
        } else if (passStr != repeatePassStr) {  // Check for a valid password, if the user entered one.
            repeatePasswordET!!.error = string(R.string.notEqualPassTF)
            focusView = repeatePasswordET
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        }
    }

    private class RegisterUI : AnkoComponent<RegistrationMediator> {
        override fun createView(ui: AnkoContext<RegistrationMediator>): View = with(ui) {
            verticalLayout {
                background = gradientBg(arrayOf(ui.owner.color(R.color.startColor), ui.owner.color(R.color.endColor)))
                gravity = Gravity.CENTER

                ui.owner.nameET = editText {
                    background = ui.owner.roundedBg(Color.WHITE, ROUND_CORNERS_16, true)
                    textSize = FONT_SIZE_14
                    textColor = ui.owner.color(R.color.colorPrimaryText)
                    hint = ui.owner.string(R.string.nameTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PERSON_NAME
                }.lparams(ui.owner.wdthProc(0.6f))

                ui.owner.emailET = editText {
                    background = ui.owner.roundedBg(Color.WHITE, ROUND_CORNERS_16, true)
                    textSize = FONT_SIZE_14
                    textColor = ui.owner.color(R.color.colorPrimaryText)
                    hint = ui.owner.string(R.string.emailTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }.lparams(ui.owner.wdthProc(0.6f)) {
                    topMargin = dip8()
                }

                ui.owner.passwordET = editText {
                    background = ui.owner.roundedBg(Color.WHITE, ROUND_CORNERS_16, true)
                    textSize = FONT_SIZE_14
                    textColor = ui.owner.color(R.color.colorPrimaryText)
                    hint = ui.owner.string(R.string.passTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }.lparams(ui.owner.wdthProc(0.6f)) {
                    topMargin = dip8()
                }

                ui.owner.repeatePasswordET = editText {
                    background = ui.owner.roundedBg(Color.WHITE, ROUND_CORNERS_16, true)
                    textSize = FONT_SIZE_14
                    textColor = ui.owner.color(R.color.colorPrimaryText)
                    hint = ui.owner.string(R.string.passRepeatTF)
                    setPadding(dip8(), dip8(), dip8(), dip8())
                    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }.lparams(ui.owner.wdthProc(0.6f)) {
                    topMargin = dip8()
                }

                button(R.string.submitTF) {
                    background = ui.owner.roundedBg(Color.WHITE, ROUND_CORNERS_16, true)
                    textSize = FONT_SIZE_14
                    onClick {
                        ui.owner.registerUser()
                    }
                }.lparams(ui.owner.wdthProc(0.6f), ui.owner.hdthProc(0.06f)) {
                    topMargin = dip8()
                }

                button(R.string.backTF) {
                    background = ui.owner.roundedBg(Color.WHITE, ROUND_CORNERS_16, true)
                    textSize = FONT_SIZE_12
                    textColor = ui.owner.color(R.color.colorPrimaryText)

                    onClick {
                        ui.owner.popToBack(LinearAnimator())
                    }
                }.lparams(ui.owner.wdthProc(0.4f), ui.owner.hdthProc(0.06f)) {
                    topMargin = dip8()
                }
            }
        }
    }
}
