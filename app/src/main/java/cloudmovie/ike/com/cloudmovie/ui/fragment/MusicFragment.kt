package cloudmovie.ike.com.cloudmovie.ui.fragment



import cloudmovie.ike.com.cloudmovie.R
import cloudmovie.ike.com.cloudmovie.databinding.FragmentMusicBinding
import cloudmovie.ike.com.cloudmovie.databinding.FragmentVideoBinding
import cloudmovie.ike.com.cloudmovie.databinding.FragmentYunpanSearchBinding
import cloudmovie.ike.com.cloudmovie.presenter.MusicContact
import cloudmovie.ike.com.cloudmovie.ui.base.BaseFragment

/**
 * 我的音乐部分
 */
open class MusicFragment :BaseFragment<FragmentMusicBinding,MusicContact.MusichPresenter,MusicContact.MusicView>() {
    override fun initView() {
    }


    override fun initData() {
    }

    override fun initListener() {
    }
    override fun getViewLayout(): Int {

        return R.layout.fragment_music
    }

}