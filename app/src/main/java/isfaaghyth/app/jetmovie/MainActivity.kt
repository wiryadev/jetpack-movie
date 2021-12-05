package isfaaghyth.app.jetmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import isfaaghyth.app.abstraction.ui.ViewPagerAdapter
import isfaaghyth.app.jetmovie.databinding.ActivityMainBinding
import isfaaghyth.app.movies.ui.MovieFragment
import isfaaghyth.app.tvshows.ui.TVShowFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPagerSetup()
    }

    private fun viewPagerSetup() {
        val adapter = ViewPagerAdapter(this)
        val titles = arrayOf("Movie", "TV")
        adapter.addFragment(MovieFragment())
        adapter.addFragment(TVShowFragment())
        with(binding) {
            viewpagerMain.adapter = adapter
            TabLayoutMediator(tabLayoutMain, viewpagerMain) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }

}
