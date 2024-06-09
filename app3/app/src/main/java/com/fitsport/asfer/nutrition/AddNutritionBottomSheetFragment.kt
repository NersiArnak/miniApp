package com.fitsport.asfer.nutrition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.fitsport.asfer.databinding.FragmentAddNutritionBottomSheetBinding

class AddNutritionBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddNutritionBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var listener: OnAddNutritionClickListener? = null
    fun setOnAddNutritionClickListener(_listener: OnAddNutritionClickListener) {
        listener = _listener
    }
    interface OnAddNutritionClickListener {
        fun onOnAddNutritionDiscount(item: NutritionListItem)
    }

    companion object {
        const val TAG = "AddNutritionBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNutritionBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNutrition.setOnClickListener {
            listener?.onOnAddNutritionDiscount(
                NutritionListItem(
                    title = binding.titleNutrition.text.toString(),
                    name = binding.nameNutrition.text.toString(),
                    morning = binding.morningNutrition.text.toString(),
                    dinner = binding.dinnerNutrition.text.toString(),
                    supper = binding.supperNutrition.text.toString()

                )
            )
            dismiss()
        }
    }

    override fun onDestroy() {
        dialog?.dismiss()
        super.onDestroy()
    }
}
