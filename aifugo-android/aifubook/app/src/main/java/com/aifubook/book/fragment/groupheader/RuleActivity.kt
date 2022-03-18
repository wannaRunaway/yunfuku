package com.aifubook.book.fragment.groupheader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.aifubook.book.R
import com.aifubook.book.databinding.ActivityRuleBinding
import com.aifubook.book.utils.StatusBarCompat
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RuleActivity : AppCompatActivity() {
    private lateinit var ruleViewModel: RuleViewModel
    private lateinit var binding: ActivityRuleBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ruleViewModel = ViewModelProvider(this)[RuleViewModel::class.java]
        binding = ActivityRuleBinding.inflate(layoutInflater)
        StatusBarCompat.translucentStatusBar(this)
        setContentView(binding.root)
        binding.header.imageviewLeft.setOnClickListener { finish() }
        binding.header.headerTextview.text = "查看规则"
        val map = HashMap<String, String>()
        map["key"] = "team_info"
        ruleViewModel.getbeansConfig(map)
        lifecycleScope.launch {
            ruleViewModel.textstring.collect {
                it.result?.let {
                    binding.textview.text = Html.fromHtml(it)
                }
            }
        }
    }
}