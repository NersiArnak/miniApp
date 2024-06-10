package com.fitsport.asfer.library

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fitsport.asfer.LIBRARY
import com.fitsport.asfer.R
import com.fitsport.asfer.databinding.FragmentLibraryListBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class LibraryListFragment : Fragment() {

    private var _binding: FragmentLibraryListBinding? = null
    private val binding get() = _binding!!

    private val easyAdapter = EasyAdapter()
    private val libraryListItemController = LibraryListItemController()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewLibraryList.adapter = easyAdapter
        easyAdapter.setItems(ItemList.create(LIBRARY, libraryListItemController))

        binding.radioBtn.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.choiceWeight -> filterLibraryList("Для набора массы")
                R.id.choiceLose -> filterLibraryList("Для похудения")
                R.id.choiceStritFood -> filterLibraryList("Стритфуд")
                R.id.choiceAll -> easyAdapter.setItems(ItemList.create(LIBRARY, libraryListItemController))
            }
        }

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    private fun filterLibraryList(status: String) {
        val filteredList = LIBRARY.filter { it.status == status }
        easyAdapter.setItems(ItemList.create(filteredList, libraryListItemController))
    }

    private fun changeRadioButtonColor(radioButton: RadioButton, color: Int) {
        radioButton.setTextColor(color)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

