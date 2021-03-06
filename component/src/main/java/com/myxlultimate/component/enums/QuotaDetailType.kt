package com.myxlultimate.component.enums

enum class QuotaDetailType(
    var type: String
) {
    PACKAGE_CANCELLABLE("PACKAGE_CANCELLABLE"),
    PACKAGE_NON_CANCELLABLE("PACKAGE_NON_CANCELLABLE"),
    EXPIRATION_UNLIMITED("EXPIRATION_UNLIMITED"),
    BOOSTER_RECURRING("BOOSTER_RECURRING"),
    BOOSTER_ONE_TIME("BOOSTER_ONE_TIME"),
    BASIC_PLAN_SWITCHABLE("BASIC_PLAN_SWITCHABLE"),
    BASIC_PLAN_SWITCHED("BASIC_PLAN_SWITCHED"),
    CONTRACT_PLAN("CONTRACT_PLAN"),
    ROAMING_ACTIVE("ROAMING_ACTIVE"),
    ROAMING_INACTIVE("ROAMING_INACTIVE"),
    GROUPED_BOOSTER_NON_CANCELLABLE("GROUPED_BOOSTER_NON_CANCELLABLE"),
    GROUPED_BOOSTER_CANCELLABLE("GROUPED_BOOSTER_CANCELLABLE"),
    GROUPED_ADDON_NON_CANCELLABLE("GROUPED_ADDON_NON_CANCELLABLE"),
    FTTH_UPGRADABLE("FTTH_UPGRADABLE"),
    FAMILY_PLAN_CANCELLABLE("FAMILY_PLAN_CANCELLABLE"),
    FAMILY_PLAN_NON_CANCELLABLE("FAMILY_PLAN_NON_CANCELLABLE"),
    FAMILY_PLAN_MEMBER_DETAIL("FAMILY_PLAN_MEMBER_DETAIL"),
    FAMILY_PLAN_UPGRADABLE("FAMILY_PLAN_UPGRADABLE"),
    FAMILY_PLAN_NON_UPGRADABLE("FAMILY_PLAN_NON_UPGRADABLE"),
    FAMILY_PLAN_ADD_SLOT("FAMILY_PLAN_ADD_SLOT"),
    CUSTOM("CUSTOM");

    companion object {
        operator fun invoke(type: String = ""): QuotaDetailType {
            return values().firstOrNull { it.name == type } ?: PACKAGE_CANCELLABLE
        }
    }
}