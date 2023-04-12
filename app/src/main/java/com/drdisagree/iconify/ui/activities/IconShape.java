package com.drdisagree.iconify.ui.activities;

import static com.drdisagree.iconify.common.Const.FRAMEWORK_PACKAGE;
import static com.drdisagree.iconify.common.Preferences.SELECTED_ICON_SHAPE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.drdisagree.iconify.Iconify;
import com.drdisagree.iconify.R;
import com.drdisagree.iconify.config.Prefs;
import com.drdisagree.iconify.ui.utils.ViewBindingHelpers;
import com.drdisagree.iconify.utils.OverlayUtil;
import com.drdisagree.iconify.utils.SystemUtil;
import com.drdisagree.iconify.utils.compiler.OnDemandCompiler;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class IconShape extends AppCompatActivity {

    private FlexboxLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icon_shape);

        // Header
        ViewBindingHelpers.setHeader(this, findViewById(R.id.collapsing_toolbar), findViewById(R.id.toolbar), R.string.activity_title_icon_shape);

        // Icon masking shape list
        container = findViewById(R.id.icon_shape_preview_container);
        ArrayList<Object[]> icon_shape_preview_styles = new ArrayList<>();

        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_none, R.string.icon_mask_style_none});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_pebble, R.string.icon_mask_style_pebble});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_rounded_hexagon, R.string.icon_mask_style_hexagon});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_samsung, R.string.icon_mask_style_samsung});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_scroll, R.string.icon_mask_style_scroll});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_teardrops, R.string.icon_mask_style_teardrop});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_square, R.string.icon_mask_style_square});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_rounded_rectangle, R.string.icon_mask_style_rounded_rectangle});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_ios, R.string.icon_mask_style_ios});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_cloudy, R.string.icon_mask_style_cloudy});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_cylinder, R.string.icon_mask_style_cylinder});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_flower, R.string.icon_mask_style_flower});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_heart, R.string.icon_mask_style_heart});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_leaf, R.string.icon_mask_style_leaf});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_stretched, R.string.icon_mask_style_stretched});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_tapered_rectangle, R.string.icon_mask_style_tapered_rectangle});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_vessel, R.string.icon_mask_style_vessel});
        icon_shape_preview_styles.add(new Object[]{R.drawable.icon_shape_rohie_meow, R.string.icon_mask_style_rice_rohie_meow});
        
        refreshBackground();
    }

    // Function to check for bg drawable changes
    @SuppressLint("SetTextI18n")
    private void refreshBackground() {
        for (int i = 0; i < container.getChildCount(); i++) {
            LinearLayout child = container.getChildAt(i).findViewById(R.id.list_item_shape);
            TextView title = child.findViewById(R.id.shape_name);
            if (i == Prefs.getInt(SELECTED_ICON_SHAPE, 0)) {
                title.setTextColor(getResources().getColor(R.color.colorSuccess));
            } else {
                title.setTextColor(getResources().getColor(R.color.textColorSecondary));
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}