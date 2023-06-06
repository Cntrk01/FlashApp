package com.movieapp.flashapp.ui

import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.movieapp.flashapp.R
import com.movieapp.flashapp.adapter.FlashLightAdapter
import com.movieapp.flashapp.adapter.RecyclerAdapter
import com.movieapp.flashapp.databinding.FragmentSlideshowBinding
import com.movieapp.flashapp.viewmodel.ColorLightViewModel
import com.movieapp.flashapp.viewmodel.FlashLightViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlashLightFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerAdapter: FlashLightAdapter
    private val viewModel: FlashLightViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerAdapter= FlashLightAdapter()
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=recyclerAdapter

        searchDataFunc()
        popupMenu()
        //swipe yapınca verilerin internetten gelmesini sağlar
        with(binding){
            swipeRefresh.setOnRefreshListener {
                recyclerView.visibility=View.GONE
                errorText.visibility=View.GONE
                notFoundText.visibility=View.GONE
                progressbar.visibility=View.VISIBLE
                viewModel.getColorApi()
                swipeRefresh.isRefreshing=false
                viewModel.data2.observe(viewLifecycleOwner, Observer {
                    recyclerAdapter.setFlashData(it)
                })
            }
        }


        observeData()
    }

    private fun popupMenu(){
        binding.popButton.setOnClickListener {
            val popupMenu = PopupMenu(requireContext(), binding.popButton)
            popupMenu.menuInflater.inflate(R.menu.selectmenu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.ascRating -> {
                        viewModel.ascRatingValue().observe(viewLifecycleOwner, Observer {
                            recyclerAdapter.setFlashData(it)
                        })
                        true
                    }
                    R.id.descRating -> {
                        viewModel.descRatingValue().observe(viewLifecycleOwner, Observer {
                            recyclerAdapter.setFlashData(it)
                        })
                        true
                    }
                    R.id.ascCount->{
                        viewModel.ascRatingCount().observe(viewLifecycleOwner, Observer {
                            recyclerAdapter.setFlashData(it)
                        })
                        true
                    }
                    R.id.descCount->{
                        viewModel.descRatingCount().observe(viewLifecycleOwner, Observer {
                            recyclerAdapter.setFlashData(it)
                        })
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }

    //Kullanıcı her arattıgı kelimede Room arama fonksiyonu çalışıyor.Eğer sonuçla eşleşirse veri gösteriliyor.
    private fun searchDataFunc() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchItem = menu.findItem(R.id.action_search)
                // cast ederken hata veriyor menu sayfasında bu olcak
                //app:actionViewClass="androidx.appcompat.widget.SearchView"
                //ve yukardaki importlarda androidx.appcompat.widget.SearchView olacak kısa hali var o çalışmıyor
                val searchView = searchItem.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }
                    override fun onQueryTextChange(newText: String?): Boolean {

                        if (newText != null) {
                            binding.recyclerView.scrollToPosition(0)
                            //viewModel.searchSosAlert(query)
                            viewModel.searchFlashLight(newText).observe(viewLifecycleOwner, Observer {
                                it.let {
                                    recyclerAdapter.setFlashData(it)
                                }
                            })
                        }
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }



    private fun observeData(){
        viewModel.toast.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(),it, Toast.LENGTH_LONG).show()
        })

        viewModel.notFoundText.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.notFoundText.visibility=View.VISIBLE
                }else {
                    binding.notFoundText.visibility = View.GONE
                }
            }
        })


        viewModel.getYourDataFromRoom().observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                binding.recyclerView.visibility=View.VISIBLE
                binding.progressbar.visibility=View.GONE
                binding.errorText.visibility=View.GONE
                recyclerAdapter.setFlashData(it)
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    binding.errorText.visibility=View.VISIBLE
                }else{
                    binding.errorText.visibility=View.GONE
                }
            }
        })

        viewModel.progresBar.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it){
                    binding.progressbar.visibility=View.VISIBLE
                    binding.errorText.visibility=View.GONE
                    binding.recyclerView.visibility=View.GONE
                }
                else{
                    binding.progressbar.visibility=View.GONE
                    binding.recyclerView.visibility=View.VISIBLE
                }

            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}