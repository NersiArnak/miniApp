package org.fitmyss.asfe.marathon

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.fitmyss.asfe.databinding.FragmentMarathonListBinding

class MarathonListFragment : Fragment() {

    private var _binding: FragmentMarathonListBinding? = null
    private val binding get() = _binding!!

    private lateinit var pref: SharedPreferences

    private var counter = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarathonListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)
        super.onViewCreated(view, savedInstanceState)

        val counterTextView: TextView = binding.counterTextView
        val incrementButton: Button = binding.incrementButton

        incrementButton.setOnClickListener {
            counter++
            counterTextView.text = counter.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
