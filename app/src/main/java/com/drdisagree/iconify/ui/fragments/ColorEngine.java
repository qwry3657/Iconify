package com.drdisagree.iconify.ui.fragments;

import static com.drdisagree.iconify.common.Const.FRAGMENT_BACK_BUTTON_DELAY;
import static com.drdisagree.iconify.common.Const.FRAMEWORK_PACKAGE;
import static com.drdisagree.iconify.common.Const.SWITCH_ANIMATION_DELAY;
import static com.drdisagree.iconify.common.Preferences.COLOR_ACCENT_PRIMARY;
import static com.drdisagree.iconify.common.Preferences.COLOR_ACCENT_SECONDARY;
import static com.drdisagree.iconify.common.Preferences.COLOR_PIXEL_DARK_BG;
import static com.drdisagree.iconify.common.Preferences.STR_NULL;
import static com.drdisagree.iconify.common.Preferences.USE_LIGHT_ACCENT;
import static com.drdisagree.iconify.common.References.ICONIFY_COLOR_ACCENT_PRIMARY;
import static com.drdisagree.iconify.common.References.ICONIFY_COLOR_ACCENT_SECONDARY;
import static com.drdisagree.iconify.common.References.ICONIFY_COLOR_PIXEL_DARK_BG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.drdisagree.iconify.R;
import com.drdisagree.iconify.config.Prefs;
import com.drdisagree.iconify.ui.activities.BasicColors;
import com.drdisagree.iconify.ui.activities.MonetEngine;
import com.drdisagree.iconify.utils.FabricatedUtil;
import com.drdisagree.iconify.utils.OverlayUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;
import java.util.Objects;

public class ColorEngine extends Fragment {

