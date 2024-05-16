/*
 * Copyright (C) 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.notification.modes;

import static com.android.settings.notification.modes.ZenModeFragmentBase.MODE_ID;

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.os.Bundle;

import com.android.settings.core.SubSettingLauncher;
import com.android.settings.notification.zen.ZenModeSettings;
import com.android.settingslib.RestrictedPreference;

/**
 * Preference representing a single mode item on the modes aggregator page. Clicking on this
 * preference leads to an individual mode's configuration page.
 */
public class ZenModeListPreference extends RestrictedPreference {
    final Context mContext;
    ZenMode mZenMode;

    ZenModeListPreference(Context context, ZenMode zenMode) {
        super(context);
        mContext = context;
        mZenMode = zenMode;
        setTitle(mZenMode.getRule().getName());
        setSummary((mZenMode.isActive() ? "ACTIVE" : "inactive") + ": "
                + mZenMode.getRule().getTriggerDescription());
    }

    @Override
    public void onClick() {
        // TODO: b/322373473 - This implementation is a hack that just leads to the old DND page
        //                     for manual only; remove this in favor of the real implementation.
        if (mZenMode.isManualDnd()) {
            new SubSettingLauncher(mContext)
                    .setDestination(ZenModeSettings.class.getName())
                    .setSourceMetricsCategory(SettingsEnums.NOTIFICATION_ZEN_MODE)
                    .launch();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString(MODE_ID, mZenMode.getId());
            new SubSettingLauncher(mContext)
                    .setDestination(ZenModeFragment.class.getName())
                    .setArguments(bundle)
                    .setSourceMetricsCategory(SettingsEnums.NOTIFICATION_ZEN_MODE_AUTOMATION)
                    .launch();
        }

    }
}