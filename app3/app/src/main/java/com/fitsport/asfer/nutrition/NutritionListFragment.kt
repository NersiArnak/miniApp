package com.fitsport.asfer.nutrition

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.fitsport.asfer.R
import com.fitsport.asfer.NUTRITIONS
import com.fitsport.asfer.databinding.FragmentNutritionListBinding
import com.fitsport.asfer.databinding.NutritionListItemBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class NutritionListFragment : Fragment(), AddNutritionBottomSheetFragment.OnAddNutritionClickListener {

    private var _binding: FragmentNutritionListBinding? = null
    private val binding get() = _binding!!


    private val easyAdapter = EasyAdapter()
    private val NutritionListItems = arrayListOf<NutritionListItem>()
    private val NutritionListItemController = NutritionListItemController()
    private lateinit var pref: SharedPreferences

    private var count = 52987;
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNutritionListBinding.inflate(inflater, container, false)
        return binding.root

    }
    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)

        pref = requireContext().getSharedPreferences("NutritionsList", Context.MODE_PRIVATE)

        val NutritionListAdapter = binding.recyclerViewNutritionList
        NutritionListAdapter.adapter = easyAdapter
        val arrayList = getDataFromSharedPrefs(pref)

        if (arrayList.isNullOrEmpty()) {
            NutritionListItems.addAll(NUTRITIONS)
            easyAdapter.setItems(ItemList.create(NutritionListItems, NutritionListItemController))
        } else {
            NutritionListItems.addAll(arrayList)
            easyAdapter.setItems(ItemList.create(arrayList, NutritionListItemController))
        }
        buildAddButton()

        val countText: TextView = binding.nubmersActivityUsers
        val countButton: Button = binding.buttonActibiteUsers

        countButton.setOnClickListener{
            count++
            countText.text = count.toString()
        }



    }

    private fun buildAddButton() {
        binding.addNutritionBtn.setOnClickListener {
            val addNutritionBottomSheet = AddNutritionBottomSheetFragment()
            addNutritionBottomSheet.setOnAddNutritionClickListener(this)
            addNutritionBottomSheet.show(parentFragmentManager, AddNutritionBottomSheetFragment.TAG)
        }
    }

    private fun getDataFromSharedPrefs(pref: SharedPreferences) : List<NutritionListItem>? {
        val json = pref.getString("NutritionsListItems", "").orEmpty()
        val type = object : TypeToken<List<NutritionListItem?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOnAddNutritionDiscount(item: NutritionListItem) {
        val editor: SharedPreferences.Editor = pref.edit()
        NutritionListItems.add(item)
        editor.putString("NutritionsListItems", Gson().toJson(NutritionListItems)).apply()
        easyAdapter.setItems(ItemList.create(NutritionListItems, NutritionListItemController))
    }
}
