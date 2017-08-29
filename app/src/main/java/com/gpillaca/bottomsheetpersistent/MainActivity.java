package com.gpillaca.bottomsheetpersistent;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    BottomSheetBehavior behavior;
    boolean mIsCollapsedFromBackPress = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View bottomSheet = findViewById(R.id.design_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_DRAGGING");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_SETTLING");
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        setStatusBarDim(false);
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_EXPANDED");
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        mIsCollapsedFromBackPress = false;
                        onBackPressed();
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_COLLAPSED");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        Log.i("BottomSheetCallback", "BottomSheetBehavior.STATE_HIDDEN");
                        break;
                    default:
                        setStatusBarDim(true);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i("BottomSheetCallback", "slideOffset: " + slideOffset);
            }
        });

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
    }

    private void setStatusBarDim(boolean dim) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(dim ? Color.TRANSPARENT :
                    ContextCompat.getColor(this, getThemedResId(R.attr.colorPrimaryDark)));
        }
    }

    private int getThemedResId(@AttrRes int attr) {
        TypedArray a = getTheme().obtainStyledAttributes(new int[]{attr});
        int resId = a.getResourceId(0, 0);
        a.recycle();
        return resId;
    }

    @Override
    public void onBackPressed() {
        mIsCollapsedFromBackPress = true;
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

}
