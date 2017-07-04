package cloudmovie.ike.com.cloudmovie.ui.fragment



import android.app.Notification
import android.app.ProgressDialog
import android.support.v7.widget.AppCompatEditText
import android.view.View
import android.widget.TextView
import cloudmovie.ike.com.cloudmovie.R
import cloudmovie.ike.com.cloudmovie.databinding.FragmentYunpanSearchBinding
import cloudmovie.ike.com.cloudmovie.model.PanDataModel
import cloudmovie.ike.com.cloudmovie.presenter.PanSearchContact
import cloudmovie.ike.com.cloudmovie.ui.base.BaseFragment
import cloudmovie.ike.com.cloudmovie.widget.ClondDialog
import com.ike.commonutils.widget.FlowLayout

/**
 * 百度云盘搜索部分
 */
open class YunPanSearchFragment : BaseFragment<FragmentYunpanSearchBinding,PanSearchContact.PanSearchPresenter, PanSearchContact.PanSearchView>(), View.OnClickListener,PanSearchContact.PanSearchView {


    open var mdatas: List<String>? = null
    var flowLayout: FlowLayout? = null
    var tvSearch: TextView? = null
    var etSearch : AppCompatEditText?=null
    var datas= mutableListOf<PanDataModel>()
    var progressDialog:ClondDialog?=null
    override fun initView() {
        progressDialog =ClondDialog(context)
        progressDialog?.show()
        flowLayout = fragmentBinding?.flowlayout
        tvSearch = fragmentBinding?.tvSearch
        etSearch=fragmentBinding?.etSearch
    }

    override fun initData() {
        mdatas = listOf<String>(
                "瓦洛兰",
                "德玛西亚",
                "班德尔城",
                "诺克萨斯",
                "祖安",
                "皮尔特沃夫",
                "艾欧尼亚",
                "李青",
                "阿利斯塔",
                "希维尔",
                "潘森",
                "伊泽瑞尔",
                "雷克顿",
                "古拉加斯",
                "奥利安娜",
                "崔斯塔娜",
                "泰达米尔",
                "马尔扎哈",
                "卡西奥佩娅",
                "艾尼维亚"
        )
        flowLayout?.setmDatas(mdatas)

    }

    override fun initListener() {
        tvSearch?.setOnClickListener(this)
    }

    override fun getViewLayout(): Int {

        return R.layout.fragment_yunpan_search
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_search -> {
                presenter?.getPanSearchData(etSearch?.text.toString().trim(),context)
        }
    }


    }
    override fun showLoadDialog() {


    }

    override fun initSearchData(data :List<PanDataModel>) {

    }
    override fun disMissDialog() {

    }

}