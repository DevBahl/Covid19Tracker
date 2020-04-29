package com.dbsrm.covid19tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import it.emperor.animatedcheckbox.binding.setChecked
import kotlinx.android.synthetic.main.selfassess.*

class SelfAssess: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.selfassess,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scrollViewSelfAssess.fullScroll(View.FOCUS_UP)
        animaionChecked()
        resultTextView.visibility = View.INVISIBLE
        submit_button.setOnClickListener {
            resultTextView.visibility = View.INVISIBLE
            if(animatedCheckBox4.isChecked()&& animatedCheckBox1.isChecked()){
                Toast.makeText(context,"Choose Appropriate Options",Toast.LENGTH_LONG).show()
                animaionChecked()
            }else if(animatedCheckBox4.isChecked()&& animatedCheckBox2.isChecked()) {
                Toast.makeText(context, "Choose Appropriate Options", Toast.LENGTH_LONG).show()
                animaionChecked()
            }else if(animatedCheckBox4.isChecked()&& animatedCheckbox3.isChecked()) {
                Toast.makeText(context, "Choose Appropriate Options", Toast.LENGTH_LONG).show()
                animaionChecked()
            }else if(animatedCheckbox5.isChecked() && animatedCheckBox6.isChecked()){
                Toast.makeText(context,"Choose Appropriate Options",Toast.LENGTH_LONG).show()
                animaionChecked()
            }else if(animatedCheckBox9.isChecked() && animatedCheckBox7.isChecked()){
                Toast.makeText(context,"Choose Appropriate Options",Toast.LENGTH_LONG).show()
                animaionChecked()
            }else if(animatedCheckBox9.isChecked() && animatedCheckBox8.isChecked()) {
                Toast.makeText(context, "Choose Appropriate Options", Toast.LENGTH_LONG).show()
                animaionChecked()
            }else if(animatedCheckBox1.isChecked() == false && animatedCheckBox2.isChecked() == false && animatedCheckbox3.isChecked() == false && animatedCheckBox4.isChecked() == false && animatedCheckbox5.isChecked() == false && animatedCheckBox6.isChecked() == false && animatedCheckBox7.isChecked() == false && animatedCheckBox8.isChecked() == false && animatedCheckBox9.isChecked() == false){
                Toast.makeText(context,"Please answer all the questions to do the self assessment",Toast.LENGTH_LONG).show()
                animaionChecked()
            }else if(animatedCheckBox1.isChecked() == false && animatedCheckBox2.isChecked() == false && animatedCheckbox3.isChecked() == false && animatedCheckBox4.isChecked() == false){
                Toast.makeText(context, "Please answer all the questions to do the self assessment", Toast.LENGTH_LONG).show()
            }else if(animatedCheckbox5.isChecked() == false && animatedCheckBox6.isChecked() == false){
                Toast.makeText(context, "Please answer all the questions to do the self assessment", Toast.LENGTH_LONG).show()
            }else if(animatedCheckBox7.isChecked() == false && animatedCheckBox8.isChecked() == false && animatedCheckBox9.isChecked() == false){
                Toast.makeText(context, "Please answer all the questions to do the self assessment", Toast.LENGTH_LONG).show()
            }
           else{
                condition()
                animaionChecked()
                scrollViewSelfAssess.fullScroll(View.FOCUS_DOWN)
            }
        }
    }

    private fun condition(){
        if(animatedCheckBox1.isChecked()||animatedCheckBox2.isChecked()||animatedCheckbox3.isChecked()){
            if(animatedCheckbox5.isChecked()){
                if(animatedCheckBox7.isChecked()||animatedCheckBox8.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You have a high risk of Infection")
                }
                else if(animatedCheckBox9.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You have a low risk of infection")
                }
            }
            else if(animatedCheckBox6.isChecked()){
                if(animatedCheckBox7.isChecked()||animatedCheckBox8.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You have a high risk of Infection")
                }
                else if(animatedCheckBox9.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You have a low risk of infection")
                }
            }
        }
        else if(animatedCheckBox4.isChecked()){
            if(animatedCheckbox5.isChecked()){
                if(animatedCheckBox7.isChecked()||animatedCheckBox8.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You either have a low risk of Infection or a risk of asymtomatic Infection")
                }
                else if(animatedCheckBox9.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You have a low risk of infection")
                }
            }
            else if(animatedCheckBox6.isChecked()){
                if(animatedCheckBox7.isChecked()||animatedCheckBox8.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You either have a low risk of Infection or a risk of asymtomatic Infection")
                }
                else if(animatedCheckBox9.isChecked()){
                    resultTextView.visibility = View.VISIBLE
                    resultTextView.setText("You have a low risk of infection")
                }
            }
        }
    }
    private fun animaionChecked(){
        animatedCheckBox1.setChecked(true)
        animatedCheckBox1.setChecked(false,true)
        animatedCheckBox2.setChecked(true)
        animatedCheckBox2.setChecked(false,true)
        animatedCheckbox3.setChecked(true)
        animatedCheckbox3.setChecked(false,true)
        animatedCheckBox4.setChecked(true)
        animatedCheckBox4.setChecked(false,true)
        animatedCheckbox5.setChecked(true)
        animatedCheckbox5.setChecked(false,true)
        animatedCheckBox6.setChecked(true)
        animatedCheckBox6.setChecked(false,true)
        animatedCheckBox7.setChecked(true)
        animatedCheckBox7.setChecked(false,true)
        animatedCheckBox8.setChecked(true)
        animatedCheckBox8.setChecked(false,true)
        animatedCheckBox9.setChecked(true)
        animatedCheckBox9.setChecked(false,true)
    }
}