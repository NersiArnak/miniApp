import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fitsport.asfer.R
import com.fitsport.asfer.databinding.FragmentResultsListBinding

class ResultsListFragment : Fragment() {

    private var _binding: FragmentResultsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferences
    private val questionIds = listOf(
        R.id.editText1, R.id.editText2, R.id.editText3,
        R.id.editText4, R.id.editText5, R.id.editText6,
        R.id.editText7, R.id.editText8
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = requireContext().getSharedPreferences("SurveyPrefs", Context.MODE_PRIVATE)

        loadAnswers()

        if (pref.getBoolean("SurveyCompleted", false)) {
            showResults()
        }

        binding.addButton.setOnClickListener {
            if (allQuestionsAnswered()) {
                saveAnswers()
                showResults()
                Toast.makeText(requireContext(), "Опрос успешно завершен", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Пожалуйста, ответьте на все вопросы", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allQuestionsAnswered(): Boolean {
        for (id in questionIds) {
            val editText = view?.findViewById<EditText>(id)
            if (editText?.text.isNullOrEmpty()) {
                return false
            }
        }
        return true
    }

    private fun saveAnswers() {
        val editor = pref.edit()
        for (id in questionIds) {
            val editText = view?.findViewById<EditText>(id)
            editText?.let {
                editor.putString("Answer_$id", it.text.toString())
            }
        }
        editor.putBoolean("SurveyCompleted", true)
        editor.apply()
    }

    private fun loadAnswers() {
        for (id in questionIds) {
            val editText = view?.findViewById<EditText>(id)
            editText?.setText(pref.getString("Answer_$id", ""))
        }
    }

    private fun showResults() {
        binding.questionContainer.visibility = View.GONE
        binding.completionText.visibility = View.VISIBLE
        binding.resultImage.visibility = View.VISIBLE
        binding.addButton.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
