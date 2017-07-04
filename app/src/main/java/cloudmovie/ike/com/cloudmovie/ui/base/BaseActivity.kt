
package cloudmovie.ike.com.cloudmovie.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 *所有activity的基类
 */
 abstract class BaseActivity<T :ViewDataBinding>:AppCompatActivity(){
    var viewBinding:T?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = DataBindingUtil.setContentView<ViewDataBinding>(this, getLayout())
        if(contentView!=null){
            viewBinding= contentView as T
        }
    }
    abstract fun getLayout():Int

}