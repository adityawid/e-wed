package online.e_wedd.apps.Activities

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.ActionBar
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import kotlinx.android.synthetic.main.activity_main.*
import online.e_wedd.apps.FragmentMain.AccountFragment
import online.e_wedd.apps.FragmentMain.ChatFragment
import online.e_wedd.apps.FragmentMain.HistoryFragment
import online.e_wedd.apps.FragmentMain.HomeFragment
import online.e_wedd.apps.R

class MainActivity : AppCompatActivity() {

    lateinit var actionBar : ActionBar

    private var temp = 1
    private lateinit var fragmentManager: FragmentManager
    private var mBottomNavigationViewEx: BottomNavigationViewEx? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        actionBar = this.supportActionBar!!

        setFragment(HomeFragment())
        initView()
    }

    private fun initView() {
        var mBottomNavigationViewEx = findViewById(R.id.MA_navigation) as BottomNavigationViewEx

//        mainMenuHandler.setmBottomNavigationViewEx(mBottomNavigationViewEx)
//        setmBottomNavigationViewEx(mBottomNavigationViewEx)
        onUser()
        mBottomNavigationViewEx.enableAnimation(false)
        mBottomNavigationViewEx.enableShiftingMode(false)
        mBottomNavigationViewEx.enableItemShiftingMode(false)
    }

    var mOnNavigationItemSelectedListenerUser: BottomNavigationView.OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.tabHome -> {
//                displayViewUser(0)
                setJudul("Ewedd")
                setFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.tabCart -> {
//                displayViewUser(1)
                setJudul("Order")
                setFragment(HistoryFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.tabChat -> {
//                displayViewUser(2)
                setJudul("Order")
                setFragment(ChatFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.tabAccount -> {
//                displayViewUser(3)
                setJudul("Account")
                setFragment(AccountFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @SuppressLint("NewApi")
    private fun selectedBottomNav(position: Int) {
        if (temp != position) {
            mBottomNavigationViewEx!!.setIconTintList(position, ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)))
            mBottomNavigationViewEx!!.setTextTintList(position, ColorStateList.valueOf(resources.getColor(R.color.colorPrimary)))
            mBottomNavigationViewEx!!.setIconTintList(temp, ColorStateList.valueOf(Color.GRAY))
            mBottomNavigationViewEx!!.setTextTintList(temp, ColorStateList.valueOf(Color.GRAY))
            temp = position
        }
    }


    // MainMENU (1) SET JUDUL
    fun setJudul(title: String) {
        actionBar.setTitle(title)
    }

    // MainMENU (2) SET FRAGMENT
    fun setFragment(fragmentGoal : Fragment) {
        //get functions can be called as instance varibales
        val fragmentTransaction  = supportFragmentManager.beginTransaction()
        //Otherwise same as normal java here
        //fragmentTransaction.add(R.id.frame_main, fragmentGoal)
        fragmentTransaction.replace(R.id.MA_content, fragmentGoal)
        fragmentTransaction.commit()
    }
    private fun onUser() {
        MA_navigation.inflateMenu(R.menu.bottom_navigation)
        MA_navigation.onNavigationItemSelectedListener = mOnNavigationItemSelectedListenerUser

    }
}
