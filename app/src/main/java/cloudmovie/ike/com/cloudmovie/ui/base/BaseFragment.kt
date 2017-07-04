package cloudmovie.ike.com.cloudmovie.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ike.commonutils.baseMvp.BasePresenter
import com.ike.commonutils.baseMvp.BaseView
import com.ike.commonutils.net.retrofitnetutils.commonUtils.ClassUtil
import com.ike.commonutils.net.retrofitnetutils.commonUtils.ClassUtil.newInstance
import java.lang.reflect.ParameterizedType

/**
 * fragment的基类
 */
abstract class BaseFragment<T: ViewDataBinding,P :BasePresenter<V>,V :BaseView> : Fragment(),BaseView {
     val TAG:String="BaseFragment"
    var fragmentBinding :T?=null
    var presenter:P?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentBinding=DataBindingUtil.inflate(inflater,getViewLayout(),container,false)
        return (fragmentBinding as ViewDataBinding?)?.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val param: ParameterizedType = (this.javaClass.genericSuperclass) as ParameterizedType
        val type = param.actualTypeArguments[1]
        val clazz = ClassUtil.getClass(type, 1)
        Log.e(TAG,"反射类:"+clazz.simpleName)
        presenter= newInstance(clazz) as P
        presenter?.attachView(this as V)
        initView()
        initListener()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    /**
     * 获取布局信息
     */
    abstract fun getViewLayout():Int

    /**
     * 初始化数据
     */
    abstract fun initData()
    /**
     * 初始化事件监听
     */
    abstract fun initListener()
    /**
     * 初始布局控件
     */
    abstract fun initView()

}