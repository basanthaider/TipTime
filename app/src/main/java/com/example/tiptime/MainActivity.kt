package com.example.tiptime

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiptime.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    var total = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val scale = resources.displayMetrics.density
            val desiredPx = (16 * scale + 0.5f).toInt()
            v.setPadding(
                systemBars.left + desiredPx,
                systemBars.top + desiredPx,
                systemBars.right + desiredPx,
                systemBars.bottom + desiredPx
            )
            insets
        }
        total = savedInstanceState?.getDouble("total") ?: 0.0
        binding.resultTv.text = total.toString()

        binding.calculateMbtn.setOnClickListener {
            val cost = binding.serviceEt.text.toString().toDouble()
            val checkedRb = binding.rbGroup.checkedRadioButtonId
            val tipPercentage = when (checkedRb) {
                R.id.amazing_rb -> 0.2
                R.id.good_rb -> 0.18
                else -> 0.15
            }
            total = tipPercentage * cost
            if (binding.roundTipMs.isChecked)
                total = ceil(total)
            binding.resultTv.text = "$$total"

            Snackbar
                .make(binding.root, "Reset Everything!", BaseTransientBottomBar.LENGTH_INDEFINITE)
                .setAction("Reset") {
                    binding.serviceEt.text?.clear()
                    binding.rbGroup.check(R.id.amazing_rb)
                    binding.roundTipMs.isChecked = true
                    binding.resultTv.setText("Tip Amount")
                }.show()

        }


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("total", total)

    }
}