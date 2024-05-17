package org.fitmyss.asfe.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import org.fitmyss.asfe.LIBRARY
import org.fitmyss.asfe.databinding.FragmentLibraryListBinding
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

        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(false)
    }
}