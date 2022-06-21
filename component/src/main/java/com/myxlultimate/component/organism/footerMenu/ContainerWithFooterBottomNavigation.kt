package com.myxlultimate.component.organism.footerMenu

import android.content.Context
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.myxlultimate.component.R
import com.myxlultimate.component.databinding.OrganismFooterMenuBottomNavTryBinding
import com.myxlultimate.component.organism.footerMenu.enum.ItemFooterMenuEnum

class ContainerWithFooterBottomNavigation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    val binding = OrganismFooterMenuBottomNavTryBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    var onClickDashboardListener: (() -> Unit)? = null
    var onClickXlStoreListener: (() -> Unit)? = null
    var onClickXlCareListener: (() -> Unit)? = null
    var onClickProfileListener: (() -> Unit)? = null
    var onFloatingButtonClickListener: (() -> Unit)? = null

    var showRedDotZeroDashboard = false
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.getOrCreateBadge(R.id.Dashboard).let {
                settingUpBadge(
                    it,
                    badgeDashboard, value
                )
            }
        }

    var showRedDotZeroXlStore = false
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.getOrCreateBadge(R.id.XlStore).let {
                settingUpBadge(
                    it,
                    badgeXlStore, value
                )
            }
        }

    var showRedDotZeroProfile = false
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.getOrCreateBadge(R.id.Profile).let {
                settingUpBadge(
                    it,
                    badgeProfile, value
                )
            }
        }

    var showRedDotZeroXlCare = false
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.getOrCreateBadge(R.id.XlCare).let {
                settingUpBadge(
                    it,
                    badgeXlCare, value
                )
            }
        }

    var badgeDashboard = 0
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.getOrCreateBadge(R.id.Dashboard).let {
                settingUpBadge(
                    it,
                    badgeDashboard,
                    showRedDotZeroDashboard
                )
            }
        }

    // ----------------------------------------------------------------------------------

    var badgeXlStore = 0
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.let {
                settingUpBadge(
                    it.getOrCreateBadge(R.id.XlStore),
                    badgeXlStore,
                    showRedDotZeroXlStore
                )
            }
        }

    // ----------------------------------------------------------------------------------

    var badgeXlCare = 0
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.getOrCreateBadge(R.id.XlCare).let {
                settingUpBadge(
                    it,
                    badgeXlCare,
                    showRedDotZeroXlCare
                )
            }
        }

    // ----------------------------------------------------------------------------------

    var badgeProfile = 0
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.let {
                settingUpBadge(
                    it.getOrCreateBadge(R.id.Profile),
                    badgeProfile,
                    showRedDotZeroProfile
                )
            }
        }
    // ----------------------------------------------------------------------------------


    var badgeMiddle = 0
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.let {
                settingUpBadge(
                    it.getOrCreateBadge(R.id.XlExtra),
                    badgeMiddle,
                    false
                )
            }
        }
    // ----------------------------------------------------------------------------------

    var isXlStoreEnabled: Boolean = true
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.menu.findItem(R.id.XlStore).isEnabled = value
        }

    // ----------------------------------------------------------------------------------

    var itemId = R.id.Dashboard
        set(value) {
            field = value
            binding.footerMenuBottomNavigationView.selectedItemId = value
            binding.footerMenuBottomNavigationView.menu.findItem(value).isChecked = true
        }

    // ----------------------------------------------------------------------------------

    fun getItemId(value: ItemFooterMenuEnum): MenuItem? {
        return try {
            binding.footerMenuBottomNavigationView.menu.findItem(
                value.itemId
            )
        } catch (e: Exception) {
            null
        }
    }

    var isFloatingSet = false
        set(value) {
            field = value
            SetUpFloating(isFloatingSet)
        }

    // ----------------------------------------------------------------------------------

    init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.footerMenuBottomNavigationAttr)
        typedArray.run {
            badgeDashboard = getInt(R.styleable.footerMenuBottomNavigationAttr_badgeDashboard, 0)
            badgeXlStore = getInt(R.styleable.footerMenuBottomNavigationAttr_badgeXlStore, 0)
            badgeXlCare = getInt(R.styleable.footerMenuBottomNavigationAttr_badgeXlCare, 0)
            badgeProfile = getInt(R.styleable.footerMenuBottomNavigationAttr_badgeProfile, 0)
        }
        typedArray.recycle()

        binding.footerMenuBottomNavigationView.labelVisibilityMode =
            LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        setUpListener()

    }

    private fun settingUpBadge(drawable: BadgeDrawable, number: Int, isZeroVisible: Boolean) {
        if (!isZeroVisible) {
            drawable.isVisible = false
        } else {
            if (number > 0) {
                drawable.number = number
            }
            drawable.isVisible = true
        }
    }


    private fun setUpListener() {
        binding.footerMenuBottomNavigationView.setOnNavigationItemSelectedListener { item ->
            binding.footerMenuBottomNavigationView.itemRippleColor =
                ColorStateList.valueOf(getColor(context, R.color.mud_palette_basic_light_grey))
            binding.footerMenuBottomNavigationView.menu.setGroupCheckable(0, true, true)
            setupFloatingCustomActive(false)
            when (item.itemId) {
                R.id.Dashboard -> {
                    onClickDashboardListener?.let { it() }
                    true
                }
                R.id.XlStore -> {
                    onClickXlStoreListener?.let { it() }
                    true
                }
                R.id.XlCare -> {
                    onClickXlCareListener?.let { it() }
                    true
                }
                R.id.Profile -> {
                    onClickProfileListener?.let { it() }
                    true
                }
                else -> false
            }
        }
        binding.footerMenuBottomNavigationView.setOnNavigationItemReselectedListener {
            binding.footerMenuBottomNavigationView.itemRippleColor =
                ColorStateList.valueOf(getColor(context, R.color.transparent))
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    binding.footerMenuBottomNavigationView.itemRippleColor =
                        ColorStateList.valueOf(
                            getColor(
                                context,
                                R.color.mud_palette_basic_light_grey
                            )
                        )
                },
                1000
            )
        }
        binding.fab.setOnClickListener {
            onFloatingButtonClickListener?.let { it() }
            binding.footerMenuBottomNavigationView.menu.getItem(2).isChecked = true
            setupFloatingCustomActive(true)
        }
    }

    private fun SetUpFloating(isShown: Boolean) {
        binding.footerMenuBottomNavigationView.menu.getItem(2).isEnabled = !isShown
        binding.fab.apply {
            visibility = if (isShown) View.VISIBLE else View.GONE
        }
        if (isShown) {
            binding.footerMenuBottomNavigationView.menu.clear() //clear old inflated items.
            binding.footerMenuBottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu_new)
        } else {
            binding.footerMenuBottomNavigationView.menu.clear() //clear old inflated items.
            binding.footerMenuBottomNavigationView.inflateMenu(R.menu.bottom_navigation_menu)
        }
    }

    private fun setupFloatingCustomActive(isActive: Boolean) {
        binding.fab.background = ContextCompat.getDrawable(
            context, if (isActive) R.drawable.bg_floating_menu_active
            else R.drawable.bg_floating_menu
        )
        binding.ivFab.setColorFilter(
            getColor(context, if (isActive) R.color.basicWhite else R.color.primaryBlue),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        binding.tvFab.setTextColor(
            getColor(
                context, if (isActive) R.color.basicWhite
                else R.color.primaryBlue
            )
        )
    }
}