package com.android.rickandmortyproject.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.rickandmortyproject.R
import com.android.rickandmortyproject.databinding.FragmentCharacterListBinding
import com.android.rickandmortyproject.enum.StatusType
import com.android.rickandmortyproject.ui.list.adapter.CharacterGridAdapter
import com.android.rickandmortyproject.ui.list.adapter.CharacterListAdapter
import com.android.rickandmortyproject.util.extension.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private var _adapterCharacterList: CharacterListAdapter? = null
    private val adapterCharacterList get() = _adapterCharacterList!!

    private var _adapterCharacterGrid: CharacterGridAdapter? = null
    private val adapterCharacterGrid get() = _adapterCharacterGrid!!

    private val viewModel: CharacterListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewRoot = inflater.inflate(R.layout.fragment_character_list, container, false)
        _binding = DataBindingUtil.bind(viewRoot)
        setUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        viewModel.characterLiveData.observe(viewLifecycleOwner, { pd ->
            lifecycleScope.launchWhenCreated {
                pd?.let {
                    adapterCharacterList.submitData(it)
                    adapterCharacterGrid.submitData(it)
                }
            }
        })

        viewModel.getCharacterList("", viewModel.statusType.status)

    }

    private fun setUI() {
        _adapterCharacterList = CharacterListAdapter(itemClickCallback = { item ->
            findNavController().navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    item
                )
            )
        }, favIconClickCallback = {
            viewModel.updateCharacterFavorite(it.id)
        })

        _adapterCharacterGrid = CharacterGridAdapter(itemClickCallback = { item ->
            findNavController().navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    item
                )
            )
        }, favIconClickCallback = {
            viewModel.updateCharacterFavorite(it.id)
        })

        binding.recyclerCharacter.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = adapterCharacterList
        }

        binding.txtClearAll.setOnClickListener {
            viewModel.setStatus(StatusType.NOT_SELECT)
            viewModel.getCharacterList("", viewModel.statusType.status)
            binding.chkUnknown.isChecked = false
            binding.chkAlive.isChecked = false
            binding.chkDead.isChecked = false
            binding.edtSearch.setText("")
        }

        binding.btnSearch.setOnClickListener {
            viewModel.getCharacterList(
                name = binding.edtSearch.text.toString(),
                status = viewModel.statusType.status
            )
            binding.root.hideKeyboard()
        }

        binding.chkDead.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                viewModel.setStatus(StatusType.DEAD)
        }

        binding.chkAlive.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                viewModel.setStatus(StatusType.ALIVE)
        }

        binding.chkUnknown.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                viewModel.setStatus(StatusType.UNKNOWN)
        }

        binding.icGrid.setOnClickListener {
            //    viewModel.updateViewType(ListType.GRID)
            //    adapterCharacterList.notifyDataSetChanged()
            binding.recyclerCharacter.apply {
                layoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
                adapter = adapterCharacterGrid
            }

            binding.icList.background = ResourcesCompat.getDrawable(
                resources, R.drawable.ic_list_unselect, null
            )
            binding.icGrid.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_grid_selected, null
            )

        }
        binding.icList.setOnClickListener {
            //     viewModel.updateViewType(ListType.LIST)
            //    adapterCharacterList.notifyDataSetChanged()
            binding.recyclerCharacter.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = adapterCharacterList
            }
            binding.icList.background = ResourcesCompat.getDrawable(
                resources, R.drawable.ic_list_select, null
            )
            binding.icGrid.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.ic_grid_unselect, null
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _adapterCharacterGrid = null
        _adapterCharacterList = null
    }
}