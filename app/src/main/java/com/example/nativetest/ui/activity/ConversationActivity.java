

package com.example.nativetest.ui.activity;

import android.net.Uri;
import android.os.Bundle;

import com.example.nativetest.R;
import com.example.nativetest.widget.TitleBar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

public class ConversationActivity extends BaseActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.conversation);
//        ImmersionBar
//                .with(this)
//                .fitsSystemWindows(true)
//                .keyboardEnable(true)
//                .statusBarColor(R.color.white)
//                .statusBarDarkFont(true)
//                .init();
//
//
//    }

    @Override
    protected void initView() {
        FragmentManager fragmentManage = getSupportFragmentManager();
        ConversationFragment fragement = (ConversationFragment) fragmentManage.findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName())
                .appendQueryParameter("targetId", "niko2").build();

        fragement.setUri(uri);


        mTitleBar.getTitleBarIvRight().setOnClickListener(v -> {
            readyGo(ChatSettingActivity.class);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.conversation;
    }

}
