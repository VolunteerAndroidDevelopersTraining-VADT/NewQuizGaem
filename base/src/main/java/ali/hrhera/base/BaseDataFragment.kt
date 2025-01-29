package ali.hrhera.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseDataFragment<DataBind : ViewDataBinding> : Fragment() {
    abstract fun bind(inflater: LayoutInflater, container: android.view.ViewGroup?): DataBind
    abstract fun afterBind()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        afterBind()
    }

    private var _binding: DataBind? = null
    protected val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = bind(inflater, container).apply {
            executePendingBindings()
            lifecycleOwner = this@BaseDataFragment.viewLifecycleOwner
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
        _binding = null
    }

}