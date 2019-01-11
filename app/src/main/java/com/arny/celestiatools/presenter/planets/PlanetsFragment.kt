package com.arny.celestiatools.presenter.planets


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import com.arny.celestiatools.R
import com.arny.celestiatools.data.adapters.SimpleAbstractAdapter
import com.arny.celestiatools.data.models.Planet
import com.arny.celestiatools.data.utils.Utility
import com.arny.celestiatools.data.utils.setVisible
import com.arny.celestiatools.presenter.base.BaseMvpFragment
import com.arny.celestiatools.presenter.planet.PlanetActivity
import kotlinx.android.synthetic.main.planets_layout.*

class PlanetsFragment : BaseMvpFragment<PlanetsContract.View, PlanetsPresenter>(), PlanetsContract.View {
    private var adapter: PlanetsAdapter? = null
    private var hasResult = false

    override fun initPresenter(): PlanetsPresenter {
        return PlanetsPresenter()
    }

    companion object {
        fun newInstance(): PlanetsFragment {
            val fragment = PlanetsFragment()
//            val args = Bundle()
//            id?.let { args.putLong("id", it) }
//            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_menu_add -> {
                val intent = Intent(activity, PlanetActivity::class.java)
                startActivityForResult(intent, 100)
                return true
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i(PlanetsFragment::class.java.simpleName, "onActivityResult: requestCode:$requestCode, resultCode:$resultCode, data:" + Utility.dumpIntent(data))
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100, 102 -> {
                    hasResult = true
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        menu?.clear()
        inflater?.inflate(R.menu.planets_menu, menu)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.planets_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = "Планеты"
        initAdapter()
        mPresenter.loadPlanets()
    }

    override fun onResume() {
        super.onResume()
        if (hasResult) {
            hasResult = false
            mPresenter.loadPlanets()
        }
    }

    private fun initAdapter() {
        rv_planets.layoutManager = LinearLayoutManager(activity)
        adapter = PlanetsAdapter()
        adapter?.setViewHolderListener(object : SimpleAbstractAdapter.OnViewHolderListener<Planet> {
            override fun onItemClick(position: Int, item: Planet) {
                val intent = Intent()
                intent.putExtra("mass", item.mass)
                intent.putExtra("radius", item.radius)
                val targetFragment = targetFragment
                if (targetFragment != null) {
                    targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    activity?.onBackPressed()
                } else {
                    val i = Intent(activity, PlanetActivity::class.java)
                    i.putExtra("id", item.id)
                    startActivityForResult(i, 102)
                }
            }
        })
        rv_planets.adapter = adapter
    }

    override fun showEmptyView(vis: Boolean) {
        tv_empty_list_view.setVisible(vis)
    }

    override fun updateAdapter(list: ArrayList<Planet>) {
        adapter?.addAll(list)
    }

    override fun showList(vis: Boolean) {
        rv_planets.setVisible(vis)
    }
}
