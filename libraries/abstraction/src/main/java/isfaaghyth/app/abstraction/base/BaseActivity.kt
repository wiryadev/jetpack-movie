package isfaaghyth.app.abstraction.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import isfaaghyth.app.abstraction.util.view.KeyboardUtils
import isfaaghyth.app.abstraction.util.ext.toast

abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity(), BaseView {

    lateinit var binding: VB

    /**
     * lifecycle method
     * @method contentView(): @return resLayoutId
     * @method initView()depe
     */
    abstract fun contentView(): VB
    abstract fun initView()
    abstract fun initInjector()

    /**
     * (optional, use it if needed)
     */
    protected lateinit var savedInstanceState: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            this.savedInstanceState = savedInstanceState
        }
        binding = contentView()
        setContentView(binding.root)
        initInjector()
        initView()
    }

    override fun onMessage(message: String?) {
        toast(message)
    }

    override fun onMessage(stringResId: Int) {
        onMessage(getString(stringResId))
    }

    /**
     * check internet connection
     */
    override fun isNetworkConnect(): Boolean {
        return true //TODO(make a utilities class for this)
    }

    /**
     * hide keyboard layout
     */
    override fun hideKeyboard() {
        return KeyboardUtils().hide(this)
    }

}