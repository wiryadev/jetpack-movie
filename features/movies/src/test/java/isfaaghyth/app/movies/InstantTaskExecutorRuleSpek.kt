package isfaaghyth.app.movies

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.dsl.Root

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class InstantTaskExecutorRuleSpek(root: Root) {
    private val dispatchers = newSingleThreadContext("test")
    init {
        root.beforeGroup {
            Dispatchers.setMain(dispatchers)
            ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
                override fun executeOnDiskIO(runnable: Runnable) {
                    runnable.run()
                }
                override fun isMainThread(): Boolean {
                    return true
                }
                override fun postToMainThread(runnable: Runnable) {
                    runnable.run()
                }
            })
        }
        root.afterGroup {
            ArchTaskExecutor.getInstance().setDelegate(null)
            Dispatchers.resetMain()
            dispatchers.close()
        }
    }
}