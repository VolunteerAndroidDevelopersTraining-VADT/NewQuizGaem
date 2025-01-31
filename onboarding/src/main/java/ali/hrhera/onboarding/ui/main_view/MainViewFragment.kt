package ali.hrhera.onboarding.ui.main_view

import ali.hrhera.base.BaseFragment
import ali.hrhera.base.showErrorToast
import ali.hrhera.onboarding.databinding.FragmentMainViewBinding
import android.app.Activity.RESULT_OK
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainViewFragment : BaseFragment<FragmentMainViewBinding>() {
    override fun bind(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentMainViewBinding.inflate(inflater, container, false)

    private val viewModel by viewModels<OnBoardingViewModel>()
    override fun afterBind() {

        binding.onboardingViewPager.adapter = viewModel.adapter
        TabLayoutMediator(binding.onboardingViewPagerIndicator, binding.onboardingViewPager) { _, _ -> }.attach()

        binding.onboardingViewPager.registerOnPageChangeCallback(object :
            androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.next.isVisible = position != viewModel.adapter.itemCount - 1
            }
        })

        binding.next.setOnClickListener {
            if (binding.onboardingViewPager.currentItem in viewModel.adapter.currentList.indices) {
                binding.onboardingViewPager.currentItem += 1
            }
        }
        binding.skip.setOnClickListener {
            requireActivity().setResult(RESULT_OK)
            requireActivity().finish()
        }


        viewModel.errorMessage.observe(viewLifecycleOwner) {
            showErrorToast(it)
        }
    }
}