package com.example.ghtk_game

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import com.example.ghtk_game.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var inflater: LayoutInflater
    private var result: String = ""
    private var i = 0
    private var indexResult = 0
    private var score = 0
    private var luotChoi = 5
    private var listQuestion = mutableListOf<String>()
    private lateinit var resultButtons: MutableList<Button>
    private var imageList = listOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflater = LayoutInflater.from(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        binding.btnNext.setOnClickListener {
            if (binding.btnNext.text == "Chơi lại") {
                resetGame()
            }else if(binding.btnNext.text == "Câu tiếp"){
                nextGame()
            }
        }
    }
    private fun initData(){
        resultButtons = mutableListOf()
        listQuestion.add("aomua")
        listQuestion.add("baocao")
        listQuestion.add("canthiep")
        listQuestion.add("chieutre")
        listQuestion.add("cattuong")
        listQuestion.add("danhlua")
        listQuestion.add("danong")
        listQuestion.add("giandiep")
        listQuestion.add("giangmai")
        listQuestion.add("hoidong")
        listQuestion.add("hongtam")
        listQuestion.add("khoailang")
        listQuestion.add("kiemchuyen")
        listQuestion.add("lancan")
        imageList = listOf(
            R.drawable.aomua,
            R.drawable.baocao,
            R.drawable.canthiep,
            R.drawable.chieutre,
            R.drawable.cattuong,
            R.drawable.danhlua,
            R.drawable.danong,
            R.drawable.giandiep,
            R.drawable.giangmai,
            R.drawable.hoidong,
            R.drawable.hongtam,
            R.drawable.khoailang,
            R.drawable.kiemchuyen,
            R.drawable.lancan
        )
        initGridResult(listQuestion[i].length)
        initGridChoice(listQuestion[i])
        binding.imgContent.setImageResource(imageList[i])
    }

    private fun resetGame() {
        binding.gridResult.removeAllViews()
        binding.gridChoice.removeAllViews()
        binding.btnNext.visibility = View.INVISIBLE
        binding.tvKetQua.text = ""
        result = ""
        indexResult = 0
        resultButtons.clear()
        initGridResult(listQuestion[i].length)
        initGridChoice(listQuestion[i])
    }
    private fun nextGame() {
        i++
        binding.gridResult.removeAllViews()
        binding.gridChoice.removeAllViews()
        binding.btnNext.visibility = View.INVISIBLE
        binding.tvKetQua.text = ""
        result = ""
        indexResult = 0
        resultButtons.clear()
        initGridResult(listQuestion[i].length)
        initGridChoice(listQuestion[i])
        binding.imgContent.setImageResource(imageList[i])
    }

    private fun initGridChoice(str: String) {
        val list = addListChoice(str)
        list.forEach { letter ->
            val buttonView = inflater.inflate(R.layout.custome_button_choice, binding.gridChoice, false) as RelativeLayout
            val button = buttonView.findViewById<Button>(R.id.custom_button)
            button.text = letter.kitu.toString()
            button.setOnClickListener {
                if (indexResult < listQuestion[i].length) {
                    resultButtons[indexResult].text = button.text.toString()
                    result += button.text.toString()
                    indexResult++
                    if (indexResult == listQuestion[i].length) {
                        binding.btnNext.visibility = View.VISIBLE
                        checkResult()
                    }
                    it.visibility = View.INVISIBLE
                }
            }
            binding.gridChoice.addView(buttonView)
        }
    }

    private fun checkResult() {
        if (result == listQuestion[i]) {
            binding.btnNext.text="Câu tiếp"
            binding.tvKetQua.text = "Bạn đã trả lời đúng"
            binding.tvKetQua.visibility = View.VISIBLE
            score += 1000
            binding.tvScore.text = score.toString()
            if(score==14000){
                binding.tvKetQua.text="Ban đã chiến thắng"
                GlobalScope.launch {
                    delay(2000)
                    finish()
                }
            }else{
                updateGridResult(true)
            }

        } else {
            binding.btnNext.text = "Chơi lại"
            binding.tvKetQua.text = "Bạn đã trả lời sai"
            binding.tvKetQua.visibility = View.VISIBLE
            luotChoi -= 1
            binding.tvLuotChoi.text = luotChoi.toString()
            if (luotChoi == 0) {
                binding.tvKetQua.text = "Bạn đã hết lượt chơi"
                binding.btnNext.visibility = View.GONE
                GlobalScope.launch {
                    delay(2000)
                    finish()
                }
            }
            updateGridResult(false)
        }
    }

    private fun updateGridResult(isCorrect: Boolean) {
        resultButtons.clear()
        binding.gridResult.removeAllViews()
        if (isCorrect) {
            initGridTrue(listQuestion[i].length, result)
        } else {
            initGridFalse(listQuestion[i].length, result)
        }
    }

    private fun initGridResult(i: Int) {
        for (index in 0 until i) {
            val buttonView = inflater.inflate(R.layout.custome_button_result, binding.gridResult, false) as RelativeLayout
            val button = buttonView.findViewById<Button>(R.id.custom_button_result)
            button.setOnClickListener {
            }
            binding.gridResult.addView(buttonView)
            resultButtons.add(button)
        }
    }

    private fun initGridTrue(i: Int, str: String) {
        for (index in 0 until i) {
            val buttonView = inflater.inflate(R.layout.custome_button_true, binding.gridResult, false) as RelativeLayout
            val button = buttonView.findViewById<Button>(R.id.custom_button_true)
            button.text = str[index].toString()
            button.setOnClickListener {
            }
            binding.gridResult.addView(buttonView)
        }
    }
    private fun initGridFalse(i: Int, str: String) {
        for (index in 0 until i) {
            val buttonView = inflater.inflate(R.layout.custome_button_false, binding.gridResult, false) as RelativeLayout
            val button = buttonView.findViewById<Button>(R.id.custom_button_false)
            button.text = str[index].toString()
            button.setOnClickListener {
                // Xử lý sự kiện click cho button nếu cần
            }
            binding.gridResult.addView(buttonView)
        }
    }

    private fun addListChoice(str: String): List<KiTu> {
        val allCharacters = "abcdefghijklmnopqrstuvwxyz"
        val characterList = str.toMutableList()
        val additionalCharactersCount = 20 - str.length
        repeat(additionalCharactersCount) {
            val randomCharacter = allCharacters.random()
            characterList.add(randomCharacter)
        }
        characterList.shuffle()
        val list = mutableListOf<KiTu>()
        for (i in characterList.indices) {
            list.add(KiTu(characterList[i], i))
        }
        return list
    }
}
