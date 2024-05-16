package org.fitmyss.asfe.trainings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.fitmyss.asfe.databinding.FragmentAddTrainigBottomSheetBinding

class AddTrainingBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddTrainigBottomSheetBinding? = null
    private val binding get() = _binding!!

    private var listener: OnAddTrainingClickListener? = null
    fun setOnAddTrainingClickListener(_listener: OnAddTrainingClickListener) {
        listener = _listener
    }
    interface OnAddTrainingClickListener {
        fun onOnAddTrainingDiscount(item: TrainingListItem)
    }

    companion object {
        const val TAG = "AddTrainingBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTrainigBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addTraining.setOnClickListener {
            listener?.onOnAddTrainingDiscount(
                TrainingListItem(
                    title = binding.titleTraining.text.toString(),
                    plan = binding.planTraining.text.toString(),
                    aim = binding.aimTraining.text.toString(),
                    time = binding.timeTraining.text.toString(),
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
