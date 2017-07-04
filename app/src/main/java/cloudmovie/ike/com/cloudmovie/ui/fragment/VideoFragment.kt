package cloudmovie.ike.com.cloudmovie.ui.fragment



import android.widget.VideoView
import cloudmovie.ike.com.cloudmovie.R
import cloudmovie.ike.com.cloudmovie.databinding.FragmentVideoBinding
import cloudmovie.ike.com.cloudmovie.databinding.FragmentYunpanSearchBinding
import cloudmovie.ike.com.cloudmovie.presenter.VideoContact
import cloudmovie.ike.com.cloudmovie.ui.base.BaseFragment

/**
 * 视频播放部分
 */
open class VideoFragment :BaseFragment<FragmentVideoBinding,VideoContact.VideoPresenter,VideoContact.VideoView>() {
    override fun initView() {
    }


    override fun initData() {
    }

    override fun initListener() {
    }
    override fun getViewLayout(): Int {

        return R.layout.fragment_video
    }

}