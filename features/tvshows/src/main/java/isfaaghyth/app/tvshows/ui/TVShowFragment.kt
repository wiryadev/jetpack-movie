package isfaaghyth.app.tvshows.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import isfaaghyth.app.abstraction.util.ext.toast
import isfaaghyth.app.abstraction.util.state.LoaderState
import isfaaghyth.app.data.entity.TVShow
import isfaaghyth.app.tvshows.databinding.FragmentTvshowBinding
import isfaaghyth.app.tvshows.di.DaggerTVShowComponent
import javax.inject.Inject

class TVShowFragment: Fragment() {

    private var _binding: FragmentTvshowBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TVShowViewModel

    private var tvshowData = arrayListOf<TVShow>()
    private val adapter: TVShowAdapter by lazy {
        TVShowAdapter(tvshowData)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentTvshowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initInjector()
        initView()
    }

    private fun initView() {
        viewModel = ViewModelProvider(this, viewModelFactory)[TVShowViewModel::class.java]

        binding?.lstTvShows?.adapter = adapter

        initObserver()
    }

    private fun initObserver() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoaderState.ShowLoading -> toast("loading")
                is LoaderState.HideLoading -> toast("complete")
            }
        }

        viewModel.result.observe(viewLifecycleOwner) { tvshows ->
            tvshowData.addAll(tvshows)
            adapter.notifyDataSetChanged()
        }

        viewModel.error.observe(viewLifecycleOwner) { error ->
            toast(error)
        }
    }

    private fun initInjector() {
        DaggerTVShowComponent.builder()
            .tVShowModule(TVShowModule())
            .build()
            .inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clear()
        _binding = null
    }

}