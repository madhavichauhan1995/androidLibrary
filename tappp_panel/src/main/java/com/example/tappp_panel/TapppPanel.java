package com.example.tappp_panel;

import android.content.Context;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;

import io.flutter.embedding.android.FlutterFragment;
import io.flutter.embedding.android.RenderMode;
import io.flutter.embedding.android.TransparencyMode;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

public class TapppPanel extends FrameLayout {
    static FlutterFragment flutterFragment;
    private static final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
    static final String CHANNEL = "pavel/flutter";
    static MethodChannel channel = null;
    public TapppPanel(@NonNull Context context) {
        super(context);
    }

    public static void s(Context c, String message){
        Toast.makeText(c,message, Toast.LENGTH_SHORT).show();
    }

    public  void loadPanel(FragmentManager supportFragmentManager, int fragmentContainer){

        FragmentManager fragmentManager = supportFragmentManager;
        flutterFragment = (FlutterFragment) fragmentManager
                .findFragmentByTag(TAG_FLUTTER_FRAGMENT);
        if (flutterFragment == null) {
            FlutterEngine flutterEngine = new FlutterEngine(getContext());
            flutterEngine.getDartExecutor().executeDartEntrypoint(
                    DartExecutor.DartEntrypoint.createDefault()
            );
            FlutterEngineCache.getInstance().put("demo", flutterEngine);
            FlutterFragment flutterFragment = FlutterFragment
                    .withCachedEngine("demo")
                    .renderMode(RenderMode.surface)
                    .transparencyMode(TransparencyMode.opaque)
                    .shouldAttachEngineToActivity(true)
                    .build();
            supportFragmentManager
                    .beginTransaction()
                    .add( fragmentContainer, flutterFragment, TAG_FLUTTER_FRAGMENT)
                    .commit();
            channel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);

            HashMap<String, String> arguments = new HashMap<>();
            arguments.put("counter", "9");
            channel.invokeMethod("setCounterValue", arguments);

            channel.setMethodCallHandler(
                    (call, result) -> {
                        String data = call.argument("data");
                        Log.i("madhavi",data);
                        result.success(null);
                    }
            );
        }
    }
}
