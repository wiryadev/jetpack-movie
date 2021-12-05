package isfaaghyth.app.jetmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(MovieFragment(), "Movie")
        adapter.addFragment(TVShowFragment(), "TV")
        binding.viewpagerMain.adapter = adapter
        binding.tabLayoutMain?.setupWithViewPager(binding.viewpagerMain)
    }

}