    private View view;
    public static List<String> EnabledOverlays = OverlayUtil.getEnabledOverlayList();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_color_engine, container, false);

        // Header
        CollapsingToolbarLayout collapsing_toolbar = view.findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle(getResources().getString(R.string.activity_title_color_engine));
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(view1 -> new Handler().postDelayed(() -> {
            getParentFragmentManager().popBackStack();
        }, FRAGMENT_BACK_BUTTON_DELAY));

        // Basic colors
        LinearLayout basic_colors = view.findViewById(R.id.basic_colors);
        basic_colors.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), BasicColors.class);
            startActivity(intent);
        });

        // Monet engine
        LinearLayout monet_engine = view.findViewById(R.id.monet_engine);
        monet_engine.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), MonetEngine.class);
            startActivity(intent);
        });

        // Apply monet accent and gradient
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch apply_monet_accent = view.findViewById(R.id.apply_monet_accent);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch apply_monet_gradient = view.findViewById(R.id.apply_monet_gradient);

        apply_monet_accent.setChecked(Prefs.getBoolean("IconifyComponentAMAC.overlay") || Prefs.getBoolean("IconifyComponentAMACL.overlay"));
        apply_monet_accent.setOnCheckedChangeListener(monetAccentListener);

        apply_monet_gradient.setChecked(Prefs.getBoolean("IconifyComponentAMGC.overlay") || Prefs.getBoolean("IconifyComponentAMGCL.overlay"));
        apply_monet_gradient.setOnCheckedChangeListener(monetGradientListener);

        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch apply_minimal_qspanel = view.findViewById(R.id.apply_minimal_qspanel);
        @SuppressLint("UseSwitchCompatOrMaterialCode") Switch apply_pitch_black_theme = view.findViewById(R.id.apply_pitch_black_theme);

        // Minimal QsPanel
        apply_minimal_qspanel.setChecked(Prefs.getBoolean("IconifyComponentQSST.overlay"));

        apply_minimal_qspanel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            new Handler().postDelayed(() -> {
                if (isChecked) {
                    apply_pitch_black_theme.setChecked(false);

                    apply_minimal_qspanel.postDelayed(() -> {
                        OverlayUtil.enableOverlay("IconifyComponentQSST.overlay");
                    }, SWITCH_ANIMATION_DELAY);
                } else {
                    OverlayUtil.disableOverlay("IconifyComponentQSST.overlay");
                }
            }, SWITCH_ANIMATION_DELAY);
        });

        // Pitch Black QsPanel
        apply_pitch_black_theme.setChecked(Prefs.getBoolean("IconifyComponentQSPB.overlay"));

        apply_pitch_black_theme.setOnCheckedChangeListener((buttonView, isChecked) -> {
            new Handler().postDelayed(() -> {
                if (isChecked) {
                    apply_minimal_qspanel.setChecked(false);

                    apply_pitch_black_theme.postDelayed(() -> {
                        OverlayUtil.enableOverlay("IconifyComponentQSPB.overlay");
                    }, SWITCH_ANIMATION_DELAY);
                } else {
                    OverlayUtil.disableOverlay("IconifyComponentQSPB.overlay");
                }
            }, SWITCH_ANIMATION_DELAY);
        });

        return view;
    }

    private void enableMonetAccent() {
        if (Prefs.getBoolean(USE_LIGHT_ACCENT, false)) {
            OverlayUtil.disableOverlay("IconifyComponentAMAC.overlay");
            OverlayUtil.enableOverlay("IconifyComponentAMACL.overlay");
        } else {
            OverlayUtil.disableOverlay("IconifyComponentAMACL.overlay");
            OverlayUtil.enableOverlay("IconifyComponentAMAC.overlay");
        }

        if (!Objects.equals(Prefs.getString(COLOR_ACCENT_PRIMARY), STR_NULL)) {
            BasicColors.applyPrimaryColors();
        } else {
            FabricatedUtil.disableOverlay(COLOR_ACCENT_PRIMARY);
        }

        if (!Objects.equals(Prefs.getString(COLOR_ACCENT_SECONDARY), STR_NULL)) {
            BasicColors.applySecondaryColors();
        } else {
            FabricatedUtil.disableOverlay(COLOR_ACCENT_SECONDARY);
        }
    }

    private void disableMonetAccent() {
        if (!((Switch) view.findViewById(R.id.apply_monet_gradient)).isChecked() && OverlayUtil.isOverlayDisabled(EnabledOverlays, "IconifyComponentME.overlay")) {
            if (Prefs.getString(COLOR_ACCENT_PRIMARY).equals(STR_NULL)) {
                FabricatedUtil.buildAndEnableOverlay(FRAMEWORK_PACKAGE, COLOR_ACCENT_PRIMARY, "color", "holo_blue_light", ICONIFY_COLOR_ACCENT_PRIMARY);
                FabricatedUtil.buildAndEnableOverlay(FRAMEWORK_PACKAGE, COLOR_PIXEL_DARK_BG, "color", "holo_blue_dark", ICONIFY_COLOR_PIXEL_DARK_BG);
            }

            if (Prefs.getString(COLOR_ACCENT_SECONDARY).equals(STR_NULL)) {
                FabricatedUtil.buildAndEnableOverlay(FRAMEWORK_PACKAGE, COLOR_ACCENT_SECONDARY, "color", "holo_green_light", ICONIFY_COLOR_ACCENT_SECONDARY);
            }
        }

        OverlayUtil.disableOverlay("IconifyComponentAMAC.overlay");
        OverlayUtil.disableOverlay("IconifyComponentAMACL.overlay");
    }

    private void enableMonetGradient() {
        if (Prefs.getBoolean(USE_LIGHT_ACCENT, false)) {
            OverlayUtil.disableOverlay("IconifyComponentAMGC.overlay");
            OverlayUtil.enableOverlay("IconifyComponentAMGCL.overlay");
        } else {
            OverlayUtil.disableOverlay("IconifyComponentAMGCL.overlay");
            OverlayUtil.enableOverlay("IconifyComponentAMGC.overlay");
        }

        if (!Objects.equals(Prefs.getString(COLOR_ACCENT_PRIMARY), STR_NULL)) {
            BasicColors.applyPrimaryColors();
        } else {
            FabricatedUtil.disableOverlay(COLOR_ACCENT_PRIMARY);
        }

        if (!Objects.equals(Prefs.getString(COLOR_ACCENT_SECONDARY), STR_NULL)) {
            BasicColors.applySecondaryColors();
        } else {
            FabricatedUtil.disableOverlay(COLOR_ACCENT_SECONDARY);
        }
    }

    private void disableMonetGradient() {
        if (!((Switch) view.findViewById(R.id.apply_monet_accent)).isChecked() && OverlayUtil.isOverlayDisabled(EnabledOverlays, "IconifyComponentME.overlay")) {
            if (Prefs.getString(COLOR_ACCENT_PRIMARY).equals(STR_NULL)) {
                FabricatedUtil.buildAndEnableOverlay(FRAMEWORK_PACKAGE, COLOR_ACCENT_PRIMARY, "color", "holo_blue_light", ICONIFY_COLOR_ACCENT_PRIMARY);
                FabricatedUtil.buildAndEnableOverlay(FRAMEWORK_PACKAGE, COLOR_PIXEL_DARK_BG, "color", "holo_blue_dark", ICONIFY_COLOR_PIXEL_DARK_BG);
            }

            if (Prefs.getString(COLOR_ACCENT_SECONDARY).equals(STR_NULL)) {
                FabricatedUtil.buildAndEnableOverlay(FRAMEWORK_PACKAGE, COLOR_ACCENT_SECONDARY, "color", "holo_green_light", ICONIFY_COLOR_ACCENT_SECONDARY);
            }
        }

        OverlayUtil.disableOverlay("IconifyComponentAMGC.overlay");
        OverlayUtil.disableOverlay("IconifyComponentAMGCL.overlay");
    }

    CompoundButton.OnCheckedChangeListener monetAccentListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                ((Switch) view.findViewById(R.id.apply_monet_gradient)).setOnCheckedChangeListener(null);
                ((Switch) view.findViewById(R.id.apply_monet_gradient)).setChecked(false);
                ((Switch) view.findViewById(R.id.apply_monet_gradient)).setOnCheckedChangeListener(monetGradientListener);
            }

            new Handler().postDelayed(() -> {
                if (isChecked) {
                    disableMonetGradient();
                    enableMonetAccent();
                } else {
                    disableMonetAccent();
                }
            }, SWITCH_ANIMATION_DELAY);
        }
    };

    CompoundButton.OnCheckedChangeListener monetGradientListener = (buttonView, isChecked) -> {
        if (isChecked) {
            ((Switch) view.findViewById(R.id.apply_monet_accent)).setOnCheckedChangeListener(null);
            ((Switch) view.findViewById(R.id.apply_monet_accent)).setChecked(false);
            ((Switch) view.findViewById(R.id.apply_monet_accent)).setOnCheckedChangeListener(monetAccentListener);
        }

        new Handler().postDelayed(() -> {
            if (isChecked) {
                disableMonetAccent();
                enableMonetGradient();
            } else {
                disableMonetGradient();
            }
        }, SWITCH_ANIMATION_DELAY);
    };
}