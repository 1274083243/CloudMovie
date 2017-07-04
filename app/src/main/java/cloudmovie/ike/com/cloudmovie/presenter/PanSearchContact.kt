package cloudmovie.ike.com.cloudmovie.presenter

import android.content.Context
import android.util.Log
import cloudmovie.ike.com.cloudmovie.constant.ConstantUtils
import cloudmovie.ike.com.cloudmovie.model.PanDataModel
import com.ike.commonutils.baseMvp.BasePresenter
import com.ike.commonutils.baseMvp.BaseView
import com.ike.commonutils.net.retrofitnetutils.Exception.ApiException
import com.ike.commonutils.net.retrofitnetutils.model.ExtendsApiResult
import com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api.ApiCallBack
import com.ike.commonutils.net.retrofitnetutils.retrofitUtils.Api.RetrofitNetUtils
import org.jsoup.Jsoup

/**
 * 云盘搜索契约类
 * */
class PanSearchContact {
    val TAG:String="PanSearchContact"
    /**
     * 云盘搜索代理类
     */
    inner class PanSearchPresenter : BasePresenter<PanSearchView>() {

        /**
         * 获取云盘信息
         */
        fun getPanSearchData(str: String, context: Context) {
            val paramMap = hashMapOf<String, String>()
            paramMap.put("keyword", str)
            var callBack = SearchCallBack(view)
            RetrofitNetUtils.Builder(context.applicationContext).build().get(ConstantUtils.YUN_PAN_SEARCH_URL, paramMap, callBack)
        }
    }

    /**
     * 云盘搜索视图
     */
    interface PanSearchView : BaseView {
        fun showLoadDialog()
        fun initSearchData(data: List<PanDataModel>)
        fun disMissDialog()
    }

    inner class SearchCallBack(view:PanSearchView) : ApiCallBack<ExtendsApiResult<String>>() {
        var data = mutableListOf<PanDataModel>()
        var mView:PanSearchView?=null
        init {
            mView=view
        }
        override fun onRequestFinish() {
            mView?.disMissDialog()
        }
        override fun onError(e: ApiException?) {
            Log.e(TAG,"出错了:"+e?.message)

        }

        override fun onSuccess(result: ExtendsApiResult<String>?) {
            val html = Jsoup.parse(result?.data)
            val tr = html.select("tr")
            for (i in 1..tr.size - 1) {
                val name = tr[i].select("td").select("a").text()
                val path = tr[i].select("td").select("a").attr("href")
                if (name.isNotEmpty() && path.isNotEmpty()) {
                    data.add(PanDataModel(name, path))

                }
            }
            mView?.initSearchData(data)
        }


    }
}