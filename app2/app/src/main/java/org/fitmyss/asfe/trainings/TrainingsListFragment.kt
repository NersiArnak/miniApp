package org.fitmyss.asfe.trainings

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.fitmyss.asfe.TRAININGS
import org.fitmyss.asfe.databinding.FragmentTrainingsListBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class TrainingsListFragment : Fragment(), AddTrainingBottomSheetFragment.OnAddTrainingClickListener {

    private var _binding: FragmentTrainingsListBinding? = null
    private val binding get() = _binding!!

    private val easyAdapter = EasyAdapter()
    private val trainingListItems = arrayListOf<TrainingListItem>()
    private val trainingListItemController = TrainingListItemController()

    private lateinit var pref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrainingsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)

        //сслыка на хранилище
        pref = requireContext().getSharedPreferences("trainingsList", Context.MODE_PRIVATE)

        val trainingListAdapter = binding.recyclerViewTrainingList
        trainingListAdapter.adapter = easyAdapter
        val arrayList = getDataFromSharedPrefs(pref)

        if (arrayList.isNullOrEmpty()) {
            trainingListItems.addAll(TRAININGS)
            easyAdapter.setItems(ItemList.create(trainingListItems, trainingListItemController))
        } else {
            trainingListItems.addAll(arrayList)
            easyAdapter.setItems(ItemList.create(arrayList, trainingListItemController))
        }

        buildAddButton()
    }

    private fun buildAddButton() {
        binding.addTrainingBtn.setOnClickListener {
            val addTrainingBottomSheet = AddTrainingBottomSheetFragment()
            addTrainingBottomSheet.setOnAddTrainingClickListener(this)
            addTrainingBottomSheet.show(parentFragmentManager, AddTrainingBottomSheetFragment.TAG)
        }
    }

    private fun getDataFromSharedPrefs(pref: SharedPreferences) : List<TrainingListItem>? {
        val json = pref.getString("trainingsListItems", "").orEmpty()
        val type = object : TypeToken<List<TrainingListItem?>?>() {}.type
        return Gson().fromJson(json, type)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onOnAddTrainingDiscount(item: TrainingListItem) {
        val editor: SharedPreferences.Editor = pref.edit()
        trainingListItems.add(item)
        editor.putString("trainingsListItems", Gson().toJson(trainingListItems)).apply()
        easyAdapter.setItems(ItemList.create(trainingListItems, trainingListItemController))
    }
}