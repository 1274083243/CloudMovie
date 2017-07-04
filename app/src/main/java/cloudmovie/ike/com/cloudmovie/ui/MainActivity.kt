package cloudmovie.ike.com.cloudmovie.ui

import android.databinding.DataBindingUtil
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import cloudmovie.ike.com.cloudmovie.R
import cloudmovie.ike.com.cloudmovie.databinding.ActivityMainBinding
import cloudmovie.ike.com.cloudmovie.databinding.NavHeaderMainBinding
import cloudmovie.ike.com.cloudmovie.ui.fragment.MusicFragment
import cloudmovie.ike.com.cloudmovie.ui.fragment.VideoFragment
import cloudmovie.ike.com.cloudmovie.ui.fragment.YunPanSearchFragment
import com.ike.commonutils.UiBase.BaseActivity
import com.ike.commonutils.widget.widgetUtils.StatsuBarUtils.StatusBarUtil

class MainActivity : BaseActivity<ActivityMainBinding>(), View.OnClickListener {
    val TAG = "MainActivity"
    var drawerLayout: DrawerLayout? = null
    var iv_menu: ImageView? = null
    var leftMenu: NavigationView? = null
    var bind: NavHeaderMainBinding? = null
    var yunPanFg: YunPanSearchFragment? = null
    var videoFg: VideoFragment? = null
    var musicFg: MusicFragment? = null
    val PAN_SEARCH: Int = 0
    val VIDEO: Int = 1
    val MUSIC: Int = 2
    var CURR_FRAGTYPE: Int = PAN_SEARCH//当前显示的fragment类型
    var manager: FragmentManager? = null
    var titleBar: TextView? = null
    override fun getLayout(): Int {
        return R.layout.activity_main
    }


    override fun initView() {
        setSupportActionBar(viewDataBinding.include.toolBar)
        drawerLayout = viewDataBinding.drawerlayout
        iv_menu = viewDataBinding.include.ivTitleMenu
        leftMenu = viewDataBinding.leftMenu
        titleBar = viewDataBinding.include.tvBarTitle

        initLeftMenu()
        manager = supportFragmentManager
        yunPanFg = YunPanSearchFragment()
        manager?.beginTransaction()?.add(R.id.fl_content, yunPanFg)?.commit()
        StatusBarUtil.setColorForDrawerLayout(this, drawerLayout, getResources().getColor(R.color.colorTheme), 0)
    }

    /**
     * 初始化侧边栏
     */
    fun initLeftMenu() {
        leftMenu?.inflateHeaderView(R.layout.nav_header_main)
        val headView: View = leftMenu?.getHeaderView(0)!!
        bind = DataBindingUtil.bind<NavHeaderMainBinding>(headView)
        bind?.llNavPansearch?.setOnClickListener(this)
        bind?.llNavMusic?.setOnClickListener(this)
        bind?.llNavVideo?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
        //云盘搜索
            R.id.ll_nav_pansearch -> {
                changeFragment(PAN_SEARCH)
            }
        //我的音乐
            R.id.ll_nav_music -> {
                changeFragment(MUSIC)
            }
        //视频
            R.id.ll_nav_video -> {
                changeFragment(VIDEO)
            }

        }

    }

    override fun initListener() {
        iv_menu?.setOnClickListener {
            drawerLayout?.openDrawer(Gravity.LEFT)
        }


    }

    override fun initData() {

    }

    /**
     * 切换fragment
     */
    fun changeFragment(fragmentType: Int) {
        drawerLayout?.postDelayed(Runnable {
            drawerLayout?.closeDrawer(Gravity.LEFT)
        }, 260)
        if (CURR_FRAGTYPE == fragmentType) {
            return
        }
        val beginTransaction = manager?.beginTransaction()
        hideFragment(beginTransaction)
        when (fragmentType) {
            PAN_SEARCH -> {
                if (yunPanFg == null) {
                    yunPanFg = YunPanSearchFragment()

                    beginTransaction?.add(R.id.fl_content, yunPanFg)
                } else {
                    beginTransaction?.show(yunPanFg)
                }
                titleBar?.setText(R.string.pan_search)
            }


            MUSIC -> {
                if (musicFg == null) {
                    musicFg = MusicFragment()
                    beginTransaction?.add(R.id.fl_content, musicFg)
                } else {
                    beginTransaction?.show(musicFg)
                }
                titleBar?.setText(R.string.music)
            }

            VIDEO -> {
                if (videoFg == null) {
                    videoFg = VideoFragment()
                    beginTransaction?.add(R.id.fl_content, videoFg)
                } else {
                    beginTransaction?.show(videoFg)
                }
                titleBar?.setText(R.string.video)
            }
        }
        beginTransaction?.commit()
        CURR_FRAGTYPE = fragmentType


    }

    /**
     * 隐藏fragment
     */
    fun hideFragment(beginTransaction: FragmentTransaction?) {
        if (yunPanFg != null) {
            beginTransaction?.hide(yunPanFg)
        }
        if (musicFg != null) {
            beginTransaction?.hide(musicFg)
        }
        if (videoFg != null) {
            beginTransaction?.hide(videoFg)
        }
    }


}
