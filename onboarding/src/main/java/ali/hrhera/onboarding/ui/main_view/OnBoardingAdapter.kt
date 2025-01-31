package ali.hrhera.onboarding.ui.main_view

import ali.hrhera.base.adapter.BaseRecyclerAdapter
import ali.hrhera.onboarding.databinding.ItemOnboardingScreenBinding
import ali.hrhera.onboarding.domain.OnBoardingScreenDto
import android.view.LayoutInflater
import android.view.ViewGroup

class OnBoardingAdapter : BaseRecyclerAdapter<OnBoardingScreenDto, ItemOnboardingScreenBinding>(
    areItemsTheSame = { old, new -> old.id == new.id }) {
    override fun viewBind(layoutInflater: LayoutInflater, parent: ViewGroup): ItemOnboardingScreenBinding {
        return ItemOnboardingScreenBinding.inflate(layoutInflater, parent, false)
    }

    override fun bind(binding: ItemOnboardingScreenBinding, item: OnBoardingScreenDto, position: Int) {
        binding.model = item
    }
}