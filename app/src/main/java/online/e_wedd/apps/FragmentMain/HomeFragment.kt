package online.e_wedd.apps.FragmentMain


import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daimajia.slider.library.Animations.DescriptionAnimation
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.daimajia.slider.library.Tricks.ViewPagerEx
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import online.e_wedd.apps.Activities.OrderActivity

import online.e_wedd.apps.R
import org.jetbrains.anko.support.v4.indeterminateProgressDialog
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), ViewPagerEx.OnPageChangeListener, BaseSliderView.OnSliderClickListener {

    var TAG = "HOMEFRAGMENT"
    lateinit var rootView : View
//    var listCategory : ArrayList<Mst_Category> = ArrayList()
    lateinit var mProgressDialog : ProgressDialog
//    lateinit var adapter: CategoryAdapter

    private var mDemoSlider: SliderLayout? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater!!.inflate(R.layout.fragment_home, container, false)
        mProgressDialog = indeterminateProgressDialog("Loading...")
        mDemoSlider = rootView.findViewById(R.id.HF_slider)
//        initSlider()

        rootView.kategori_wardrobe.setOnClickListener {
            startActivity(Intent(context, OrderActivity::class.java))
        }
        rootView.kategori_catering.setOnClickListener {
            toast("Coming Soon")
        }
        rootView.kategori_decoration.setOnClickListener {
            toast("Coming Soon")
        }
        rootView.kategori_documentation.setOnClickListener {
            toast("Coming Soon")
        }
        rootView.kategori_makeup.setOnClickListener {
            toast("Coming Soon")
        }
        rootView.kategori_performance.setOnClickListener {
            toast("Coming Soon")
        }
        return rootView
    }

    private fun initSlider() {
        val file_maps = HashMap<String, String>()
        file_maps["paket"] = "https://s0.bukalapak.com/img/054369541/large/IMG_20160204_064432_scaled.jpg";
        file_maps["WardRobe"] = "https://ecs7.tokopedia.net/img/cache/300/product-1/2016/7/12/1342571/1342571_ebd85ee6-aff2-4d12-a2ba-081a5a38378a.png";
        file_maps["UNKOWN"] = "https://www.fashiongonerogue.com/wp-content/uploads/2017/07/Converse-Patbo-Chuck-Taylor-All-Star-Sneaker1.jpg";

        for (name in file_maps.keys) {
            val textSliderView = TextSliderView(context)
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps[name])
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this)

            //add your extra information
            textSliderView.bundle(Bundle())
            textSliderView.bundle
                    .putString("extra", name)

            mDemoSlider?.addSlider(textSliderView)
        }
        mDemoSlider?.setPresetTransformer(SliderLayout.Transformer.Accordion)
        mDemoSlider?.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom)
        mDemoSlider?.setCustomAnimation(DescriptionAnimation())
        mDemoSlider?.setDuration(4000)
        mDemoSlider?.addOnPageChangeListener(this)
        mDemoSlider?.setCustomAnimation(DescriptionAnimation())
    }

    override fun onSliderClick(slider: BaseSliderView?) {
        toast(""+slider?.bundle?.get("extra")!!.toString())

    }

    override fun onPageScrollStateChanged(state: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPageSelected(position: Int) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }


    override fun onStop() {
        super.onStop()
        mDemoSlider?.stopAutoCycle()
    }

    override fun onResume() {
        super.onResume()
        HideMessage()
    }

    fun ShowDialog(message: String){
        if (!mProgressDialog.isShowing){
            mProgressDialog.setMessage(message)
            mProgressDialog.setCancelable(false)
            mProgressDialog.show()
        }
    }
    fun HideMessage(){
        if (mProgressDialog.isShowing){
            mProgressDialog.dismiss()
        }
    }

}// Required empty public constructor
