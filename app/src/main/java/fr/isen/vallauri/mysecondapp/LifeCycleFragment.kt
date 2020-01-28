package fr.isen.vallauri.mysecondapp


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_life_cycle.*

/**
 * A simple [Fragment] subclass.
 */
class LifeCycleFragment : Fragment() {

    private fun notification(message : String, isActive : Boolean){
        if(isActive)
            textViewFragment.text = message
        else
            Log.d("TAG", message)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_life_cycle, container, false)
    }

    override fun onStart(){
        super.onStart()
        notification("onStart", isActive = true)
    }

    override fun onResume(){
        super.onResume()
        notification("onResume", isActive = true)
    }

    override fun onStop(){
        super.onStop()
        notification("onStop", isActive = false)
    }

    override fun onDestroy(){
        super.onDestroy()
        notification("onDestroy", isActive = false)
    }

    override fun onPause(){
        super.onPause()
        notification("onStop", isActive = false)
    }
}
